package com.task.interview.sst.fis.controllers;

import com.task.interview.sst.fis.services.BrandService;
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

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)

public class BrandControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BrandController brandController;

    @Mock
    private BrandService brandService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandController)
                .build();
    }

    @Test
    public void getBrand() throws Exception {
        when(brandService.getBrandByNameWithModelsAndCarParts(anyString()))
                .thenReturn(new HashMap<>());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/brands/{name}", "BMW")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(brandService, times(1)).getBrandByNameWithModelsAndCarParts("BMW");
    }

}