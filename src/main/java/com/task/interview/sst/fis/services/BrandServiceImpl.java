package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.entities.Brand;
import com.task.interview.sst.fis.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand getBrandById(Long id) {
        //todo
        return brandRepository.findById(id).orElse(null);
    }

}
