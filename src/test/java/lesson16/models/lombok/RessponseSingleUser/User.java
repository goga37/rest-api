package lesson16.models.lombok.RessponseSingleUser;

import lombok.Data;

@Data
public class User {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
