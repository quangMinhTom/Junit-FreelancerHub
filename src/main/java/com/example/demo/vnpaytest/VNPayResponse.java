package com.example.demo.vnpaytest;

public class VNPayResponse {
    String orderInfo;
    String paymentTime;
    String transactionId;
    String totalPrice;


    public VNPayResponse(String totalPrice, String transactionId, String paymentTime, String orderInfo) {
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.paymentTime = paymentTime;
        this.orderInfo = orderInfo;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
