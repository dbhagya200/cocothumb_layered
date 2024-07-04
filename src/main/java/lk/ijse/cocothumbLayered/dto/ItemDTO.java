package lk.ijse.cocothumbLayered.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
String item_code;
String item_type;
double unit_price;
double unit_price_forCompany;
String stock_qty;
String user_id;

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }



}
