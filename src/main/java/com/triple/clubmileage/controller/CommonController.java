package com.triple.clubmileage.controller;

import com.triple.clubmileage.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommonController {

    @GetMapping("/health")
    public String health() {
        log.info("health_check");
        return Constants.HEALTH;
    }

}
