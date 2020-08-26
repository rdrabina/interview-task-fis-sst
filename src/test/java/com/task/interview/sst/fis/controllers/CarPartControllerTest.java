package com.task.interview.sst.fis.controllers;


import com.google.gson.Gson;
import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.services.CarPartService;
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

import java.util.Date;
import java.util.HashMap;

import static com.task.interview.sst.fis.test.utils.ObjectFactory.createCarPartDto;
import static com.task.interview.sst.fis.test.utils.ObjectFactory.createServiceActionDto;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarPartControllerTest {

    private MockMvc mockMvc;

    private Gson gson = new Gson();

    @InjectMocks
    private CarPartController carPartController;

    @Mock
    private CarPartService carPartService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carPartController)
                .build();
    }

    @Test
    public void getAllGroupedByBrandAndModel() throws Exception {
        when(carPartService.getAllGroupedByBrandAndModel())
                .thenReturn(new HashMap<>());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/car-parts/grouped-by-brand-and-model")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(carPartService, times(1)).getAllGroupedByBrandAndModel();
    }

    @Test
    public void getAllGroupedByBrandAndModelWithFilter() throws Exception {
        when(carPartService.getAllGroupedByBrandAndModelWithFilter(anyString()))
                .thenReturn(new HashMap<>());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/car-parts/grouped-by-brand-and-model/{car-part-name-description-filter}",
                        "car")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(carPartService, times(1)).getAllGroupedByBrandAndModelWithFilter(anyString());
    }

    @Test
    public void checkAvailability() throws Exception {
        when(carPartService.checkAvailabilityAndShipmentDate(anyLong()))
                .thenReturn(new CarPartAvailabilityDto(true, new Date()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/car-parts/{id}/availability",
                        1L)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(carPartService, times(1)).checkAvailabilityAndShipmentDate(anyLong());
    }

    @Test
    public void modifyCarPart() throws Exception {
        when(carPartService.modifyCarPart(anyLong(), any(CarPartDto.class)))
                .thenReturn(1);
        CarPartDto carPartDto = createCarPartDto();

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/car-parts/{id}", 1L)
                        .content(gson.toJson(carPartDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(carPartService, times(1)).modifyCarPart(1L, carPartDto);
    }

    @Test
    public void addServiceAction() throws Exception {
        doNothing().when(carPartService)
                .addServiceAction(anyLong(), any());
        ServiceActionDto serviceActionDto = createServiceActionDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/car-parts/{id}/service-actions",
                        1L)
                        .content(gson.toJson(serviceActionDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(carPartService, times(1)).addServiceAction(1L, serviceActionDto);
    }

    @Test
    public void deleteSalesArguments() throws Exception {
        when(carPartService.deleteSalesArguments(anyLong()))
                .thenReturn(5);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/car-parts/{id}/sales-arguments",
                        1L)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk());

        verify(carPartService, times(1)).deleteSalesArguments(1L);
    }

}