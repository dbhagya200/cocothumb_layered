package lk.ijse.cocothumbLayered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuppOrder {
    private String order_id;
    private String supp_id;
    private String user_id;
    private Date date;

}
