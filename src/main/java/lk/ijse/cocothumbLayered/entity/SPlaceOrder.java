package lk.ijse.cocothumbLayered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SPlaceOrder {
    private SuppOrder suppOrder;
    private List<SuppDetails> sodList;
}
