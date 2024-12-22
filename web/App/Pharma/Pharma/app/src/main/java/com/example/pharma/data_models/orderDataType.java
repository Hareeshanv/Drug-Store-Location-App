package com.example.pharma.data_models;

public class orderDataType {

    String ID;
    String OrderDate;
    String tvMedicalStoreID;
    String Status;
    String BillAmount;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getTvMedicalStoreID() {
        return tvMedicalStoreID;
    }

    public void setTvMedicalStoreID(String tvMedicalStoreID) {
        this.tvMedicalStoreID = tvMedicalStoreID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBillAmount() {
        return BillAmount;
    }

    public void setBillAmount(String billAmount) {
        BillAmount = billAmount;
    }
}
