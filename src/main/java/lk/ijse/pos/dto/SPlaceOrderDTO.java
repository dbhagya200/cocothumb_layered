package lk.ijse.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SPlaceOrderDTO {
    private SuppOrderDTO suppOrder;
    private List<SuppDetailsDTO> sodList;
}
