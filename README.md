# java11-completable-future-timeout
_Reference_: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/CompletableFuture.html

# project description
We have three approaches to timeouts when using 
`CompletableFuture`:
* `public T get(long timeout, TimeUnit unit)` - 
Waits if necessary for at most the given time for this 
future to complete, and then returns its result, if available.
* `CompletableFuture<T>	orTimeout​(long timeout, TimeUnit unit)` -
Exceptionally completes this `CompletableFuture` with a `TimeoutException` 
if not otherwise completed before the given timeout.
* `CompletableFuture<T> completeOnTimeout​(T value, long timeout, TimeUnit unit)` - 
Completes this `CompletableFuture` with the given value if not otherwise 
completed before the given timeout.