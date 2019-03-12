package ru.ssau.project.blacar.data.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Car {
    private Color color;
    private String index;
    private Brand brand;
}
