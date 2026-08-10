package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;

@Service
public class PickupService {

    private final OrderDAO orderDAO;

    public PickupService(
            OrderDAO orderDAO) {

        this.orderDAO = orderDAO;
    }

    /*
     * 予約情報を登録する
     */
    public boolean reserve(
            Order order) {

        if (order == null) {
            return false;
        }

        if (order.getCustomerName() == null
                || order.getCustomerName().isBlank()) {
            return false;
        }

        if (order.getEmail() == null
                || order.getEmail().isBlank()) {
            return false;
        }

        if (order.getPhone() == null
                || order.getPhone().isBlank()) {
            return false;
        }

        if (order.getProductId() <= 0) {
            return false;
        }

        if (order.getReservationDate() == null) {
            return false;
        }

        if (order.getReservationTime() == null) {
            return false;
        }

        return orderDAO.insert(order);
    }
}