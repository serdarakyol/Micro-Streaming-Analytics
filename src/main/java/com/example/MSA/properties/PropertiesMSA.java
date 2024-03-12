package com.example.MSA.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PropertiesMSA implements ApplicationRunner {

    private String host;

    public String getHost() {
        return host;
    }

    @Value("${msa.host}")
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Host initialized on: ".concat(host));
    }
    
}
