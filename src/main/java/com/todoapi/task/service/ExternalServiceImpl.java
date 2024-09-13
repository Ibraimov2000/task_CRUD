package com.todoapi.task.service;

import com.todoapi.task.client.ExternalClient;
import org.springframework.stereotype.Service;

@Service
public class ExternalServiceImpl implements ExternalService {

    private final ExternalClient externalClient;

    public ExternalServiceImpl(ExternalClient externalClient) {
        this.externalClient = externalClient;
    }

    @Override
    public String fetchDataFromExternalApi() {
        return externalClient.getObjectsExternalApi();
    }
}
