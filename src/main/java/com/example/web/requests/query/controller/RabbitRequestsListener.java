package com.example.web.requests.query.controller;

import com.example.web.domain.exceptions.UserIdMismatchException;
import com.example.web.domain.models.Users;
import com.example.web.jpa.handler.UsersRepositoryHandler;
import com.example.web.requests.query.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * This class is defined as {@link Component} and is responsibility for handle requests from request queue defined in {@link RabbitMQConfig}
 */
@Component
public class RabbitRequestsListener {

    public final UsersRepositoryHandler usersRepositoryHandler;
    public final RabbitTemplate rabbitTemplate;
    public final ObjectMapper objectMapper;

    public RabbitRequestsListener(UsersRepositoryHandler usersRepositoryHandler, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.usersRepositoryHandler = usersRepositoryHandler;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Listener for request queue. Awaits for requests and proceed with request method defined in request message header.
     * @param providedMessage Object of {@link Message Message} Class provided from queue.
     * @return Message body as String sent to reply-to queue.
     */
    @RabbitListener(queues = "cud.in.queue.requests")
    public String listenRequests(Message providedMessage) throws JsonProcessingException {
        String response = "";
        RequestMethod requestMethod = RequestMethod.valueOf(providedMessage.getMessageProperties().getType());
        switch(requestMethod) {
            case CREATE:
                response = createRequestAccepted(providedMessage);
                break;
            case UPDATE:
                response = updateRequestAccepted(providedMessage);
                break;
            case DELETE:
                response = deleteRequestAccepted(providedMessage);
                break;
        }
        return response;
    }

    /**
     * Create a {@link Users object} from provided Body from {@link Message Message} Class and save it in database via methods in
     * {@link UsersRepositoryHandler} Interface and send status to console
     * @param providedMessage A {@link Message Message} which is delivered from queue
     * @return Object of String with response to callback queue
     */
    private String createRequestAccepted(Message providedMessage) throws JsonProcessingException {
        Users userBodyToCreate = objectMapper.readValue(new String(providedMessage.getBody(), StandardCharsets.UTF_8), Users.class);
        try {
            usersRepositoryHandler.saveUserInDB(userBodyToCreate);
        }
        catch(Exception e) {
            return exceptionPrinter(e, providedMessage.getMessageProperties().getType(), userBodyToCreate.getUserUUID());
        }

        return "User " + userBodyToCreate.getUserUUID() + " created successfully";
    }

    /**
     * Create a {@link Users object} from provided Body from {@link Message Message} Class and update it in database via methods in
     * {@link UsersRepositoryHandler} Interface and send status to callback queue.
     * @param providedMessage A {@link Message Message} which is delivered from queue
     * @return Object of String with response to callback queue
     */
    private String updateRequestAccepted(Message providedMessage) throws JsonProcessingException {
        Users userBodyToUpdate = objectMapper.readValue(new String(providedMessage.getBody(), StandardCharsets.UTF_8), Users.class);
        String providedUUIDAsString = providedMessage.getMessageProperties().getHeader("ProvidedUUID");
        try {
            usersRepositoryHandler.updateUserInDB(
                    userBodyToUpdate,
                    UUID.fromString(providedUUIDAsString)
            );
        }
        catch(Exception e) {
            return exceptionPrinter(e, providedMessage.getMessageProperties().getType(), userBodyToUpdate.getUserUUID());
        }

        return "User " + providedUUIDAsString + " has been updated successfully";
    }

    /**
     * With provided header ({@link UUID}) delete user in database via methods in
     * {@link UsersRepositoryHandler} Interface and send status to console
     * @param providedMessage A {@link Message Message} which is delivered from queue
     * @return Object of String with response to callback queue
     */
    private String deleteRequestAccepted(Message providedMessage) {
        String providedUUIDAsString = providedMessage.getMessageProperties().getHeader("ProvidedUUID");
        try {
            usersRepositoryHandler.removeUserFromDB(
                    UUID.fromString(providedUUIDAsString)
            );
        }
        catch(UserIdMismatchException e) {
            return exceptionPrinter(e, providedMessage.getMessageProperties().getType(), UUID.fromString(providedUUIDAsString));
        }
        return "User " + providedUUIDAsString + " has been deleted successfully";
    }


    /**
     * This method return status with occurred exception as response.
     * @param occurredException Object of occurred exception.
     * @param requestMethod Name of method, which is requested (CREATE, UPDATE, DELETE).
     * @param providedUserUUID UUID of user from database.
     * @return Response status with occurred exception as String.
     */
    private String exceptionPrinter(Exception occurredException, String requestMethod, UUID providedUserUUID) {
        StringJoiner joinExceptionValues = new StringJoiner(",\n");
        joinExceptionValues.add("User " + providedUserUUID + " has been unable to " + requestMethod);
        joinExceptionValues.add("Reason: " + occurredException.getMessage());
        return joinExceptionValues.toString();
    }
}