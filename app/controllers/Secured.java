package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return checkUsername(ctx);
    }

    public static String checkUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        try {
            String uri = URLEncoder.encode(ctx.request().uri(), "UTF-8");
            return redirect(routes.Application.index() + "?redirect=" + uri);
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return redirect(routes.Application.index());
    }
}