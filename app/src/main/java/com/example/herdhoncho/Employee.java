package com.example.herdhoncho;

import com.google.firebase.database.Exclude;

public class Employee {

    String employeeName, employeeNumber, employeeLocation;

    @Exclude
    String employeeID;

    public Employee() {
        //this is required for firebase
    }

    public Employee(String employeeName, String employeeNumber, String employeeLocation) {
        this.employeeName = employeeName;
        this.employeeNumber = employeeNumber;
        this.employeeLocation = employeeLocation;
    }

    @Exclude
    public String getEmployeeID() {
        return employeeID;
    }

    @Exclude
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeName = employeeNumber;
    }

    public String getEmployeeLocation() {
        return employeeLocation;
    }

    public void setEmployeeLocation(String employeeLocation) {
        this.employeeLocation = employeeLocation;
    }

}