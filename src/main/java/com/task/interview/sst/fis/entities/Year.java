package com.task.interview.sst.fis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private int value;

    @OneToMany(
            mappedBy = "productionStartDate",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Model> productionStartDateModels;

    @OneToMany(
            mappedBy = "productionEndDate",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Model> productionEndDateModels;

}
