package com.terminius.assignment.service;

import com.terminius.assignment.dto.WeatherDto;
import com.terminius.assignment.entity.Weather;
import com.terminius.assignment.mapper.WeatherMapper;
import com.terminius.assignment.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by muhammad.junaid on 9/3/2021
 */
@Service
public class WeatherService {


    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherDto addWeatherData(WeatherDto weatherDto) {
        Weather weatherEntityToSave = WeatherMapper.INSTANCE.INSTANCE.mapWeatherDtoToWeather(weatherDto);
        Weather createdEntity = weatherRepository.save(weatherEntityToSave);
        return WeatherMapper.INSTANCE.mapWeatherToWeatherDto(createdEntity);
    }

    public List<WeatherDto> getWeatherData(Optional<String> city, Optional<String> sort) {

        Sort sortCriteria = Sort.by("id").ascending();

        String cityname = city.orElse(null);

        List<Weather> weatherEntities =
                cityname==null?weatherRepository.findAll(sortCriteria):
                        weatherRepository.findAllByCity(cityname, sortCriteria);
        return weatherEntities.stream().map(WeatherMapper.INSTANCE::mapWeatherToWeatherDto).collect(Collectors.toList());
    }

    public WeatherDto getWeatherDataById(Integer id) {
        Optional<Weather> weatherEntity = weatherRepository.findById(id);
        return weatherEntity.isPresent()?WeatherMapper.INSTANCE.mapWeatherToWeatherDto(weatherEntity.get()):null;
    }
}
