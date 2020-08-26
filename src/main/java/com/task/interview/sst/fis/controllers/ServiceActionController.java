package com.task.interview.sst.fis.controllers;

import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.services.ServiceActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/service-actions")
@CrossOrigin("*")
public class ServiceActionController {

    private final ServiceActionService serviceActionService;

    @Autowired
    public ServiceActionController(ServiceActionService serviceActionService) {
        this.serviceActionService = serviceActionService;
    }

    @GetMapping("/{start-date}/{end-date}")
    public ResponseEntity<List<ServiceActionDto>> findBetweenDates(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                   @PathVariable("start-date") Date startDate,
                                                                   @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                   @PathVariable("end-date") Date endDate) {
        return new ResponseEntity<>(
                serviceActionService.findAllBetweenDates(startDate, endDate),
                HttpStatus.OK
        );
    }
}
