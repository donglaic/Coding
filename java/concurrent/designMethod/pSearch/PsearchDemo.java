import java.util.concurrent.atomic.*;
import java.util.*;
import java.util.concurrent.*;
public class PsearchDemo {
	static int[] arr = {1,2,3,4,5,6,7,8,9,0};
	static ExecutorService pool = Executors.newCachedThreadPool();
	static final int Thread_Num=2;
	static AtomicInteger result = new AtomicInteger(-1);

	public static void main(String[] args) throws InterruptedException,ExecutionException {
		System.out.println(pSearch(5));
	}

	public static int pSearch(int searchValue) throws InterruptedException,ExecutionException {
		int subArrSize = arr.length/Thread_Num + 1;
		List<Future<Integer>> re = new ArrayList<Future<Integer>>();
		for (int i=0;i<arr.length;i+=subArrSize){
			int end = i+subArrSize;
			if (end>=arr.length) end = arr.length;
			re.add(pool.submit(new SearchTask(searchValue,i,end)));
		}
		for(Future<Integer> fu:re){
			if(fu.get() >= 0) {
				//pool.shutdown();
				return fu.get();
			}
		}
		pool.shutdown();
		return -1;
	}

	public static int search(int searchValue,int beginPos,int endPos){
		int i=0;
		for(i=beginPos;i<endPos;i++){
			if(result.get()>=0){
				return result.get();
			}
			if(arr[i]==searchValue){
				if(!result.compareAndSet(-1,i)){
					return result.get();
				}
				return i;
			}
		}
		return -1;
	}

	public static class SearchTask implements Callable<Integer> {
		int begin,end,searchValue;
		public SearchTask(int searchValue,int begin,int end){
			this.begin = begin;
			this.end = end;
			this.searchValue = searchValue;
		}
		public Integer call(){
			int re=search(searchValue,begin,end);
			return re;
		}
	}
}
