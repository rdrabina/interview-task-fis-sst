package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.entities.Brand;

import java.util.List;

public interface BrandService {

    Brand getBrandById(Long id);

    List<Brand> findAll();

}
