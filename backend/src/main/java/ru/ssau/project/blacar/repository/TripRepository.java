package ru.ssau.project.blacar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.project.blacar.data.main.Trip;
import ru.ssau.project.blacar.data.meta.Place;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findAllByArrivalAndDepartureAndDateBetweenAndCountSeatsGreaterThan(Place arrival, Place departure, LocalDateTime date, LocalDateTime time, Integer value);
    List<Trip> findAllByDriver(String driver);
}
