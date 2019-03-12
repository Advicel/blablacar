package ru.ssau.project.blacar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.project.blacar.data.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
