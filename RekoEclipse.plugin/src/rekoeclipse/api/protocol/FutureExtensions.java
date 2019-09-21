package rekoeclipse.api.protocol;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FutureExtensions {

	private static final class FutureRunnable<T> implements Runnable {
		private final CompletableFuture<T> future = new CompletableFuture<>();
		private final Callable<T> func;

		public CompletableFuture<T> getFuture() {
			return future;
		}
		
		public FutureRunnable(Callable<T> func){
			this.func = func;
		}

		@Override
		public void run() {
			try {
				future.complete(func.call());
			} catch (Throwable t) {
				future.completeExceptionally(t);
			}
		}
	}

	public static <T> CompletableFuture<T> supplyAsync(Callable<T> func){
		FutureRunnable<T> futureRunnable = new FutureRunnable<>(func);
		CompletableFuture.runAsync(futureRunnable);
		return futureRunnable.getFuture();
	}
	
	public static <T> CompletableFuture<T> supplyAsync(Callable<T> func, Executor executor) {
		FutureRunnable<T> futureRunnable = new FutureRunnable<>(func);
		CompletableFuture.runAsync(futureRunnable, executor);
		return futureRunnable.getFuture();
	}
}
