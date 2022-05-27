package com.example.web.requests.query.handler;

import com.example.web.domain.models.UsersRequestBody;
import com.example.web.requests.query.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
public class BaseRabbitRequestHandler implements RabbitRequestHandler {

    final AsyncRabbitTemplate asyncRabbitTemplate;
    final Map<UUID, AsyncRabbitTemplate.RabbitMessageFuture> responses;

    public BaseRabbitRequestHandler(AsyncRabbitTemplate asyncRabbitTemplate, Map<UUID, AsyncRabbitTemplate.RabbitMessageFuture> responses) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.responses = responses;
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows(JsonProcessingException.class)
    public String getRequestResourceObjectAsString(UUID correlationId, UUID userUUID) {
        Map<String, UUID> requestResourceAsObject = new HashMap<>();
        requestResourceAsObject.put("ResourceId:", UUID.randomUUID());
        requestResourceAsObject.put("CorrelationId:", correlationId);
        requestResourceAsObject.put("uuid:", userUUID);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestResourceAsObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createUserRequest(UsersRequestBody userBody) {
        Message message = MessageBuilder
                .withBody(userBody.toString().getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setType("CREATE")
                .build();
        AsyncRabbitTemplate.RabbitMessageFuture future = asyncRabbitTemplate.sendAndReceive(RabbitMQConfig.REQUEST_QUEUE,message);
        responses.put(UUID.fromString(message.getMessageProperties().getCorrelationId()), future);
        UUID correlationId = UUID.fromString(message.getMessageProperties().getCorrelationId());
        return getRequestResourceObjectAsString(correlationId,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateUserRequest(UsersRequestBody userBody, UUID userUUID) {
        Message message = MessageBuilder
                .withBody(userBody.toString().getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("ProvidedUUID", String.valueOf(userUUID))
                .setType("UPDATE")
                .build();
        AsyncRabbitTemplate.RabbitMessageFuture future = asyncRabbitTemplate.sendAndReceive(RabbitMQConfig.REQUEST_QUEUE,message);
        responses.put(UUID.fromString(message.getMessageProperties().getCorrelationId()),future);
        UUID correlationId = UUID.fromString(message.getMessageProperties().getCorrelationId());
        return getRequestResourceObjectAsString(correlationId, userUUID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteUserRequest(UUID userUUID) {
        Message message = MessageBuilder.withBody("".getBytes())
                .setHeader("ProvidedUUID", String.valueOf(userUUID))
                .setType("DELETE")
                .build();
        AsyncRabbitTemplate.RabbitMessageFuture future = asyncRabbitTemplate.sendAndReceive(RabbitMQConfig.REQUEST_QUEUE,message);
        responses.put(UUID.fromString(message.getMessageProperties().getCorrelationId()),future);
        UUID correlationId = UUID.fromString(message.getMessageProperties().getCorrelationId());
        return getRequestResourceObjectAsString(correlationId, userUUID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRespondMessage(UUID correlationId) throws ExecutionException, InterruptedException {
        if(!responses.containsKey(correlationId)) {
            return "Your request doesn't exist in queue";
        }
        else if(isRequestDone(correlationId)) {
            Message message = responses.get(correlationId).get();
            return new String(Objects.requireNonNull(message).getBody(), StandardCharsets.UTF_8);
        }
        else {
            return "Your request still in queue. Check later";
        }
    }

    private boolean isRequestDone(UUID correlationId) {
        return responses.get(correlationId).isDone();
    }
}
