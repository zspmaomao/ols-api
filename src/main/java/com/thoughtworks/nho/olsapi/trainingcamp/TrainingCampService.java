package com.thoughtworks.nho.olsapi.trainingcamp;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TrainingCampService {

    private static List<TrainingCamp> allCamps = new ArrayList<>();

    public TrainingCamp createOne(TrainingCamp newCamp) {
        newCamp.setCreatedTime(Date.from(Instant.now()));
        newCamp.setId(allCamps.size() + 1);
        allCamps.add(newCamp);
        return newCamp;
    }

    public  List<TrainingCamp> getTrainList(){
        return allCamps;
    }
}
