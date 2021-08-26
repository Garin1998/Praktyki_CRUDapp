package com.example.web.jpa.handler;

import com.example.web.domain.exceptions.UserIdMismatchException;
import com.example.web.domain.models.Users;

import java.util.UUID;

/**
 * Interface which is used for using methods implemented in {@link BaseUsersRepositoryHandler Class}
 * @author  Krzysztof Kubiś
 * @version 1.0
 * @since   JDK 11
 */
public interface UsersRepositoryHandler {

    /**
     * Find all user in database
     * @return Body of founded users in database as object of {@link Users#Users class}
     */
    Iterable<Users> findAllUsersInDB();

    /**
     * Find user in database with provided UUID
     * @param userUUID UUID of user which we want to find in database
     * @return Body of founded user in database as object of {@link Users#Users class}
     * @throws UserIdMismatchException user with such ID doesn't exist
     */
    Users findUserByUUID(UUID userUUID) throws UserIdMismatchException;

    /**
     * Save User in DB with provided Object of {@link Users#Users Class}
     * @param userBody Object of {@link Users#Users Class}
     * @return Body of created user in database as object of {@link Users#Users class} or throws the {@link UserIdMismatchException Exception} when already exist
     * @throws UserIdMismatchException user with such ID doesn't exist
     */
    Users saveUserInDB(Users userBody) throws UserIdMismatchException;

    /**
     * Find user in database and update with provided object of {@link Users#Users Class} and UUID
     * @param userBody Object of {@link Users#Users Class}
     * @param userUUID UUID of user which we want to find in database and update
     * @return Body of founded and updated user in database as object of {@link Users#Users class}
     * @throws UserIdMismatchException user with such ID doesn't exist
     */
    Users updateUserInDB(Users userBody, UUID userUUID);

    /**
     * Find user in database and delete with provided UUID
     * @param userUUID UUID of user which we want to find in database and update
     * @throws UserIdMismatchException user with such ID doesn't exist
     */
    void removeUserFromDB(UUID userUUID);
}
