package ru.ssau.project.blacar.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.ssau.project.blacar.data.main.Trip;
import ru.ssau.project.blacar.data.meta.Place;
import ru.ssau.project.blacar.service.TripService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController
{
    private TripService service;
    private ObjectMapper objectMapper;
    @Autowired
    private void init(TripService service, ObjectMapper objectMapper)
    {
        this.service = service;
        this.objectMapper = objectMapper;
    }


    @GetMapping("/find")
    public ResponseEntity find(@RequestParam String date, @RequestParam String fromCountry, @RequestParam String fromCity,
                               @RequestParam String toCountry, @RequestParam String toCity) {
        try {
//            System.out.println(LocalDateTime.parse(date));
            Place departure = Place.builder().country(fromCountry).city(fromCity).build();
            Place arrival = Place.builder().country(toCountry).city(toCity).build();
            List<Trip> list = new ArrayList<>();
            HttpStatus status = HttpStatus.OK;
            list = service.get(departure, arrival, LocalDateTime.parse(date));
//            System.out.println(list.toString());
//            System.out.println(fromCountry.equals("Russia"));
//            System.out.println(arrival.toString());
//            ObjectNode objectNode = objectMapper.createObjectNode();
//            objectNode.putArray("content").addAll(objectMapper.convertValue(list, ArrayNode.class));
//            System.out.println(objectNode.toString());
            return new ResponseEntity<>(list, status);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @GetMapping("/delete")
    public ResponseEntity delete(Principal principal, @RequestParam String id) {
        HttpStatus status = HttpStatus.OK;
        try {
            service.delete(principal, id);
        } catch (HttpClientErrorException e) {
            status = e.getStatusCode();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/my-trip")
    public ResponseEntity findByOwner(Principal principal) {
        HttpStatus status = HttpStatus.OK;
        List<ObjectNode> list = null;
        try {
            list = service.find(principal);
        } catch (HttpClientErrorException e) {
            status = e.getStatusCode();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(list, status);
    }

    @GetMapping("/my-plans")
    public ResponseEntity findByPassenger(Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Collection<ObjectNode> list = null;
        try {
            list = service.findByPassenger(principal);
        } catch (HttpClientErrorException e) {
            status = e.getStatusCode();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(list, status);
    }

    @PostMapping("/add")
    public ResponseEntity add(Principal principal,  @Valid @RequestBody Trip trip) {
        HttpStatus status = HttpStatus.OK;
        try {
            trip = service.add(principal, trip);
            return new ResponseEntity<>(trip, status);
        } catch (HttpClientErrorException e) {
            status = e.getStatusCode();
            return new ResponseEntity<>(e.getMessage(), status);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), status);
        }
    }
}
