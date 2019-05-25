package com.thoughtworks.nho.olsapi.trainingcamp;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrainingCampServiceTest {

    private TrainingCampService service;

    @BeforeEach
    void setUp() {
        service = new TrainingCampService();
    }

    @org.junit.jupiter.api.Test
    void should_create_new_camp() {
        var givenItem = new TrainingCamp();
        var itemService = new TrainingCampService();
        var actualItem = itemService.createOne(givenItem);
        assertEquals(1, actualItem.getId());
        assertNotNull(actualItem.getCreatedTime());
    }

}