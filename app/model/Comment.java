package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by tito on 21/10/15.
 *
 * db.Comment.ensureIndex({tId:1, cd : -1});
 */

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseModel {

    @Property("tId")
    private ObjectId ticketId;

    @Property("txt")
    private String text;

}
