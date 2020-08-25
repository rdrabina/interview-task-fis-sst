package com.task.interview.sst.fis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sales_argument")
@NoArgsConstructor
@Getter
@Setter
public class SalesArgument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "salesArguments")
    private Set<CarPart> carParts;

    public void removeCarPart(CarPart carPart) {
        this.getCarParts().remove(carPart);
    }

}
