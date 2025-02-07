package lk.ijse.cocothumbLayered.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDTO {

    private String pay_id;
    private String cust_id;
    private String pay_method;
    private double t_price;
    private Date date;
}
