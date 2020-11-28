package com.cozmus.emanagement.service;

import com.cozmus.emanagement.controller.EmployeeAlreadyExistException;
import com.cozmus.emanagement.controller.EmployeeNotFoundException;
import com.cozmus.emanagement.entity.Employee;
import com.cozmus.emanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee id " + employeeId + " does not found!"));
    }

    public Employee saveEmployee(Employee employee){
        if(employeeRepository.existsByFirstNameAndLastName(employee.getFirstName(), employee.getLastName())){
            throw new EmployeeAlreadyExistException(employee.getFirstName() + " " + employee.getLastName() + " already exist!");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee){
        findEmployeeById(employee.getId());
        return employeeRepository.save(employee);
    }

    public String deleteEmployeeById(Long employeeId){
        findEmployeeById(employeeId);
        employeeRepository.deleteById(employeeId);
        return employeeId + " is deleted.";
    }
}
