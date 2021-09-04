package com.terminius.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;
    private Float latitude;
    private Float longitude;
    private String city;
    private String province;

    private List<Double> temperatures;

    public WeatherDto(Date date, Float latitude, Float longitude, String city, String province, List<Double> temperatures) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.province = province;
        this.temperatures = temperatures;
    }
}
