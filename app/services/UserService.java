package services;

import helper.ds.MorphiaObject;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by tito on 22/10/15.
 */
public class UserService extends DataAccessServiceImpl<User, String> {


    public User findByUserName(String userName){
        return MorphiaObject.datastore.find(User.class).field("un").equal(userName).get();
    }


    public User findByUserNameAndPassword(String userName, String password){
        Query<User> query = MorphiaObject.datastore.createQuery(User.class);
        query.and(query.criteria("un").equal(userName), query.criteria("pass").equal(password));
        for (User user : query.fetch()){
            return user;
        }
        return null;
    }

    public void deleteAuthToken(String token){
        Query<User> query = MorphiaObject.datastore.createQuery(User.class).field("auth").equal(token);
        UpdateOperations<User> ops = MorphiaObject.datastore.createUpdateOperations(User.class).unset("auth");
        MorphiaObject.datastore.update(query, ops);
    }

    public User authenticate(String userName, String password) {
        return findByUserNameAndPassword(userName, password);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}

