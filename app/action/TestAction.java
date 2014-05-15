package action;

import play.libs.F;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;

public class TestAction extends MemberAction {

    public static String TEST = "TestAction.test";

    @Override
    public F.Promise<SimpleResult> call(Context ctx) throws Throwable {

        super.call(ctx);

        ctx.args.put(TEST, "test");
        return delegate.call(ctx);
    }

}
