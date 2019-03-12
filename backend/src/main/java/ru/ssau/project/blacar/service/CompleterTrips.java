package ru.ssau.project.blacar.service;


import ru.ssau.project.blacar.data.main.Appointment;
import ru.ssau.project.blacar.data.main.Trip;

public class CompleterTrips implements Runnable
{
    private Trip trip;

    public CompleterTrips(Trip trip)
    {
        this.trip = trip;
    }

    @Override
    public void run()
    {
        AppointmentService appointmentService = BeanUtils.getBean(AppointmentService.class);
        for (Appointment appointment : appointmentService.getAppointments(trip.getId()))
        {
            appointmentService.delete(appointment.getId());
        }
        BeanUtils.getBean(TripService.class).delete(trip.getId());
    }
}
