package com.thoughtworks.nho.olsapi.trainingcamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingCampController.class)
class TrainingCampControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingCampService trainingCampService;

    private static ObjectMapper jacksonDataMapper;

    @BeforeAll
    static void initializeJacksonMapper() {
        jacksonDataMapper = new ObjectMapper();
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");
        jacksonDataMapper.setDateFormat(dateFormat);
    }

    private TrainingCamp buildTrainingCamp() {
        var trainingCamp = new TrainingCamp();
        trainingCamp.setCreatedTime(Date.from(Instant.now()));
        trainingCamp.setStartTime(Date.from(Instant.now()));
        trainingCamp.setTitle("New Camp");
        trainingCamp.setDescription("Test Camp");
        return trainingCamp;
    }

    @Nested
    class createItem {
        @Nested
        class when_an_item_is_given {
            @org.junit.jupiter.api.Test
            void should_create_new_training_camp() throws Exception {
                var createdTrainingCamp = buildTrainingCamp();
                var payload = jacksonDataMapper.writeValueAsString(createdTrainingCamp);
                when(trainingCampService.createOne(any(TrainingCamp.class))).thenReturn(createdTrainingCamp);
                mockMvc.perform(post("/trainingcamps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(payload));
            }
        }
    }


}