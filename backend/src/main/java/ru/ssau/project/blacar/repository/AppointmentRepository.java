package ru.ssau.project.blacar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.project.blacar.data.main.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepository  extends MongoRepository<Appointment, String>
{
    List<Appointment> findAllByPassenger(String passenger);
    List<Appointment> findAllByPassengerAndTrip(String passenger, String trip);

    List<Appointment> findAllByTrip(String trip);
}
