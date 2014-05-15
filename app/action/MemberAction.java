package action;

import models.Member3;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;
import controllers.Membership3;

public class MemberAction extends Action.Simple {

    public static String MEMBER = "MemberAction.member";

    @Override
    public F.Promise<SimpleResult> call(Context ctx) throws Throwable {

        Member3 member = Membership3.getUser();
        ctx.args.put(MEMBER, member);
        return delegate.call(ctx);
    }

}
