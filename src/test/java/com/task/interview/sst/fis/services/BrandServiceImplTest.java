package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.repositories.BrandRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Set;

import static com.task.interview.sst.fis.test.utils.ObjectFactory.createBrandList;
import static com.task.interview.sst.fis.test.utils.ObjectFactory.createGroupedByBrandAndModelMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class BrandServiceImplTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Test
    public void getBrandByNameWithModelsAndCarParts() {
        when(brandRepository.findAllByName(anyString()))
                .thenReturn(createBrandList());

        Map<String, Map<String, Set<CarPartDto>>> brandNameTest = brandService.getBrandByNameWithModelsAndCarParts("brand name test");

        verify(brandRepository, times(1)).findAllByName("brand name test");
        Assertions.assertEquals(brandNameTest, createGroupedByBrandAndModelMap());
    }

}