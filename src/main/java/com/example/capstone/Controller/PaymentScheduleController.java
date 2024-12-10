package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.PaymentSchedule;
import com.example.capstone.Service.PaymentScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-schedule")
@RequiredArgsConstructor
public class PaymentScheduleController {

    private final PaymentScheduleService paymentScheduleService;

    @GetMapping("/get")
    public ResponseEntity<List<PaymentSchedule>> getAllPaymentSchedules() {
        return ResponseEntity.ok(paymentScheduleService.getAllPaymentSchedules());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentSchedule> getPaymentScheduleById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentScheduleService.getPaymentScheduleById(id));
    }

    // endpoint 10
    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity getAllPaymentSchedulesByUserId(@PathVariable Integer userId){
        return ResponseEntity.ok(paymentScheduleService.getAllPaymentSchedulesByUserId(userId));
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addPaymentSchedule(@RequestBody @Valid PaymentSchedule paymentSchedule) {
        paymentScheduleService.addPaymentSchedule(paymentSchedule);
        return ResponseEntity.status(201).body(new ApiResponse("PaymentSchedule added successfully"));
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updatePaymentSchedule(@PathVariable Integer id,@RequestBody @Valid PaymentSchedule paymentSchedule) {
        paymentScheduleService.updatePaymentSchedule(id, paymentSchedule);
        return ResponseEntity.ok(new ApiResponse("PaymentSchedule updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePaymentSchedule(@PathVariable Integer id) {
        paymentScheduleService.deletePaymentSchedule(id);
        return ResponseEntity.ok(new ApiResponse("PaymentSchedule deleted successfully"));
    }
}
