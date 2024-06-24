package lk.ijse.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceOrderDTO {
    private OrdersDTO orders;
    private List<OrderDetailsDTO> odList;

}
