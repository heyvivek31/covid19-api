package com.covid19.plasma.service;

import com.covid19.plasma.exception.CovidStatsTotalNodeMissingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@Service
public class CovidStatsService {

    @Autowired
    OkHttpClient client;

    @Value("${spring.statewise.endpoint}")
    private String statewiseEndpoint;

    public String getStatewiseStats() throws IOException {
        return client.doGetRequest(statewiseEndpoint);
    }

    @Cacheable(value = "totalCasesCache")
    public JsonNode getTotalStats() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = client.doGetRequest(statewiseEndpoint);
        Reader reader = new StringReader(json);
        ObjectNode node = objectMapper.readValue(reader,
                ObjectNode.class);
        if (node.get("TT") == null) {
            throw new CovidStatsTotalNodeMissingException("Covid Stats: TT node is null");
        }
        JsonNode totalNode = node.get("TT");
        return totalNode;
    }
}
