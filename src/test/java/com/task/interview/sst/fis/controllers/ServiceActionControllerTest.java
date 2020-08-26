package com.task.interview.sst.fis.controllers;


import com.task.interview.sst.fis.services.ServiceActionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceActionControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ServiceActionController serviceActionController;

    @Mock
    private ServiceActionService serviceActionService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceActionController)
                .build();
    }

    @Test
    public void findBetweenDates() throws Exception {
        when(serviceActionService.findAllBetweenDates(any(Date.class), any(Date.class)))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/service-actions/{start-date}/{end-date}",
                        "2020-05-12", "2020-05-19")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(serviceActionService, times(1)).findAllBetweenDates(any(Date.class), any(Date.class));
    }

}