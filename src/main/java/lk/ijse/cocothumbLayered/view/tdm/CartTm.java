package lk.ijse.cocothumbLayered.view.tdm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTm {
    private String item_code;
    private int qty;
    private String description;
    private double unit_price;
    private double amount;
    private String pay_method;
    private String email;
    private JFXButton btnRemove;

    public void setTotal(double amount) {

    }
}
