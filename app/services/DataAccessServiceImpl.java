package services;

import com.google.inject.Inject;
import helper.ds.MorphiaObject;
import model.BaseModel;
import model.User;
import model.UserVO;
import org.bson.types.ObjectId;
import play.mvc.Controller;
import play.mvc.Http;

import javax.jws.soap.SOAPBinding;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tito on 22/10/15.
 */
public abstract class DataAccessServiceImpl<T extends BaseModel, ID extends Serializable> implements DataAccessService<T, ID> {

    @Inject
    UserService userService;


    @Override
    public T create(T t) {
        UserVO userVO = getCurrentUser();
        if(userVO != null){
            t.setCreatedBy(getCurrentUser());
        }
        t.setCreatedDate(System.currentTimeMillis());
        MorphiaObject.datastore.save(t);
        return t;
    }


    @Override
    public List<T> findAll() {
        List<T> ts = MorphiaObject.datastore.find(getEntityClass()).asList();
        return ts;
    }

    @Override
    public T findById(ID id) {
        T t= MorphiaObject.datastore.find(getEntityClass()).field("_id").equal(new ObjectId((String) id)).get();
        return t;
    }

    @Override
    public void update(T t) {
        MorphiaObject.datastore.merge(t);
    }

    public UserVO getCurrentUser(){
        User user = (User) Http.Context.current().args.get("user");
        System.out.println(user);
        if(user != null) {
            return new UserVO(user.getFirstName() + " " + user.getLastName(), user.getStringId());
        }
        return null;
    }

    @Override
    public List<T> findByBatch(Integer page, Integer size) {
        List<T> ts = MorphiaObject.datastore.find(getEntityClass()).order("-cd").limit(size).offset(page).asList();
        return ts;
    }


}
