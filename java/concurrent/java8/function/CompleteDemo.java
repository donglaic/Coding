import java.util.concurrent.*;
public class CompleteDemo{
	public static Integer calc(Integer para){
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
		}
		return para/2;
	}
	public static void main(String[] args) throws InterruptedException,ExecutionException{
		CompletableFuture<Integer> intFuture = CompletableFuture.supplyAsync(()->calc(50));
		CompletableFuture<Integer> intFuture2 = CompletableFuture.supplyAsync(()->calc(25));

		CompletableFuture<Void> future = intFuture.thenCombine(intFuture2,(i,j)->(i+j))
			.thenApply((str)->"\""+str+"\"")
			.thenAccept(System.out::println);
		future.get();
	}
}
