package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Login;
import model.Ticket;
import model.User;
import play.libs.Json;
import play.mvc.*;
import services.TicketService;
import services.UserService;
import views.html.index;

import javax.inject.Inject;
import java.util.UUID;

public class Application extends Controller {

    @Inject
    TicketService ticketService;

    @Inject
    UserService userService;

    public Result index() {
        return ok(index.render());
    }

    public Result hasLoggedIn(){
        String userId = session().get("userId");
        if(userId != null) {
            return ok(Json.toJson(userService.findById(userId)));
        }
        return unauthorized();
    }

    @Security.Authenticated(Secured.class)
    public Result addTicket() {
        Ticket ticket = Json.fromJson(request().body().asJson(), Ticket.class);
        ticket = ticketService.create(ticket);
        return ok(Json.toJson(ticket));
    }

    @Security.Authenticated(Secured.class)
    public Result updateTicket() {
        Ticket ticket = Json.fromJson(request().body().asJson(), Ticket.class);
        ticketService.update(ticket);
        return ok(Json.toJson(ticket));
    }

    @Security.Authenticated(Secured.class)
    public Result getTickets(Integer page, Integer size){
        return ok(Json.toJson(ticketService.findByBatch(page,size)));
    }

    @Security.Authenticated(Secured.class)
    public Result getTicket(String id){
        Ticket ticket = ticketService.findById(id);
        if(ticket == null){
        }
        return ok(Json.toJson(ticket));
    }

    public Result login() {
        return ok(index.render());
    }

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    public Result authenticate(){
        Login login = Json.fromJson(request().body().asJson(), Login.class);
        User user = userService.findByUserNameAndPassword(login.getUsername(), login.getPassword());
        if (user != null){
            //session("userId", user.getStringId() );
            String authToken = UUID.randomUUID().toString();
            user.setAuthToken(authToken);
            userService.update(user);
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(AUTH_TOKEN, authToken);
            response().setCookie(AUTH_TOKEN, authToken);
            return ok(authTokenJson);
        } else {
            return unauthorized();
        }
    }

    public Result register(){
        User user = Json.fromJson(request().body().asJson(), User.class);
        User dbuser = userService.findByUserName(user.getUsername());
        if(dbuser == null){
            userService.create(user);
            session("userId", user.getStringId());
            return ok();
        } else {
            session().clear();
            return badRequest();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result logout() {
        response().discardCookie(AUTH_TOKEN);
        User user = (User) Http.Context.current().args.get("user");
        userService.deleteAuthToken(user.getAuthToken());
        return ok();
    }

}
