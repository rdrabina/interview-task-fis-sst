package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findAllByName(String name);

}
