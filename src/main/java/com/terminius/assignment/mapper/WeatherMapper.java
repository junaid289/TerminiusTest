package com.terminius.assignment.mapper;

import com.terminius.assignment.dto.WeatherDto;
import com.terminius.assignment.entity.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by muhammad.junaid on 9/3/2021
 */
@Mapper
public interface WeatherMapper {
    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mappings({
            @Mapping(target = "latitude", source = "lat"),
            @Mapping(target = "longitude", source = "lon"),
    })
    WeatherDto mapWeatherToWeatherDto(Weather weather);

    @Mappings({
            @Mapping(target = "lat", source = "latitude"),
            @Mapping(target = "lon", source = "longitude"),
    })
    Weather mapWeatherDtoToWeather(WeatherDto weather);
}
