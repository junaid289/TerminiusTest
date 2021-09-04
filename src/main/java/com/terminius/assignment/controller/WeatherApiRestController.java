package com.terminius.assignment.controller;

import com.terminius.assignment.entity.Weather;
import com.terminius.assignment.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by muhammad.junaid on 9/3/2021
 */
@RestController
@RequestMapping("weather")
public class WeatherApiRestController {

  @Autowired
  private WeatherRepository weatherRepository;

  @PostMapping({"/", ""})
  public ResponseEntity<Weather> addWeatherData(@RequestBody Weather weatherModel) {
    Weather weatherEntity = weatherRepository.save(weatherModel);
    System.out.println(weatherEntity.toString());
    return new ResponseEntity<>(weatherEntity, HttpStatus.CREATED);
  }

  @GetMapping({"/", ""})
  public ResponseEntity<List<Weather>> getWeatherData(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date,
                                                      @RequestParam("city") Optional<String> cities,
                                                      @RequestParam("sort") Optional<String> sort) {

    Sort sortCriteria = Sort.by("id").ascending();
    if(sort.isPresent()) {
      if(sort.get().startsWith("-date")) {
        sortCriteria = sortCriteria.and(Sort.by(Sort.Direction.ASC, "date"));
      }
      else {
        sortCriteria = sortCriteria.and(Sort.by(Sort.Direction.DESC, "date"));
      }
    }
    Optional<String[]> citiesArray = cities.isPresent()?
            Optional.of(cities.get().split(",")): Optional.empty();

    List<Weather> weatherEntities = weatherRepository.findAllByDateAndCity(date, citiesArray, sortCriteria);
    return new ResponseEntity<List<Weather>>(weatherEntities, HttpStatus.OK);
  }

  @GetMapping({"/{id}", "/{id}/"})
  public ResponseEntity<Weather> getWeatherDataById(@PathParam("id") Integer id) {
    Optional<Weather> weatherEntity = weatherRepository.findById(id);
    if(weatherEntity.isPresent()) {
      return new ResponseEntity<Weather>(weatherEntity.get(), HttpStatus.OK);
    }
    else {
      return new ResponseEntity<Weather>(HttpStatus.NOT_FOUND);
    }
  }
}
