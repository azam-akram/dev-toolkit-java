package com.azamakram.github.clientidgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;

@Component
public class ServiceDiscoveryHelper {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.uuidgenerator.name}")
    private String uuidGeneratorServiceName;

    @Value("${application.uuidgenerator.uuid-context-path}")
    private String uuidGeneratorContextPath;    

    public String getUuidFromUuidGeneratorService() throws ServiceUnavailableException {
        // Discover uuid-generator service from consul and read message key (uuid) from it
        URI service = uuidGeneratorServerUri()
                .map(s -> s.resolve(uuidGeneratorContextPath))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate.getForObject(service, String.class);
    }


    private Optional<URI> uuidGeneratorServerUri() {
        return discoveryClient.getInstances(uuidGeneratorServiceName)
            .stream()
            .map(si -> si.getUri()).findFirst();
    }
}
