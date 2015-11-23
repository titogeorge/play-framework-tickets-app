package controllers;

import model.Comment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.CommentsService;

import javax.inject.Inject;

/**
 * Created by tito on 21/10/15.
 */

@Security.Authenticated(Secured.class)
public class CommentsController extends Controller {

    @Inject
    CommentsService commentsService;

    public Result addComment(){
        Comment comment = Json.fromJson(request().body().asJson(), Comment.class);
        comment = commentsService.create(comment);
        return ok(Json.toJson(comment));
    }

    public Result getComments(String ticketId){
        return ok(Json.toJson(commentsService.findAllByTicketId(ticketId)));
    }
}
