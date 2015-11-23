package controllers;

/**
 * Created by tito on 22/10/15.
 */

import helper.ds.MorphiaObject;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import services.UserService;

import javax.inject.Inject;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        User user = null;

        String[] authTokenHeaderValues = ctx.request().headers().get(Application.AUTH_TOKEN_HEADER);
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            user = MorphiaObject.datastore.find(User.class).field("auth").equal(authTokenHeaderValues[0]).get();
            if (user != null) {
                ctx.args.put("user", user);
                return user.getUsername();
            }
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(routes.Application.index());
    }

    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("X-AUTH-TOKEN");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }
}
