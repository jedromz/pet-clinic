package com.jedromz.petclinic.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Locale;
import java.util.Set;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper(Set<Converter> converters) {
        ModelMapper modelMapper = new ModelMapper();
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }

}
