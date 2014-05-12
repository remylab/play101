package controllers;

import org.apache.commons.lang3.StringUtils;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Membership extends Controller {
    private final static Form<RegisterModel> registerForm = new Form<RegisterModel>(RegisterModel.class);

    public static class RegisterModel {

        public String email;
        public String userName;
        public String password;
    }

    public static Result register() {
        Form<RegisterModel> form = registerForm.bindFromRequest();

        if (form.data().size() > 0) {
            if (StringUtils.isEmpty(form.get().email)) {
                form.reject("email", "email cannot be null");
            }
        }

        if (form.hasErrors()) {
            form.reject("This form is invalid");
        }

        return ok(views.html.register.render(form));
    }
}
