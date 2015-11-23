package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.mongodb.morphia.annotations.Embedded;

import java.io.Serializable;

/**
 * Created by tito on 22/10/15.
 */
@Data
@ToString
@Embedded
@AllArgsConstructor
@NoArgsConstructor
public class UserVO  implements Serializable{

    private String name;

    private String userId;
}
