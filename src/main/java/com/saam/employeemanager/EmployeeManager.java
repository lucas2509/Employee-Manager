package com.saam.employeemanager;

import com.saam.employeemanager.controller.EmployeeManagerController;

public class EmployeeManager {
    
    public static void main(String[] args) {

        // Instancializa e inicia o controlador inicial
        EmployeeManagerController employeeManagerController = new EmployeeManagerController();
        employeeManagerController.init();
    }
}
