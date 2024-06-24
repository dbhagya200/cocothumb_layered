package lk.ijse.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private String user_id;
    private String u_name;
    private String u_password;
    private String u_email;
    private String u_role;
    private String e_id;
}
