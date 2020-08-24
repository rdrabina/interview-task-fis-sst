package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarPartRepository extends JpaRepository<CarPart, Long> {

    List<CarPart> findAll();

    List<CarPart> findAllByCarPartDetailsNameContainingIgnoreCaseOrCarPartDetailsDescriptionContainingIgnoreCase(
            String nameFilter,
            String descriptionFilter
    );

}
