package controllers;

import models.Member3;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class Application3 extends Controller {

    public static Result index() {
        Member3 member = Membership3.getUser();

        return ok(views.html.index3.render(member));
    }

    public static void onLogin(String email) {
        session("email", email);
    }

    public static void onLogout() {
        session().remove("email");
    }

    @Security.Authenticated(Secured.class)
    public static Result testSecure() {
        Member3 member = Membership3.getUser();
        return ok(views.html.testsecure.render(member));
    }

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", 
                                          controllers.routes.javascript.Membership3.addTodo(),
                                          controllers.routes.javascript.Membership3.removeTodo()
                                         ));
    }
}
