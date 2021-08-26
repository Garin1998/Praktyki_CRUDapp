package com.example.web.requests.query.handler;

import com.example.web.domain.models.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Interface which is used for using methods implemented in {@link BaseRabbitRequestHandler Class}
 * @author  Krzysztof Kubiś
 * @version 1.0
 * @since   JDK 11
 */
public interface RabbitRequestHandler {
    /**
     * Prepare a JSON object to be returned to Client as String with an information about request.
     * @param correlationId Request's CorrelationID
     * @param userUUID UUID of user
     * @return Json as String with request's information.
     */
    @SneakyThrows(JsonProcessingException.class)
    String getRequestResourceObjectAsString(UUID correlationId, UUID userUUID);

    /**
     * Send a {@link Message Message} to queue with CREATE HTTP request.
     * @param userBody Object of {@link Users#Users Class}, which is sent as Message Body
     * @return Request's CorrelationID.
     */
    @SneakyThrows(JsonProcessingException.class)
    String createUserRequest(Users userBody);

    /**
     * Send a {@link Message Message} to queue with PUT HTTP request.
     * @param userBody Object of {@link Users#Users Class}, which is sent as Message Body
     * @param userUUID UUID of user which we want to find and update in database
     * @return Request's CorrelationID.
     */
    @SneakyThrows(JsonProcessingException.class)
    String updateUserRequest(Users userBody, UUID userUUID);

    /**
     * Send a {@link Message Message} without body to queue with DELETE HTTP request.
     * @param userUUID UUID of user which we want to find and delete in database.
     * @return Request's CorrelationID.
     */
    @SneakyThrows(JsonProcessingException.class)
    String deleteUserRequest(UUID userUUID);

    /**
     * Send {@link UUID UUID} of request and check realisation status. It's an asynchronous reply method for future.
     * @param requestUUID UUID of sent request to queue as correlation ID.
     * @return Body of {@link Message Message} with realization status as String
     * @throws ExecutionException Exception thrown when attempting to retrieve the result of a task that aborted by throwing an exception
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     * @see ExecutionException
     * @see InterruptedException
     */
    String getRespondMessage(UUID requestUUID) throws ExecutionException, InterruptedException;
}
