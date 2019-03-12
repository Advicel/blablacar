package ru.ssau.project.blacar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.ssau.project.blacar.data.Role;
import ru.ssau.project.blacar.data.User;
import ru.ssau.project.blacar.data.main.Appointment;
import ru.ssau.project.blacar.data.main.Trip;
import ru.ssau.project.blacar.data.meta.Place;
import ru.ssau.project.blacar.data.meta.TimeToRun;
import ru.ssau.project.blacar.repository.TripRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TripService
{
    private TripRepository repository;
    private TripHelper helper;
    private UserService userService;
    private AppointmentService appointmentService;
    private ObjectMapper objectMapper;

    @Autowired
    private void init(TripRepository repository,
                      TripHelper helper,
                      UserService userService,
                      AppointmentService appointmentService,
                      ObjectMapper objectMapper) {
        this.repository = repository;
        this.helper = helper;
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.objectMapper = objectMapper;
    }

    public void delete(Principal principal, String id) throws HttpClientErrorException {
        Trip trip = Optional.of(repository.findOne(id)).orElseThrow(() -> new HttpClientErrorException(
                HttpStatus.NOT_FOUND, "Trip with id= " + id + " is not founded"));
        if (principal.getName().equals(trip.getDriver())) {
            appointmentService.deleteByTrip(id);
            repository.delete(id);
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Access denied");
        }
    }

    public Trip add(Principal principal, Trip trip)  throws HttpClientErrorException {
        try {
            User user = userService.getUserByUsername(principal.getName());
            if (trip.getDate().isBefore(LocalDateTime.now())) {
                throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Past date");
            }
            if (user.getAuthorities().contains(Role.USER)) {
                if (user.getCars().size() == 0) {
                    throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Car is empty");
                }
                trip.setDriver(user.getUsername());
                TimeToRun time = helper.getTimeToRun(trip);
                helper.addToScheduler(trip, time);
                System.out.println(trip.getDriver());
                System.out.println(user.getUsername());
                return repository.save(trip);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Access Denied");
            }
        } catch (UsernameNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<Trip> get(Place departure, Place arrival, LocalDateTime date) {
        List<Trip> list = repository.findAllByArrivalAndDepartureAndDateBetweenAndCountSeatsGreaterThan(arrival, departure, date.minusNanos(1), date.plusDays(2), 0);
        list.sort(((trip1, trip2) -> {
            if (trip1.getDate().isAfter(trip2.getDate())) {
                return 1;
            } else if (trip1.getDate().isEqual(trip2.getDate())) {
                return 0;
            } else {
                return -1;
            }
        }));
        return list;
    }

    public List<ObjectNode> find(Principal principal) {
        List<Trip> listOfTrip = repository.findAllByDriver(principal.getName());
        List<ObjectNode> objects = new ArrayList<>(listOfTrip.size());
        for (Trip trip: listOfTrip) {
            Collection<Appointment> list = appointmentService.getAppointments(trip.getId());
            ObjectNode objectNode = objectMapper.valueToTree(trip);
            objectNode.putArray("appointments").addAll(objectMapper.convertValue(list, ArrayNode.class));
//            ArrayNode arrayNode = objectNode.putArray("appointments");
//            for (Appointment appointment: list) {
//                arrayNode.add(objectMapper.valueToTree(appointment));
//            }
            objects.add(objectNode);
        }
        return objects;
    }

    public List<ObjectNode> findByPassenger(Principal principal) {
        List<Appointment> list = appointmentService.getAppointmentsByPassenger(principal.getName());
        List<ObjectNode> listOfObjects = new ArrayList<>(list.size());
        for (Appointment appointment: list) {
            Trip trip = repository.findOne(appointment.getTrip());
            ObjectNode objectNode = objectMapper.valueToTree(trip);
            objectNode.putObject("appointment").setAll(objectMapper.convertValue(appointment, ObjectNode.class));
            listOfObjects.add(objectNode);
        }
        return listOfObjects;
    }

    void delete(String trip) {
        repository.delete(trip);
    }

    Trip get(String tripId) {
        return repository.findOne(tripId);
    }
    void update(Trip trip) {
        repository.save(trip);
    }
}

