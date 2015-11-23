package services;

import helper.ds.MorphiaObject;
import model.Comment;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by tito on 21/10/15.
 */
public class CommentsService extends DataAccessServiceImpl<Comment, String> {

    public List<Comment> findAllByTicketId(String ticketId) {
        List<Comment> comments = MorphiaObject.datastore.find(Comment.class).field("tId").equal(new ObjectId(ticketId)).order("-cd").asList();
        return comments;
    }


    @Override
    public List<Comment> findAll() {
        throw new UnsupportedOperationException("Use findAllByTicketId instead");
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
