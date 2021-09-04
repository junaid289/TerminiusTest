package com.terminius.assignment.controller;

import com.terminius.assignment.dto.WeatherDto;
import com.terminius.assignment.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

/**
 * Created by muhammad.junaid on 9/3/2021
 */
@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {

  @Autowired
  private WeatherService weatherService;

  @PostMapping({"/", ""})
  public ResponseEntity<WeatherDto> addWeatherData(@RequestBody WeatherDto weatherModel) {
    WeatherDto weatherDto = weatherService.addWeatherData(weatherModel);
    return new ResponseEntity<WeatherDto>(weatherDto, HttpStatus.CREATED);
  }

  @GetMapping({"/", ""})
  public ResponseEntity<List<WeatherDto>> getWeatherData(@RequestParam("city") Optional<String> cities,
                                                      @RequestParam("sort") Optional<String> sort) {

    List<WeatherDto> weatherDtos = weatherService.getWeatherData(cities, sort);
    return new ResponseEntity<List<WeatherDto>>(weatherDtos, HttpStatus.OK);
  }

  @GetMapping({"/{id}", "/{id}/"})
  public ResponseEntity<WeatherDto> getWeatherDataById(@PathVariable Integer id) {
    WeatherDto weatherDto = weatherService.getWeatherDataById(id);

    if(weatherDto != null) {
      return new ResponseEntity<WeatherDto>(weatherDto, HttpStatus.OK);
    }
    else {
      return new ResponseEntity<WeatherDto>(HttpStatus.NOT_FOUND);
    }
  }
}
