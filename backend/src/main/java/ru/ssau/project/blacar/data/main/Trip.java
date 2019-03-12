package ru.ssau.project.blacar.data.main;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.ssau.project.blacar.data.meta.Place;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "trips")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip
{
    @Id
    private String id;
    @NotNull
    private String description;
    @NotNull
    private Place departure;
    @NotNull
    private Place arrival;
    @NotNull
    private Double price;
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    private LocalDateTime date;
    @NotNull
    @Min(1)
    private Integer countSeats;
    private String driver;
}
