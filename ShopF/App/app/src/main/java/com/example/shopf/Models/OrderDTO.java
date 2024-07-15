package com.example.shopf.Models;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private int userId;
    private Date orderDate;
    private Double total;
    private String orderAddress;
    private int statusId;
    private Date createdAt;
    private Date updatedAt;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO() {
    }

    public OrderDTO(int orderId, int userId, Date orderDate, Double total, String orderAddress, int statusId, Date createdAt, Date updatedAt, List<OrderDetailDTO> orderDetails) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.total = total;
        this.orderAddress = orderAddress;
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderDetails = orderDetails;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
