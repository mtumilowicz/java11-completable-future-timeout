# java11-completable-future-timeout
_Reference_: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/CompletableFuture.html

# project description
We have three approaches to timeouts when using 
`CompletableFuture`:
* `public T get(long timeout, TimeUnit unit)` - 
Waits if necessary for at most the given time for this 
future to complete, and then returns its result, if available.
    * with timeout
        ```
        @Test(expected = TimeoutException.class)
        public void get_withTimeoutException() throws InterruptedException, ExecutionException, TimeoutException {
            var future = CompletableFuture.supplyAsync(() -> {
                Delay.delay();
                return "future";
            });
    
            future.get(100, TimeUnit.MILLISECONDS);
        }
        ```
    * without timeout
        ```
        @Test
        public void get_withoutTimeoutException() throws InterruptedException, ExecutionException, TimeoutException {
            var future = CompletableFuture.supplyAsync(() -> "future");
        
            assertThat(future.get(500, TimeUnit.MILLISECONDS), is("future"));
        }
        ```
* `CompletableFuture<T>	orTimeout​(long timeout, TimeUnit unit)` -
Exceptionally completes this `CompletableFuture` with a `TimeoutException` 
if not otherwise completed before the given timeout.
    * with timeout
        ```
        @Test
        public void orTimeout_withTimeoutException() {
            var future = CompletableFuture.supplyAsync(() -> {
                Delay.delay();
                return "future";
            }).orTimeout(300, TimeUnit.MILLISECONDS);
            
            Delay.delay();
            
            assertTrue(future.isCompletedExceptionally());
        }
        ```
    * without timeout
        ```
        @Test
        public void orTimeout_withoutTimeoutException() {
            var future = CompletableFuture.supplyAsync(() -> "future")
                    .orTimeout(300, TimeUnit.MILLISECONDS);
        
            assertThat(future.join(), is("future"));
        }
        ```
* `CompletableFuture<T> completeOnTimeout​(T value, long timeout, TimeUnit unit)` - 
Completes this `CompletableFuture` with the given value if not otherwise 
completed before the given timeout.
    * with timeout
        ```
        @Test
        public void completeOnTimeout_withTimeoutException() {
            var future = CompletableFuture.supplyAsync(() -> {
                Delay.delay();
                return "future";
            }).completeOnTimeout("timeout", 300, TimeUnit.MILLISECONDS);
        
            assertThat(future.join(), is("timeout"));
        }
        ```
    * without timeout
        ```
        @Test
        public void completeOnTimeout_withoutTimeoutException() {
            var future = CompletableFuture.supplyAsync(() -> "future")
                    .completeOnTimeout("timeout", 300, TimeUnit.MILLISECONDS);
        
            assertThat(future.join(), is("future"));
        }
        ```