package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarPartRepository extends JpaRepository<CarPart, Long> {

    List<CarPart> findAll();

    List<CarPart> findAllByCarPartDetailsNameContainingIgnoreCaseOrCarPartDetailsDescriptionContainingIgnoreCase(
            String nameFilter,
            String descriptionFilter
    );

    @Modifying
    @Query("update CarPart cp set cp.carPartDetails.description = ?1 where cp.id = ?2")
    int modifyCarPartDescription(String description, Long id);

}
