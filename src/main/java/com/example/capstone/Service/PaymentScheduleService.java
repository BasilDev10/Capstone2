package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.Loan;
import com.example.capstone.Model.PaymentSchedule;
import com.example.capstone.Repository.LoanRepository;
import com.example.capstone.Repository.PaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentScheduleService {

    private final PaymentScheduleRepository paymentScheduleRepository;

    public List<PaymentSchedule> getAllPaymentSchedules() {
        return paymentScheduleRepository.findAll();
    }
    public List<PaymentSchedule> getAllPaymentSchedulesByUserId(Integer userId) {
        return paymentScheduleRepository.findPaymentScheduleByUserId(userId).stream().sorted(Comparator.comparing(PaymentSchedule::getScheduleDate)).toList();
    }
    public PaymentSchedule getPaymentScheduleById(Integer id) {
        return paymentScheduleRepository.findPaymentScheduleById(id);
    }

    public List<PaymentSchedule> getByGroupSavingAccountId(Integer groupSavingAccountId){
        return paymentScheduleRepository.findPaymentScheduleByGroupSavingAccountId(groupSavingAccountId);
    }

    public PaymentSchedule getPaymentScheduleByUserIdAndPaymentTypeAndMonthAndYear(Integer userId ,String PaymentType,String scheduleCreatedType, int month , int year){
        return paymentScheduleRepository.getByUserIdAndPaymentTypeAndMonthAndYear(userId,PaymentType,scheduleCreatedType,month,year);

    }
    public void addPaymentSchedule(PaymentSchedule paymentSchedule) {
        paymentScheduleRepository.save(paymentSchedule);
    }


    public void updatePaymentSchedule(Integer id, PaymentSchedule paymentSchedule) {
        if (paymentScheduleRepository.findPaymentScheduleById(id) == null) {
            throw new ApiException("Error: PaymentSchedule not found!");
        }
        paymentSchedule.setId(id);
        paymentScheduleRepository.save(paymentSchedule);
    }

    public void deletePaymentSchedule(Integer id) {
        if (paymentScheduleRepository.findPaymentScheduleById(id) == null) {
            throw new ApiException("Error: PaymentSchedule not found!");
        }
        paymentScheduleRepository.deleteById(id);
    }
}
