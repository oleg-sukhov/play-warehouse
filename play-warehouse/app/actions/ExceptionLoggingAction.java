package actions;

import exceptions.ProductNotFoundException;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ExceptionLoggingAction extends Action.Simple {

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        try {
            return delegate.call(ctx);
        } catch (ProductNotFoundException e) {
            System.out.println("Alarm! Exception has occurred " + e.getClass());
            return CompletableFuture.completedFuture(notFound(e.getMessage()));
        }
    }
}
