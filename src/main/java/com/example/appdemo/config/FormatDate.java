package com.example.appdemo.config;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FormatDate {

    public String formatDateTime(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String formattedString = date.format(formatter);
        return formattedString;
    }

}
