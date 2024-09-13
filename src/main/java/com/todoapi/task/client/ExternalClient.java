package com.todoapi.task.client;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ExternalClient {

    @Value("${external.api.url}")
    private String EXTERNAL_URL;
    private final RestTemplate restTemplate;

    public ExternalClient( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getObjectsExternalApi() {
        log.info("Sending GET request to {}", EXTERNAL_URL);
        try {
            String response = restTemplate.getForObject(EXTERNAL_URL, String.class);
            log.info("Received response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Failed to fetch data from external API", e);
            throw e;
        }
    }
}
