package lk.ijse.cocothumbLayered.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailsDTO {
    private String item_code;
    private String order_id;
    private int qty;
    private String description;
    private double unit_price;
    private double amount;
    private String pay_method;
    private String email;


}
