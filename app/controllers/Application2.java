package controllers;

import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application2 extends Controller {

    public static Result index() {

        return ok(views.html.index2.render("Play 101 - Part2"));
    }

    public static Result ajaxTest() {
        ObjectNode jsonResponse = Json.newObject();

        DynamicForm data = Form.form().bindFromRequest();

        jsonResponse.put("status", "OK");
        jsonResponse.put("message", data.get("name"));

        return ok(jsonResponse);
    }

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", controllers.routes.javascript.Application2.ajaxTest()));
    }
}
