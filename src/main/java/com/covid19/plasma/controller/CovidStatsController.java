package com.covid19.plasma.controller;

        import com.covid19.plasma.service.CovidStatsService;
        import com.fasterxml.jackson.databind.JsonNode;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.MediaType;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        import java.io.IOException;

@RestController
public class CovidStatsController {

    @Autowired
    CovidStatsService covidStatsService;

    @RequestMapping(value = "/covid/stats/statewise", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStatewiseStats() throws IOException {
        return covidStatsService.getStatewiseStats();
    }

    @RequestMapping(value = "/covid/stats/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode getTotalStats() throws IOException {
        return covidStatsService.getTotalStats();
    }
}
