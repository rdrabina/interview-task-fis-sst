package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.entities.ServiceActionName;
import com.task.interview.sst.fis.repositories.ServiceActionNameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceActionNameServiceImplTest {

    @InjectMocks
    private ServiceActionNameServiceImpl serviceActionNameService;

    @Mock
    private ServiceActionNameRepository serviceActionNameRepository;

    @Test
    public void save() {
        ServiceActionName serviceActionName = new ServiceActionName();
        when(serviceActionNameRepository.save(any(ServiceActionName.class)))
                .thenReturn(serviceActionName);

        serviceActionNameService.save(serviceActionName);

        verify(serviceActionNameRepository, times(1)).save(serviceActionName);
    }

    @Test
    public void findByName() {
        ServiceActionName serviceActionName = new ServiceActionName();
        when(serviceActionNameRepository.findByValue(anyString()))
                .thenReturn(Optional.of(serviceActionName));

        Optional<ServiceActionName> foundByName = serviceActionNameService.findByName("service action name test");

        verify(serviceActionNameRepository, times(1)).findByValue("service action name test");
    }

}