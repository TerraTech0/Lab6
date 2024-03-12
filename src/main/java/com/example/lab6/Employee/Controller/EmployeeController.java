package com.example.lab6.Employee.Controller;

import com.example.lab6.Employee.ApiResponse.ApiResponse;
import com.example.lab6.Employee.Model.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/show")
    public ResponseEntity show(){
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Employee employee, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("employee added successfully!"));
    }
    @PutMapping("/update/{index}")
    public ResponseEntity update(@PathVariable int index, @RequestBody @Valid Employee employee, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.set(index, employee);
        return ResponseEntity.status(200).body(new ApiResponse("employee updated successfully!"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity delete(@PathVariable int index){
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("employee deleted successfully!"));
    }



    @GetMapping("search/{position}")
    public ResponseEntity searchByPosition(@PathVariable String position){
        ArrayList<Employee> emp = new ArrayList<>();
        for (Employee employee : employees){
            if (employee.getPosition().equalsIgnoreCase(position)){
                emp.add(employee);
            }
        }
        return ResponseEntity.status(200).body(emp);
    }

    @GetMapping("get-age/{minAge}/{maxAge}")
    public ResponseEntity getAge(@PathVariable int minAge, @PathVariable int maxAge){
        ArrayList<Employee> emp = new ArrayList<>();
        for (Employee employee : employees){
            if (employee.getAge() >= minAge && employee.getAge() <= maxAge){
                emp.add(employee);
            }
        }
        return ResponseEntity.status(200).body(emp);
    }

    //allow employees to apply for annual leave!
    //Verify that the employee exists.
    //The employee must not be on leave (the onLeave flag must be false).
    //The employee must have at least one day of annual leave remaining.
    /*
     Behavior:
        ▪ Set the onLeave flag to true.
        ▪ Reduce the annualLeave by 1.
     */
    @PutMapping("/apply/{index}")
    public ResponseEntity apply(@PathVariable int index){
        if (index < 0 || index >= employees.size()) {
            return ResponseEntity.status(400).body(new ApiResponse("index not found"));
        }
        Employee emp = employees.get(index);
        if (emp.getOnLeave() || emp.getAnnualLeave() < 1) {
            return ResponseEntity.status(400).body(new ApiResponse("cna't apply for leave!"));
        }
        emp.setOnLeave(true);
        emp.setAnnualLeave(emp.getAnnualLeave() - 1);
        if (emp.getAnnualLeave() == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Reminder! You have 1 day left!"));
        }
        return ResponseEntity.status(200).body(emp);
    }

    /*
    Get Employees with No Annual Leave: Retrieves a list of employees who have
    used up all their annual leave.
     */
    @GetMapping("/no-leave")
    public ResponseEntity getEmployeeWithNoAnnualLeave(){
        ArrayList<Employee> emp = new ArrayList<>();
        for (Employee employee : employees){
            if (employee.getAnnualLeave() == 0){
                emp.add(employee);
            }
        }
        return ResponseEntity.status(200).body(emp);
    }

    @PutMapping("/promote/{id}/{role}")
    public ResponseEntity promote(@PathVariable int id, @PathVariable String role){
        if (!role.equals("supervisor")){
            return ResponseEntity.status(400).body(new ApiResponse("Only supervisor can promote employees!"));
        }
        if (id < 0 || id >= employees.size()){
            return ResponseEntity.status(400).body(new ApiResponse("Employee not found!"));
        }
        Employee emp = employees.get(id);
        if (emp.getAge() < 30){
            return ResponseEntity.status(400).body(new ApiResponse("employee's age must be more than 30"));
        }
        if (emp.getOnLeave()){
            return ResponseEntity.status(400).body(new ApiResponse("employee can't be on leave when being promoted!"));
        }
        emp.setPosition("supervisor");
        return ResponseEntity.status(200).body(emp);
    }




}
