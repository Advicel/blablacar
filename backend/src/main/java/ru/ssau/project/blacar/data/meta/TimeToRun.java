package ru.ssau.project.blacar.data.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeToRun
{
    private long years;
    private long months;
    private long days;
    private long hours;
    private long minutes;
    private long seconds;
    private long nano;

    @Override
    public String toString() {
        return " " + years + " " + months + " " + days + " " + hours + " " + minutes + " " +seconds + " " +nano;
    }
}
