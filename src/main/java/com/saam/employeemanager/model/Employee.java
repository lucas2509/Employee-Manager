package com.saam.employeemanager.model;

import java.sql.Date;

// Classe que representa a entity employee do banco de dados
public class Employee {
    private final int id;
    private int accountId;
    private String name;
    private Date admissionDate;
    private float salaryValue;
    private boolean status;

    public Employee(int id, int accountId, String name, Date admissionDate, float salaryValue, boolean status) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.admissionDate = admissionDate;
        this.salaryValue = salaryValue;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }
    
    public void setAccountId(int id){
        this.accountId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public float getSalaryValue() {
        return salaryValue;
    }

    public void setSalaryValue(float salaryValue) {
        this.salaryValue = salaryValue;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
