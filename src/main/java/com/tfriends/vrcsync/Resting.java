package com.tfriends.vrcsync;

import com.tfriends.domain.CovidLocationVO;
import com.tfriends.domain.WeatherVO;
import com.tfriends.service.CovidService;
import com.tfriends.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
public class Resting {
    @Autowired
    private CovidService covidservice;

    @Autowired
    private WeatherService weatherservice;

    @GetMapping("/covid/{lname}")
    public CovidLocationVO CovidStatus (@PathVariable String lname) {
        return covidservice.LocGet(lname);
    }

    @GetMapping("/weather/{local}")
    public WeatherVO WeatherStat (@PathVariable String local) {
        return weatherservice.WeatherImport(local);
    }
}
