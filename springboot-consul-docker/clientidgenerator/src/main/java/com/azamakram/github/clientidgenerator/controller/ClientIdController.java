package com.azamakram.github.clientidgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.naming.ServiceUnavailableException;

@RestController
public class ClientIdController {

    @Autowired
    private ServiceDiscoveryHelper sdHelper;

    public ClientIdController(ServiceDiscoveryHelper serviceDiscovery) {
        this.sdHelper = serviceDiscovery;
    }

    
    @GetMapping(path = "/{name}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getHelloMessageString(
        @PathVariable(value = "name") final String name) throws ServiceUnavailableException {

        String msgId = sdHelper.getUuidFromUuidGeneratorService();
        return "Hello " + name + "-" + msgId;
    }

}