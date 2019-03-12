package ru.ssau.project.blacar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.ssau.project.blacar.data.User;
import ru.ssau.project.blacar.data.meta.Car;
import ru.ssau.project.blacar.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService service;

    @Autowired
    private void init(UserService service) {
        this.service = service;
    }

    @Autowired
    private TokenStore tokenStore;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User user = null;
        HttpStatus status = HttpStatus.OK;
        try {
            user = service.getUserByUsername(principal.getName());
        } catch (HttpClientErrorException e) {
            status = e.getStatusCode();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(user, status);
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestParam String username, @RequestParam String password) {
        try {
            User user = service.createUser(username, password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity(e.getStatusCode());
        }
    }
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestParam(value = "refresh_token") String refresh) {
        logger.info("Logout user: " + tokenStore.readAuthenticationForRefreshToken(
                tokenStore.readRefreshToken(refresh)).getName());
        tokenStore.removeAccessTokenUsingRefreshToken(
                tokenStore.readRefreshToken(refresh));
        tokenStore.removeRefreshToken(tokenStore.readRefreshToken(refresh));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add-car")
    public ResponseEntity addCar(Principal principal, @RequestBody Car car) {
        User user = null;
        try {
            user = service.addCar(principal.getName(), car);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }


}
