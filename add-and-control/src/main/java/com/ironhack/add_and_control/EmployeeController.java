package com.ironhack.add_and_control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<Employee> addDoctor(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Employee> changeStatus(@PathVariable Long id, @RequestBody Status status) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setStatus(status);
                    employeeRepository.save(employee);
                    return ResponseEntity.ok(employee);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}/department")
    public ResponseEntity<Employee> updateDepartment(@PathVariable Long id, @RequestBody String department) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setDepartment(department);
                    employeeRepository.save(employee);
                    return ResponseEntity.ok(employee);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
