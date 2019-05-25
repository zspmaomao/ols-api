package com.thoughtworks.nho.olsapi.trainingcamp;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingCampController {

    TrainingCampController(TrainingCampService trainingCampService) {
        this.trainingCampService = trainingCampService;
    }

    private final TrainingCampService trainingCampService;

}
