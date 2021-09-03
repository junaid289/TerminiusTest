package com.terminius.test.repository;

import com.terminius.test.entity.Weather;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    public List<Weather> findAllByDateAndCity(Optional<Date> date, Optional<String[]> cities, Sort sort);
}
