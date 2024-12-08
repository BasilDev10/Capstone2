package com.example.capstone.Repository;

import com.example.capstone.Model.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, Integer> {

    PaymentSchedule findPaymentScheduleById(Integer id);

    PaymentSchedule findPaymentScheduleByUserIdAndPaymentTypeAndScheduleDate_MonthAndScheduleDateYear(Integer userId ,String PaymentType, short month , int year);
}
