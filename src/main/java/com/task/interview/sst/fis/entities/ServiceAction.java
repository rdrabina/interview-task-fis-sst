package com.task.interview.sst.fis.entities;

import com.task.interview.sst.fis.exceptions.StartDateAfterEndDateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "car_part_id")
    private CarPart carPart;

    public void setStartDate(Date startDate) {
        if (Objects.nonNull(endDate) && endDate.getTime() < startDate.getTime()) {
            throw new StartDateAfterEndDateException();
        }

        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        if (Objects.nonNull(startDate) && endDate.getTime() < startDate.getTime()) {
            throw new StartDateAfterEndDateException();
        }

        this.endDate = endDate;
    }

    public void setServiceActionName(ServiceAction serviceAction, ServiceActionName serviceActionName) {
        serviceAction.setServiceActionName(serviceActionName);
        serviceActionName.getServiceActions().add(serviceAction);
    }

    public void setCarPart(ServiceAction serviceAction, CarPart carPart) {
        serviceAction.setCarPart(carPart);
        carPart.getServiceActions().add(serviceAction);
    }

    public String getServiceActionNameValue() {
        return this.getServiceActionName().getValue();
    }

    public String getCarPartName() {
        return this.getCarPart().getCarPartName();
    }

}
