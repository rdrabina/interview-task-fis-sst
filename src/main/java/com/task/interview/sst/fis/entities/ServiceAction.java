package com.task.interview.sst.fis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "service_action")
@NoArgsConstructor
@Getter
@Setter
public class ServiceAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_action_name_id")
    private ServiceActionName serviceActionName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "car_part_id")
    private CarPart carPart;

    public void setServiceActionName(ServiceAction serviceAction, ServiceActionName serviceActionName) {
        serviceAction.setServiceActionName(serviceActionName);
        serviceActionName.getServiceActions().add(serviceAction);
    }

    public void setCarPart(ServiceAction serviceAction, CarPart carPart) {
        serviceAction.setCarPart(carPart);
        carPart.getServiceActions().add(serviceAction);
    }

}
