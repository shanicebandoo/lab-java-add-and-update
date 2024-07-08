package com.ironhack.add_and_control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        if (employeeRepository.existsById(patient.getAdmittedBy().getEmployeeId())) {
            Patient savedPatient = patientRepository.save(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setName(patientDetails.getName());
                    patient.setDateOfBirth(patientDetails.getDateOfBirth());
                    patient.setAdmittedBy(patientDetails.getAdmittedBy());
                    patientRepository.save(patient);
                    return ResponseEntity.ok(patient);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
