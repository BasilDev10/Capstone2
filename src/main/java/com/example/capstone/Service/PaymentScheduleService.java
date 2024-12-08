package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.Loan;
import com.example.capstone.Model.PaymentSchedule;
import com.example.capstone.Repository.LoanRepository;
import com.example.capstone.Repository.PaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentScheduleService {

    private final PaymentScheduleRepository paymentScheduleRepository;
    private final LoanRepository loanRepository;

    public List<PaymentSchedule> getAllPaymentSchedules() {
        return paymentScheduleRepository.findAll();
    }

    public PaymentSchedule getPaymentScheduleById(Integer id) {
        return paymentScheduleRepository.findPaymentScheduleById(id);
    }


    public PaymentSchedule getPaymentScheduleByUserIdAndPaymentTypeAndMonthAndYear(Integer userId ,String PaymentType, short month , int year){
        return paymentScheduleRepository.findPaymentScheduleByUserIdAndPaymentTypeAndScheduleDate_MonthAndScheduleDateYear(userId,PaymentType,month,year);
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
