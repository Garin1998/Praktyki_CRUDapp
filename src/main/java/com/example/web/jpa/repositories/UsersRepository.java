package com.example.web.jpa.repositories;

import com.example.web.domain.models.Users;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

/**
 * Interface which is inherited by {@link CrudRepository CrudRepository}
 * @author Krzysztof Kubiś
 * @version 1.0
 * @since JDK 11
 */
public interface UsersRepository extends CrudRepository<Users, UUID> {
}
