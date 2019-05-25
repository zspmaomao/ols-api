package com.thoughtworks.nho.olsapi.trainingcamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class TrainingCampController {

    public TrainingCampController(@Autowired TrainingCampService trainingCampService) {
        this.trainingCampService = trainingCampService;
    }

    private final TrainingCampService trainingCampService;

    @PostMapping("trainingcamps")
    public ResponseEntity<TrainingCamp> createOne(@RequestBody TrainingCamp newCamp) {
        var createdCamp = trainingCampService.createOne(newCamp);
        return ResponseEntity.created(URI.create("/trainingcamps/" + createdCamp.getId()))
                .body(createdCamp);
    }

}
