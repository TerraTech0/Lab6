package com.example.lab6.Employee.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty(message = "id can't be empty")
    @Size(min = 2, message = "the id length must be 2 or more!")
    private String id;

    @NotEmpty(message = "name can't be an empty!")
    @Size(min = 4, message = "length of name must be 4 or more!")
    @Pattern(regexp = "[a-zA-Z]+", message = "that contains only on charachters")
    private String name;

    @NotEmpty(message = "email can't be empty!!!")
    @Email(message = "please write the email in right format like this example@gmail.com")
    private String email;


    @Pattern(regexp = "^05.*$", message = "the number must start with 05") // start with 05
    @Size(min = 10, max = 10) //contain exactly 10 digits
    private String phoneNumber;

    @NotNull(message = "age can't be null!")
    @Positive(message = "please enter a positive number for age")
    @Min(value = 25,message = "the age must be 25 or more!")
    private Integer age;

    @NotEmpty(message = "position can't be empty!!")
    @Pattern(regexp = "^(supervisor|coordinator)$",
             message = "position must be either (supervisor) or (coordinator)!")
    private String position;

    @AssertFalse(message = "On Leave must be false!")
    private Boolean onLeave;

    @NotNull(message = "Hire Date can't be null!")
    @PastOrPresent
    private Date hireDate;


    @NotNull(message = "Anuual Leave can't be null!")
    @Positive
    private Integer annualLeave; //الاجاز السنوية
}
