package lk.ijse.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String cust_id;
    private String cust_NIC;
    private String cust_name;
    private String cust_address;
    private String cust_contact;
    private String user_id;
}
