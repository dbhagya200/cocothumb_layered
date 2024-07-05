package lk.ijse.cocothumbLayered.dto;

import javafx.collections.ObservableList;
import lk.ijse.cocothumbLayered.view.tdm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersDTO {

    private String order_id;
    private String cust_NIC;
    private String cust_id;
    private String user_id;
    private Date order_date;
    List<OrderDetailsDTO> orderDetails;

    public OrdersDTO(String orderId, String custNic, String custId, String userId, LocalDate orderDate, ObservableList<CartTm> orderDetails) {
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

}
