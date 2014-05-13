package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.PersistenceException;

import models.Member3;
import models.Member3Todo;

import org.apache.commons.lang3.StringUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Membership3 extends Controller {

    private final static Form<RegisterModel> registerForm = new Form<RegisterModel>(RegisterModel.class);

    public static class RegisterModel {
        public String email;
        public String username;
        public String password;
    }

    public final static Form<LoginModel> loginForm = new Form<LoginModel>(LoginModel.class);

    public static class LoginModel {
        public String email;
        public String password;
        public String redirect;
    }

    public static Result register() {

        return ok(views.html.register3.render(registerForm));
    }

    public static Result create() {
        Form<RegisterModel> form = registerForm.bindFromRequest();

        if (form.data().size() > 0) {
            if (StringUtils.isEmpty(form.get().email)) {
                form.reject("email", "email cannot be null");
            }
            if (StringUtils.isEmpty(form.get().username)) {
                form.reject("username", "username cannot be null");
            }
            if (StringUtils.isEmpty(form.get().password)) {
                form.reject("password", "password cannot be null");
            }
        }

        if (form.hasErrors()) {
            form.reject("This form is invalid");
        } else {
            try {
                Member3.create(form.get().email, form.get().username, form.get().password);
                Application3.onLogin(form.get().email);
                flash("registrationSuccess", "");
                return redirect(routes.Application3.index());
            } catch (PersistenceException e) {
                form.reject("Erreur lors de la sauvegarde DB");
            }
        }

        return ok(views.html.register3.render(form));
    }

    public static Result login() {
        Form<LoginModel> form = loginForm.bindFromRequest();

        if (Member3.authenticate(form.get().email, form.get().password) == null) {
            flash("errorLogin", "");
        } else {
            Application3.onLogin(form.get().email);
            if (form.get().redirect != null) {
                return redirect(form.get().redirect);
            }
        }

        if (form.get().redirect != null) {
            String uri;
            try {
                uri = URLEncoder.encode(form.get().redirect, "UTF-8");
                return redirect(routes.Application.index() + "?redirect=" + uri);
            } catch (UnsupportedEncodingException e) {
                // ignore
            }
        }
        return redirect(routes.Application.index());

    }

    @Security.Authenticated(Secured.class)
    public static Result profile() {
        Member3 member = Membership3.getUser();
        return ok(views.html.profile3.render(member));
    }

    @Security.Authenticated(AjaxSecured.class)
    public static Result addTodo(String todo) {
        Member3 member = Membership3.getUser();

        ObjectNode jsonResponse = Json.newObject();

        if (!StringUtils.isEmpty(todo)) {
            try {
                Member3Todo memberTodo = Member3.addTodo(member, todo);
                jsonResponse.put("id", memberTodo.id);
                return ok(jsonResponse);
            } catch (PersistenceException e) {
                return internalServerError("DB error");
            }
        }

        return badRequest("parameter missing");
    }

    @Security.Authenticated(AjaxSecured.class)
    public static Result removeTodo(Long todoId) {
        Member3 member = Membership3.getUser();

        try {
            Member3.removeTodo(member, todoId);
            return ok("done");
        } catch (PersistenceException e) {
            return internalServerError("DB error");
        }

    }

    public static Result logout() {
        Application3.onLogout();
        return redirect(routes.Application3.index());
    }

    public static Member3 getUser() {
        return (Ebean.find(Member3.class).where().eq("email", session("email")).findUnique());
    }
}
