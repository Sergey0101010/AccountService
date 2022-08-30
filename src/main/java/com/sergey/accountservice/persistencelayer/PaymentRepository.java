package com.sergey.accountservice.persistencelayer;

import com.sergey.accountservice.databaselayer.PaymentEntity;
import com.sergey.accountservice.databaselayer.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
    PaymentEntity findByPaymentPeriodAndUser(YearMonth paymentPeriod, UserEntity user);
}
