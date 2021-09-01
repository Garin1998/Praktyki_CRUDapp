package com.example.web.jpa.controller;

import com.example.web.domain.models.Users;
import com.example.web.jpa.handler.UsersRepositoryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;


/**
 * Class which is used as controller for REST service for Users repository
 * @author Krzysztof Kubiś
 * @version 1.0
 * @since JDK 11
 */
@RestController
@RequestMapping("/users")
public class UsersRepositoryController {

    private final UsersRepositoryHandler usersRepositoryHandler;

    public UsersRepositoryController(UsersRepositoryHandler usersRepositoryHandler) {
        this.usersRepositoryHandler = usersRepositoryHandler;
    }

    /**
     * Redirect from default path /users to /users/all/0.
     * @return representation of Model and View in SpringMVC
     */
    @GetMapping("/")
    public ModelAndView redirectToFirstPage() {
        return new ModelAndView("redirect:" + "all/0");
    }

    /**
     * Handle HTTP request method GET on default path defined in class.
     * @return Body of founded users in database as object of {@link Users#Users class}
     * @see UsersRepositoryHandler
     */
    @GetMapping("/all/{numberOfPage}")
    public Iterable<Users> findAll(@PathVariable String numberOfPage) {
        return usersRepositoryHandler.findAllUsersInDB(numberOfPage);
    }

    /**
     * Handle HTTP request method GET on /users/uuid path, where uuid is identification for user, which we want to find.
     * @param userUUID UUID of user which we want to find in database
     * @return Body of founded user via UUID in database as object of a {@link Users#Users Class}
     * @see UsersRepositoryHandler
     */
    @GetMapping("/{userUUID}")
    public Users findByUUID(@PathVariable UUID userUUID) {
        return usersRepositoryHandler.findUserByUUID(userUUID);
    }

    /**
     * Handle HTTP request method POST on default path defined in class.
     * @param userBody Object of {@link Users#Users Class}
     * @return Body of created user in database as object of a {@link Users#Users class}
     * @see UsersRepositoryHandler
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users save(@Valid @RequestBody Users userBody) {
        return usersRepositoryHandler.saveUserInDB(userBody);
    }

    /**
     * Handle HTTP request method PUT on /users/uuid path, where uuid is identification for user, which we want to update from database.
     * @param userBody Object of {@link Users#Users Class}
     * @param userUUID UUID of user which we want to update in database
     * @return Body of created user in database as object of a {@link Users#Users class}
     * @see UsersRepositoryHandler
     */
    @PutMapping("/{userUUID}")
    public Users updateUser(@Valid @RequestBody Users userBody, @PathVariable UUID userUUID) {
        return usersRepositoryHandler.updateUserInDB(userBody,userUUID);
    }

    /**
     * Handle HTTP request method DELETE on /users/uuid path, where uuid is identification for user, which we want to delete from database.
     * @param userUUID UUID of user which we want to delete in database
     * @return Body of created user in database as object of a {@link Users#Users class}
     * @see UsersRepositoryHandler
     */
    @DeleteMapping("/{userUUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Users> deleteUser(@PathVariable UUID userUUID) {
        usersRepositoryHandler.removeUserFromDB(userUUID);
        return ResponseEntity.noContent().build();
    }
}
