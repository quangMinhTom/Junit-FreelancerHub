package com.example.demo.Service;

import com.example.demo.Model.Payment;
import com.example.demo.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Lấy tất cả các payment
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Lấy payment theo ID
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    // Lưu một payment mới
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Xóa payment theo ID
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
