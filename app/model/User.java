package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import java.util.UUID;

/**
 * Created by tito on 22/10/15.
 *
 * db.User.ensureIndex({un:1});
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class User extends BaseModel {

    @Property("fn")
    private String firstName;

    @Property("ln")
    private String lastName;

    @Property("un")
    private String username;

    @Property("pass")
    private String password;

    @Property("auth")
    private String authToken; //TODO make this a list to support multiple device login



}
