package ru.ssau.project.blacar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.ssau.project.blacar.data.main.Trip;
import ru.ssau.project.blacar.data.meta.TimeToRun;
import ru.ssau.project.blacar.repository.TripRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class TripHelper
{

    private final static Logger logger = LoggerFactory.getLogger(TripHelper.class);

    private ThreadPoolTaskScheduler scheduler;
    private TripRepository repository;

    @Autowired
    private void init(ThreadPoolTaskScheduler scheduler,
                      TripRepository repository) {
        this.scheduler = scheduler;
        this.repository = repository;
   }

    public TimeToRun getTimeToRun(Trip trip)
    {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime targetTime = trip.getDate();
        return TimeToRun.builder()
                .years(ChronoUnit.YEARS.between(nowTime, targetTime))
                .months(ChronoUnit.MONTHS.between(nowTime, targetTime))
                .days(ChronoUnit.DAYS.between(nowTime, targetTime))
                .hours(ChronoUnit.HOURS.between(nowTime, targetTime))
                .minutes(ChronoUnit.MINUTES.between(nowTime, targetTime))
                .seconds(ChronoUnit.SECONDS.between(nowTime, targetTime))
                .nano(ChronoUnit.NANOS.between(nowTime, targetTime))
                .build();
    }

    public void addToScheduler(Trip trip, TimeToRun time) {
        if (time.getYears() <= 0
                && time.getMonths() <= 0
                && time.getDays() <= 0) {
            Date out = Date.from(trip.getDate().atZone(ZoneId.systemDefault()).toInstant());
            scheduler.schedule(new CompleterTrips(trip), out);
        }
    }

    @Scheduled(fixedDelay = 86400000)
    private void initScheduler() {
        List<Trip> trips = repository.findAll();
        for (Trip e : trips)
        {
            TimeToRun time = getTimeToRun(e);
            addToScheduler(e, time);
        }
    }
}
