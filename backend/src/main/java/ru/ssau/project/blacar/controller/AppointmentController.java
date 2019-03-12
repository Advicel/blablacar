package ru.ssau.project.blacar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.ssau.project.blacar.data.main.Appointment;
import ru.ssau.project.blacar.service.AppointmentService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController
{
    private AppointmentService service;

    @Autowired
    private void init(AppointmentService service) {
        this.service = service;
    }
    @PostMapping("/join")
    public ResponseEntity join(Principal principal, @RequestParam String tripId) {
        try
        {
            return new ResponseEntity<>(service.joinToTrip(principal, tripId), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }



    @PostMapping("/back-join")
    public ResponseEntity backJoin(@RequestParam String id, Principal principal) {
        try {
            service.backJoinToTrip(id, principal.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @GetMapping("/appointments")
    public ResponseEntity findAppointments(@RequestParam("id") String tripId, Principal principal) {
        try {
            List<Appointment> list = service.findAppointmentsByTripId(tripId, principal.getName());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }


    @PostMapping("/back-confirm")
    public ResponseEntity backConfirm(@RequestParam("id") String appointmentId, Principal principal) {
        try {
            service.backConfirmTrip(appointmentId, principal.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
}
