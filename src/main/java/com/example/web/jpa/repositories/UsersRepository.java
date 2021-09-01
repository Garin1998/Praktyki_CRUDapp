package com.example.web.jpa.repositories;

import com.example.web.domain.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Interface which is inherited by {@link CrudRepository CrudRepository}
 * @author Krzysztof Kubiś
 * @version 1.0
 * @since JDK 11
 */

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
}
