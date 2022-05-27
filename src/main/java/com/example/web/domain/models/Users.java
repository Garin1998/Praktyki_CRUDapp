package com.example.web.domain.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import java.util.StringJoiner;
import java.util.UUID;


/**
 * Class which is entity for Users with automatic generated Getters, Setters, Constructor and toString method via Project Lombok
 * @author  Krzysztof Kubiś
 * @version 1.0
 * @since   11
 */
@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "uuid")
    @NotNull(message = "Please provide a UUID.")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userUUID;

    @Column(name = "name")
    @NotBlank(message = "Please provide a name.")
    private String userName;

    @Column(name = "email")
    @NotBlank(message = "Please provide a email.")
    @Email(message = "Email should be valid")
    private String userEmail;

    @Column(name = "register_date")
    @CreationTimestamp
    private LocalDate userRegisterDate;

    @Override
    public String toString() {
        StringJoiner joinValues = new StringJoiner(",\n","{\n","\n}");
        joinValues.add("\"userUUID\": " + "\"" + this.getUserUUID() + "\"");
        joinValues.add("\"userName\": " + "\"" + this.getUserName() + "\"");
        joinValues.add("\"userEmail\": " + "\"" + this.getUserEmail() + "\"");
        joinValues.add("\"userRegisterDate\": " + "\"" + this.getUserRegisterDate() + "\"");

        return joinValues.toString();
    }
}
