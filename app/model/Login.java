package model;

import lombok.Data;
import lombok.ToString;

/**
 * Created by tito on 22/10/15.
 */
@Data
@ToString
public class Login {
    private String username;
    private String password;
}
