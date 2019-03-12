package ru.ssau.project.blacar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.ssau.project.blacar.data.Role;
import ru.ssau.project.blacar.data.User;
import ru.ssau.project.blacar.data.meta.Car;
import ru.ssau.project.blacar.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService
{
    private UserRepository repository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    private void setEncoder(BCryptPasswordEncoder encoder)
    {
        this.encoder = encoder;
    }

    @Autowired
    private void setRepository(UserRepository repository)
    {
        this.repository = repository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = Optional.of(repository.findOne(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        return user;
    }

    public User createUser(String username, String password) throws HttpClientErrorException
    {
        if (username.length() != 10 || Pattern.matches(username, "(^$|[0-9]{10})")) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "length username must be ten");
        }
        return repository.save(User.builder()
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .username(username)
            .password(encoder.encode(password))
            .authorities(Arrays.asList(Role.USER))
                .cars(new ArrayList<>())
                .build());
    }


    public User getUserByUsername(String name) {
        return Optional.of(repository.findOne(name))
                .orElseThrow(() -> new UsernameNotFoundException(name));
    }

    public User addCar(String name, Car car) {
        User user = getUserByUsername(name);
        user.getCars().add(car);
        return repository.save(user);
    }
}
