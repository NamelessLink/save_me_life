package com.lxs.otherentity;

import java.util.Date;

public class OrderDetail {
    private String orderId;

    private String state;

    private Date createDate;

    private Date expectDate;

    private Date endDate;

    private String sendAddr;

    private String userName;

    private String driverName;

    private String rName;

    private String dishName;

    private int dishPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userName = userName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverId(String driverName) {
        this.driverName = driverName;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishPrice() {
        return dishPrice;
    }
}