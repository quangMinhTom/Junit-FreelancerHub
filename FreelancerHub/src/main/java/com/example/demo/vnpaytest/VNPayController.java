package com.example.demo.vnpaytest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class VNPayController {

    private final VNPAYService vnPayService;

    public VNPayController(VNPAYService vnPayService) {
        this.vnPayService = vnPayService;
    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submitOrder(
            @RequestBody VNPayRequest VNPayRequest, // Accepts VNPayRequest as JSON payload
            HttpServletRequest request) {

        // Construct the order info from VNPayRequest
        String orderInfo = VNPayRequest.getEmail() + '_'
                + VNPayRequest.getFirstName() + '_'
                + VNPayRequest.getLastName() + '_'
                + VNPayRequest.getPhoneNumber() + '_'
                + VNPayRequest.getAddress() + '_'
                + VNPayRequest.getUserId();

        // Extract orderTotal from VNPayRequest
        int orderTotal = VNPayRequest.getOrderTotal();
        System.out.println(VNPayRequest.email);
        // Construct the base URL
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        // Call the vnPayService to generate the payment URL
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);

        // Return the generated VNPay URL
        return vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public Map<String, Object> paymentCompleted(
            @RequestParam("vnp_OrderInfo") String orderInfo,
            @RequestParam("vnp_PayDate") String paymentTime,
            @RequestParam("vnp_TransactionNo") String transactionId,
            @RequestParam("vnp_Amount") String totalPrice,
            HttpServletRequest request) {

        // Process the payment return logic
        int paymentStatus = vnPayService.orderReturn(request);

        // Prepare the response as a JSON object
        Map<String, Object> response = new HashMap<>();
        response.put("orderInfo", orderInfo);
        response.put("paymentTime", paymentTime);
        response.put("transactionId", transactionId);
        response.put("totalPrice", totalPrice);
        response.put("paymentStatus", paymentStatus == 1 ? "SUCCESS" : "FAILED");

        System.out.println("Payment Success");
        System.out.println("Order Info: " + orderInfo);
        System.out.println("Payment Time: " + paymentTime);
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Total Price: " + totalPrice);

        return response;
    }


}