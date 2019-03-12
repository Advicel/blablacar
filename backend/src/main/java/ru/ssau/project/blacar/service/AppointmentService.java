package ru.ssau.project.blacar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.ssau.project.blacar.data.User;
import ru.ssau.project.blacar.data.main.Appointment;
import ru.ssau.project.blacar.data.main.Trip;
import ru.ssau.project.blacar.data.meta.Place;
import ru.ssau.project.blacar.repository.AppointmentRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private TripService tripService;
    private AppointmentRepository appointmentRepository;

    @Autowired
    private void init(TripService tripService,
                      AppointmentRepository appointmentRepository) {
        this.tripService = tripService;
        this.appointmentRepository = appointmentRepository;
    }


    public Appointment joinToTrip(Principal principal, String tripId) throws HttpClientErrorException {
        Trip trip = tripService.get(tripId);
        if (trip.getDriver().equals(principal.getName())) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't join in your trip");
        }
        if (trip.getCountSeats() < 1) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Places are over");
        }
        trip.setCountSeats(trip.getCountSeats() - 1);
        tripService.update(trip);
        return appointmentRepository.save(Appointment.builder()
                .passenger(principal.getName())
                .trip(tripId)
                .build());
    }


    public void backJoinToTrip(String appointmentId, String name) throws HttpClientErrorException {
        Appointment appointment = appointmentRepository.findOne(appointmentId);
        if (appointment.getPassenger().equals(name)) {
            Trip trip = tripService.get(appointment.getTrip());
            trip.setCountSeats(trip.getCountSeats() + 1);
            tripService.update(trip);
            appointmentRepository.delete(appointmentId);
        } else {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Don't access");
        }
    }


    void delete(String id) {
        appointmentRepository.delete(id);
    }

    List<Appointment> getAppointments(String id) {
        return appointmentRepository.findAllByTrip(id);
    }

    void deleteByTrip(String id) {
        for (Appointment a : getAppointments(id)) {
            appointmentRepository.delete(a.getId());
        }
    }

    List<Appointment> getAppointmentsByPassenger(String name) {
        return appointmentRepository.findAllByPassenger(name);
    }

    public void backConfirmTrip(String id, String name) throws HttpClientErrorException {
        try {
            Appointment appointment = appointmentRepository.findOne(id);
            if (tripService.get(appointment.getTrip()).getDriver().equals(name)) {
                Trip trip = tripService.get(appointment.getTrip());
                trip.setCountSeats(trip.getCountSeats() + 1);
                tripService.update(trip);
                appointmentRepository.delete(appointment);
            }
            else {
                throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    public List<Appointment> findAppointmentsByTripId(String tripId, String name) throws HttpClientErrorException {
        if (tripService.get(tripId).getDriver().equals(name))
            return appointmentRepository.findAllByTrip(tripId);
        else
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
