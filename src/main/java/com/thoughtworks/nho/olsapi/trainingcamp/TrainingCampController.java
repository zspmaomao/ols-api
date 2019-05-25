package com.thoughtworks.nho.olsapi.trainingcamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Date;
import java.util.List;

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

    @RequestMapping("trainList")
    public List<TrainingCamp> getTrainList() {
        TrainingCamp trainingCamp = new TrainingCamp();
        trainingCamp.setDescription("xxx");
        trainingCamp.setTitle("1212");
        trainingCamp.setStartTime(new Date());
        trainingCampService.createOne(trainingCamp);
        return trainingCampService.getTrainList();

    }

}
