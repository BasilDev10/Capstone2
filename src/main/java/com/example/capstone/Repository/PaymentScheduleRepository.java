package com.example.capstone.Repository;

import com.example.capstone.Model.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, Integer> {

    PaymentSchedule findPaymentScheduleById(Integer id);

    @Query("select p from PaymentSchedule p where p.userId =?1 and p.paymentType =?2 and p.scheduleCreatedType =?3 and month(p.scheduleDate) =?4 and year(p.scheduleDate) =?5")
    PaymentSchedule getByUserIdAndPaymentTypeAndMonthAndYear(Integer userId ,String PaymentType,String scheduleCreatedType, int month , int year);

    List<PaymentSchedule> findPaymentScheduleByGroupSavingAccountId(Integer groupSavingAccountId);
    List<PaymentSchedule> findPaymentScheduleByUserId(Integer userId);
}
