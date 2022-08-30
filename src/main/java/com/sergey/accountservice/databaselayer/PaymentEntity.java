package com.sergey.accountservice.databaselayer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.YearMonth;

@Entity
@Table(name = "payments",
        uniqueConstraints = { @UniqueConstraint(name = "periodAndUserConstraint",
                columnNames = { "period", "employee_id" })})
@Data
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "period")
    private YearMonth paymentPeriod;

    @Column(name = "salary")
    private Long salary;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private UserEntity user;

}
