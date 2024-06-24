package lk.ijse.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDTO {

    private String supp_id;
    private String supp_name;
    private String supp_address;
    private String supp_contact;
}
