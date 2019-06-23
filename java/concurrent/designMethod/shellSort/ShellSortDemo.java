import java.util.*;
import java.util.concurrent.*;

public class ShellSortDemo {
	static int[] arr = {9,8,7,6,5,4,3,2,1,0};
	static ExecutorService pool = Executors.newFixedThreadPool(9);
	public static class ShellSortTask implements Runnable {
		int i=0;
		int h=0;
		CountDownLatch l;
		int[] arr;

		public ShellSortTask(int i,int h,CountDownLatch latch,int[] arr){
			this.i=i;
			this.h=h;
			this.l=latch;
			this.arr = arr;
		}

		@Override
		public void run(){
			System.out.println("before ShellTask "+Thread.currentThread().getId()+ "\ti:"+i+"\th"+h+"\t"+Arrays.toString(arr));
			if(arr[i] < arr[i-h]){
				int tmp = arr[i];
				int j = i - h;
				while(j>=0 && arr[j]>tmp){
					arr[j+h] = arr[j];
					j-=h;
				}
				arr[j+h] = tmp;
			}
			l.countDown();
			System.out.println("ShellTask "+Thread.currentThread().getId()+ "\ti:"+i+"\th"+h+"\t"+Arrays.toString(arr));
		}
	}

	public static void pShellSort(int[] arr) throws InterruptedException {
		int h=1;
		CountDownLatch latch = null;
		while(h <= arr.length/3){
			h=h*3+1;
		}
		while(h>0){
			System.out.println("h="+h);
			long start = System.currentTimeMillis();
			if(h>=4){
				latch = new CountDownLatch(arr.length-h);
			}
			for (int i=h;i<arr.length;i++){
				if(h>=4){
					pool.execute(new ShellSortTask(i,h,latch,arr));
				}else{
					System.out.println("before normal "+i+" " + Arrays.toString(arr));
					if(arr[i] < arr[i-h]){
						int tmp = arr[i];
						int j = i-h;
						while(j>=0 && arr[j]>tmp){
							arr[j+h] = arr[j];
							j-=h;
						}
						arr[j+h] = tmp;
					}
					System.out.println("normal "+i+" " + Arrays.toString(arr));
				}
			}
			latch.await();
			System.out.println("Used: "+(System.currentTimeMillis()-start));
			h = (h-1)/3;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("begin "+Arrays.toString(arr));
		pShellSort(arr);	
		System.out.println("end " +Arrays.toString(arr));
		pool.shutdown();
	}
}
