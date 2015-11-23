package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by tito on 21/10/15.
 */

@Data
@ToString
public class BaseModel {

    @Id
    @JsonIgnore
    private ObjectId id;

    @Property("cd")
    private Long createdDate;

    @Embedded
    private UserVO createdBy;

    @JsonGetter("id")
    public String getStringId(){
        return this.getId().toString();
    }

    @JsonSetter("id")
    public void setStringId(String id){
        this.setId(new ObjectId(id));
    }

}
