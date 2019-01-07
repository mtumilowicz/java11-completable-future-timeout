import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2019-01-07.
 */
public class CompletableFutureTests {
    
    @Test(expected = TimeoutException.class)
    public void get_withTimeoutException() throws InterruptedException, ExecutionException, TimeoutException {
        var future = CompletableFuture.supplyAsync(() -> {
            Delay.delay();
            return "future";
        });

        future.get(100, TimeUnit.MILLISECONDS);
    }

    @Test
    public void get_withoutTimeoutException() throws InterruptedException, ExecutionException, TimeoutException {
        var future = CompletableFuture.supplyAsync(() -> "future");

        assertThat(future.get(500, TimeUnit.MILLISECONDS), is("future"));
    }

    @Test
    public void orTimeout_withTimeoutException() {
        var future = CompletableFuture.supplyAsync(() -> {
            Delay.delay();
            return "future";
        }).orTimeout(300, TimeUnit.MILLISECONDS);
        
        Delay.delay();
        
        assertTrue(future.isCompletedExceptionally());
    }

    @Test
    public void orTimeout_withoutTimeoutException() {
        var future = CompletableFuture.supplyAsync(() -> "future")
                .orTimeout(300, TimeUnit.MILLISECONDS);

        assertThat(future.join(), is("future"));
    }

    @Test
    public void completeOnTimeout_withTimeoutException() {
        var future = CompletableFuture.supplyAsync(() -> {
            Delay.delay();
            return "future";
        }).completeOnTimeout("timeout", 300, TimeUnit.MILLISECONDS);

        assertThat(future.join(), is("timeout"));
    }

    @Test
    public void completeOnTimeout_withoutTimeoutException() {
        var future = CompletableFuture.supplyAsync(() -> "future")
                .completeOnTimeout("timeout", 300, TimeUnit.MILLISECONDS);

        assertThat(future.join(), is("future"));
    }
}
