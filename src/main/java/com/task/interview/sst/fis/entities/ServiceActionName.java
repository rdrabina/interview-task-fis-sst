package com.task.interview.sst.fis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_action_name")
@NoArgsConstructor
@Getter
@Setter
public class ServiceActionName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String value;

    @OneToMany(
            mappedBy = "serviceActionName",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ServiceAction> serviceActions;

}
