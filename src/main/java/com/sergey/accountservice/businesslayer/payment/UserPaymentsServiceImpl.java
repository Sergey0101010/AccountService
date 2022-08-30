package com.sergey.accountservice.businesslayer.payment;

import com.sergey.accountservice.databaselayer.PaymentEntity;
import com.sergey.accountservice.databaselayer.UserEntity;
import com.sergey.accountservice.dto.payment.GetPaymentResponseDto;
import com.sergey.accountservice.dto.payment.OperationStatusResponseDto;
import com.sergey.accountservice.dto.payment.PaymentEntityDto;
import com.sergey.accountservice.exception.EmployeePaymentException;
import com.sergey.accountservice.persistencelayer.PaymentRepository;
import com.sergey.accountservice.persistencelayer.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserPaymentsServiceImpl implements UserPaymentsService {

    final UserRepository userRepository;

    final PaymentRepository paymentRepository;

    /**
     * Method returns employee payment for certain period of time
     */
    @Override
    public List<GetPaymentResponseDto> getPayment(UserDetails userDetails, Map<String, String> params) {

        UserDetails details2 = Optional.ofNullable(userDetails).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        UserEntity userEntity = userRepository
                .findUserEntitiesByEmailIgnoreCase(details2.getUsername());
        List<GetPaymentResponseDto> getPaymentResponseDtos = new ArrayList<>();

        //should be only one param with certain name
        if (params.size() >= 2) {
            throw new EmployeePaymentException("Wrong request params quantity");
        } else if (params.containsKey("period")) {

            YearMonth paramPeriod = YearMonth.parse(params.get("period"), DateTimeFormatter.ofPattern("MM-yyyy"));

            PaymentEntity byPaymentPeriodAndUser = paymentRepository
                    .findByPaymentPeriodAndUser(paramPeriod, userEntity);

            if (byPaymentPeriodAndUser == null) {
                return new ArrayList<>(); //if user don't have payment for this period return empty List
            } else {
                GetPaymentResponseDto getPaymentResponseDto = getGetPaymentResponseDto(userEntity, paramPeriod,
                        byPaymentPeriodAndUser);

                getPaymentResponseDtos.add(getPaymentResponseDto);
                return getPaymentResponseDtos;
            }
        } else if (params.size() == 1) {
            throw new EmployeePaymentException("Wrong request param, should be \"period\" ");
        } else {
            List<PaymentEntity> userPayments = userEntity.getUserPayments();
            userPayments = userPayments.stream()
                    .sorted(Comparator.comparing(PaymentEntity::getPaymentPeriod).reversed())
                    .collect(Collectors.toList());

            for (PaymentEntity paymentEntity : userPayments) {

                GetPaymentResponseDto getPaymentResponseDto =
                        getGetPaymentResponseDto(userEntity, paymentEntity.getPaymentPeriod(), paymentEntity);
                getPaymentResponseDtos.add(getPaymentResponseDto);

            }


            return getPaymentResponseDtos;
        }


    }

    private GetPaymentResponseDto getGetPaymentResponseDto(UserEntity userEntity,
                                                           YearMonth paramPeriod,
                                                           PaymentEntity paymentEntity) {
        GetPaymentResponseDto getPaymentResponseDto = new GetPaymentResponseDto();

        String formattedDate = getLocalDateMonthFullName(paramPeriod);
        getPaymentResponseDto.setPeriod(formattedDate);
        String salary = getStringSalaryRepresentation(paymentEntity);
        getPaymentResponseDto.setLastname(userEntity.getLastname());
        getPaymentResponseDto.setName(userEntity.getName());
        getPaymentResponseDto.setSalary(salary);
        return getPaymentResponseDto;
    }


    private String getStringSalaryRepresentation(PaymentEntity byPaymentPeriodAndUser) {
        Long salaryDollars = byPaymentPeriodAndUser.getSalary() / 100;
        Long salaryCents = byPaymentPeriodAndUser.getSalary() % 100;
        return salaryDollars + " dollar(s) " + salaryCents + " cent(s)";
    }

    private String getLocalDateMonthFullName(YearMonth paramPeriod) {
        DateTimeFormatter formatMonthToName = DateTimeFormatter.ofPattern("MMMM-yyyy");
        return paramPeriod.format(formatMonthToName);
    }


    @Override
    @Transactional
    public OperationStatusResponseDto addEmployeePayments(List<PaymentEntityDto> paymentEntityDtoList) {
        for (PaymentEntityDto paymentEntityDto :
             paymentEntityDtoList ) {

            PaymentEntityDto entityDtoNotNull = paymentDataEntityDtoRequirementCheck(paymentEntityDto);

            List<PaymentEntity> userPaymentsInDb = Optional.ofNullable(userRepository
                    .findUserEntitiesByEmailIgnoreCase(entityDtoNotNull.getEmployee())
                    .getUserPayments()).orElse(new ArrayList<>()); // creating arrayList for user payments

            boolean alreadyExistsInDb = userPaymentsInDb.stream()
                    .map(PaymentEntity::getPaymentPeriod).toList()
                    .contains(YearMonth.parse(entityDtoNotNull.getPeriod(),
                            DateTimeFormatter.ofPattern("MM-yyyy")));

            if (alreadyExistsInDb) {
                throw new EmployeePaymentException("Salary for this user for this period already exists");
            } else {
                PaymentEntity newPaymentEntity = getPaymentEntityFromDto(entityDtoNotNull);
                paymentRepository.save(newPaymentEntity);
            }

        }
        return new OperationStatusResponseDto("Added successfully!");
    }


    @Override
    public OperationStatusResponseDto updateEmployeePayment(PaymentEntityDto paymentEntityDto) {
        PaymentEntityDto entityNotNull = paymentDataEntityDtoRequirementCheck(paymentEntityDto);
        UserEntity userEntity = userRepository.findUserEntitiesByEmailIgnoreCase(entityNotNull.getEmployee());
        PaymentEntity paymentPeriodEntity = Optional
                .ofNullable(paymentRepository.findByPaymentPeriodAndUser(
                        YearMonth.parse(paymentEntityDto.getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy"))
                        , userEntity))
                .orElseThrow(() -> new EmployeePaymentException(
                        "payment for user " + paymentEntityDto.getEmployee() + " for this period not found"));
        paymentPeriodEntity.setSalary(paymentEntityDto.getSalary());
        paymentRepository.save(paymentPeriodEntity);
        return new OperationStatusResponseDto("Updated successfully!");
    }
    private PaymentEntity getPaymentEntityFromDto(PaymentEntityDto entityDtoNotNull) {
        UserEntity userForSave = userRepository
                .findUserEntitiesByEmailIgnoreCase(entityDtoNotNull.getEmployee());
        PaymentEntity newPaymentEntity = new PaymentEntity();
        newPaymentEntity.setPaymentPeriod(YearMonth.parse(entityDtoNotNull.getPeriod(),
                DateTimeFormatter.ofPattern("MM-yyyy")));
        newPaymentEntity.setUser(userForSave);
        newPaymentEntity.setSalary(entityDtoNotNull.getSalary());
        return newPaymentEntity;
    }

    private PaymentEntityDto paymentDataEntityDtoRequirementCheck(PaymentEntityDto paymentEntityDto) {
        PaymentEntityDto entityNotNull = Optional.ofNullable(paymentEntityDto).orElseThrow(() ->
                new EmployeePaymentException("entity can't be null"));

        if (!userRepository.existsByEmailIgnoreCase(entityNotNull.getEmployee())) {
            throw new EmployeePaymentException("this is not our employee(user)");
        } else if (entityNotNull.getSalary() < 0) {
            throw new EmployeePaymentException("Salary can't be negative number");
        } else {
            return entityNotNull;
        }
    }


}
