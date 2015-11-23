package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by tito on 20/10/15.
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ticket extends BaseModel {

    private String title;

    private String type;

    private String assign;

    private String priority;

    private String c_id;

    private String c_name;

    private String c_number;

    private String description;

    private String status;

}
