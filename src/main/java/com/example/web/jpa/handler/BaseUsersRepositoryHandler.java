package com.example.web.jpa.handler;

import com.example.web.domain.exceptions.UserIdMismatchException;
import com.example.web.domain.models.Users;
import com.example.web.jpa.repositories.UsersRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.UUID;


/**
 * Class which is used for handle user request from repository
 * @author Krzysztof Kubiś
 * @version 1.0
 * @since JDK 11
 */
@Repository
public class BaseUsersRepositoryHandler implements UsersRepositoryHandler {
    private final UsersRepository usersRepository;

    public BaseUsersRepositoryHandler(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<Users> findAllUsersInDB() {
        return usersRepository.findAll();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Users findUserByUUID(UUID userUUID) throws UserIdMismatchException {
        return usersRepository.findById(userUUID).orElseThrow(UserIdMismatchException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users saveUserInDB(Users userBody) throws UserIdMismatchException {
        if(usersRepository.findById(userBody.getUserUUID()).isEmpty()) {
           return usersRepository.save(userBody);
        }
        throw new UserIdMismatchException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users updateUserInDB(@Valid Users userBody, UUID userUUID) {
        usersRepository.findById(userUUID).orElseThrow(UserIdMismatchException::new);
        return usersRepository.save(userBody);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUserFromDB(UUID userUUID) {
        usersRepository.findById(userUUID).orElseThrow(UserIdMismatchException::new);
        usersRepository.deleteById(userUUID);
    }
}
