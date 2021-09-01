package com.example.web.jpa.controller;

import com.example.web.domain.models.Users;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CRUDIntegrityTests {
//    private static final String API_ROOT = "http://localhost:8081/users";
//    protected RequestSpecification request;
//
//    @Before
//    public void authorization() {
//        request = RestAssured
//                .given()
//                .auth()
//                .basic("test_user", "test_userPass");
//    }
//
//    public Users createUser() {
//        Users users = new Users();
//        users.setUserUUID(UUID.randomUUID());
//        users.setUserName("Jan Kowalski");
//        users.setUserEmail("example@example.com");
//        users.setUserRegisterDate(LocalDate.now());
//        return users;
//    }
//
//    public void deleteUser(Users users) {
//        String location = createUserAsUri(users);
//        request.delete(location);
//    }
//
//    private String createUserAsUri(Users users) {
//        Response response = request
//                .given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(users)
//                .post(API_ROOT);
//
//        return API_ROOT + "/" + response.jsonPath().get("userUUID");
//    }
//
//    @Test
//    public void whenGetAllUsers_thenOK() {
//        Response response = request.get(API_ROOT);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//    }
//
//    @Test
//    public void whenGetUserByID_thenOK() {
//        Users users = createUser();
//        String location = createUserAsUri(users);
//        Response response = request.get(location);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        assertEquals(users.getUserName(), response.jsonPath().get("userName"));
//        this.deleteUser(users);
//    }
//
//    @Test
//    public void whenCreateNewUser_thenCreated() {
//        Users users = createUser();
//
//        Response response = request
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(users)
//                .post(API_ROOT);
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
//        this.deleteUser(users);
//    }
//
//    @Test
//    public void whenUpdateExistedUser_thenOk() {
//        Users users = createUser();
//        String location = createUserAsUri(users);
//
//        users.setUserName("Jan Nowak");
//
//        Response response = request
//                .given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(users)
//                .put(location);
//
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        response = request.get(location);
//
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        assertEquals("Jan Nowak", response.jsonPath().get("userName"));
//        this.deleteUser(users);
//    }
//
//    @Test
//    public void whenDeleteExistedUser_thenOk() {
//        Users users = createUser();
//        String location = createUserAsUri(users);
//
//        Response response = request
//                .given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(users)
//                .delete(location);
//
//        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
//        response = request.get(location);
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
//    }
//
//    @Test
//    public void whenGetNonExistUserByID_thenError() {
//        Response response = request.get(API_ROOT + "/" + randomNumeric(4));
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
//    }

}
