package lk.ijse.cocothumbLayered.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class EmployeeDTO  {
    private String e_Id;
    private String e_Name;
    private String e_jobrole;
    private String e_Address;
    private String e_Contact;
    private double e_Salary;
    private String e_email;
    private String machine_id;



}