package lesson16.models.lombok.RessponseSingleUser;

import lombok.Data;

@Data
public class SingleUserResponse {
    private User data;
    private Support support;
}
