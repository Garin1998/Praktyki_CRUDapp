package com.example.web.requests.query.controller;

import com.example.web.domain.models.Users;
import com.example.web.requests.query.handler.RabbitRequestHandler;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Class which is used for AMPQ service via REST controller.
 * @author Krzysztof Kubiś
 * @version 1.0
 * @since JDK 11
 */
@RestController
@RequestMapping("/ampq")
public class RabbitRequestsController {
    public final RabbitRequestHandler rabbitRequestHandler;

    public RabbitRequestsController(RabbitRequestHandler rabbitRequestHandler) {
        this.rabbitRequestHandler = rabbitRequestHandler;
    }

    /**
     * Handle HTTP request method POST on default path defined in class.
     * @param userBody Object of a {@link Users#Users Class}.
     * @return Object with resources such as correlationID, which is request identification.
     * @see RabbitRequestHandler
     */
    @SneakyThrows
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String sendCreateRequestToQuery(@Valid @RequestBody Users userBody) {
        return rabbitRequestHandler.createUserRequest(userBody);
    }

    /**
     * Handle HTTP request method PUT on /amqp/userUUID path, where userUUID is identification for user, which we want to update in database.
     * @param userBody Object of a {@link Users#Users Class}.
     * @param userUUID UUID of user which we want to find in database.
     * @return Object with resources such as correlationID, which is request identification.
     * @see RabbitRequestHandler
     */
    @SneakyThrows
    @PutMapping("/{userUUID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String sendUpdateRequestToQuery(@Valid @RequestBody Users userBody, @PathVariable UUID userUUID) {
        return rabbitRequestHandler.updateUserRequest(userBody, userUUID);
    }

    /**
     * Handle HTTP request method DELETE on /amqp/userUUID path, where userUUID is identification for user, which we want to delete in database.
     * @param userUUID UUID of user which we want to find in database.
     * @return Object with resources such as correlationID, which is request identification.
     * @see RabbitRequestHandler
     */
    @SneakyThrows
    @DeleteMapping("/{userUUID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String sendDeleteRequestToQuery(@PathVariable UUID userUUID) {
        return rabbitRequestHandler.deleteUserRequest(userUUID);
    }

    /**
     * <p>Handle HTTP request method GET on /amqp/status/responseUUID,
     * where responseUUID is identification for request with provided.</p>
     * @param responseUUID UUID of response requested message, which we want to retrieve.
     * @return Body of {@link Message Message} with realization status as String.
     * @see RabbitRequestHandler
     */
    @SneakyThrows
    @GetMapping("/status/{responseUUID}")
    @ResponseStatus(HttpStatus.OK)
    public String getResponseMessage(@PathVariable UUID responseUUID) {
        return rabbitRequestHandler.getRespondMessage(responseUUID);
    }
}