package com.example.web.jpa.handler;

import com.example.web.domain.exceptions.UserIdMismatchException;
import com.example.web.domain.models.Users;
import com.example.web.jpa.repositories.UsersRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.UUID;


/**
 * Class which is used for handle user request from repository
 * @author Krzysztof Kubiś
 * @version 1.0
 * @since JDK 11
 */
@Service
public class BaseUsersRepositoryHandler implements UsersRepositoryHandler {
    private final UsersRepository usersRepository;

    public BaseUsersRepositoryHandler(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Users> findAllUsersInDB(String numberOfPage) {
        Pageable selectedPageSortedByName = PageRequest.of(
                Integer.parseInt(numberOfPage),
                5,
                Sort.by("userName")
        );
        return usersRepository.findAll(selectedPageSortedByName).getContent();
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
        return usersRepository.save(userBody);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users updateUserInDB(@Valid Users userBody, UUID userUUID) {
        Users userBodyToUpdate = usersRepository.findById(userUUID).orElseThrow(UserIdMismatchException::new);
        userBodyToUpdate.setUserName(userBody.getUserName());
        userBodyToUpdate.setUserEmail(userBody.getUserEmail());
        return usersRepository.save(userBodyToUpdate);
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
