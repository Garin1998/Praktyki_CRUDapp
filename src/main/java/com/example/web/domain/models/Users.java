package com.example.web.domain.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
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
    private UUID userUUID;

    @Column(name = "name")
    @NotBlank(message = "Please provide a name.")
    private String userName;

    @Column(name = "email")
    @NotBlank(message = "Please provide a email.")
    @Email(message = "Email should be valid")
    private String userEmail;

    @Column(name = "register_date")
    @NotNull(message = "Please provide a date.")
    @FutureOrPresent(message = "not from present nor future")
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
