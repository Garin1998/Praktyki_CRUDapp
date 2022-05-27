package com.example.web.domain.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.StringJoiner;

@Data
public class UsersRequestBody {

    @NotBlank(message = "Please provide a name.")
    private String userName;

    @NotBlank(message = "Please provide a email.")
    @Email(message = "Email should be valid")
    private String userEmail;

    @Override
    public String toString() {
        StringJoiner joinValues = new StringJoiner(",\n","{\n","\n}");
        joinValues.add("\"userName\": " + "\"" + this.getUserName() + "\"");
        joinValues.add("\"userEmail\": " + "\"" + this.getUserEmail() + "\"");
        return joinValues.toString();
    }
}
