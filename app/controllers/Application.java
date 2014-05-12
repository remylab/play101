package controllers;

import java.util.LinkedList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;

import com.google.gson.Gson;

public class Application extends Controller {

    public static Result index() {

        List<String> target = new LinkedList<String>();
        target.add("Play101");
        target.add("Part1");

        Gson gson = new Gson();
        String json = gson.toJson(target);

        return ok(views.html.index.render(json));
    }
}
