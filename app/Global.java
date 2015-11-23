import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import helper.ds.MorphiaObject;
import org.mongodb.morphia.Morphia;
import play.GlobalSettings;

/**
 * Created by tito on 20/10/15.
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(play.Application app) {
        super.beforeStart(app);

        MongoClientURI uri = new MongoClientURI("mongodb://dbuser:dbpassword@localhost:27017/dbname");
        MorphiaObject.mongo = new MongoClient(uri);
        MorphiaObject.morphia = new Morphia();
        MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(MorphiaObject.mongo, "tito");

    }

}
