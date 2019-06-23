package com.matrix.MatrixDemo;

import java.util.concurrent.*;
import org.jmatrices.dbl.*;

/**
 * Hello world!
 *
 */
public class App 
{
	public static final int granularity=3;
    public static void main( String[] args ) throws InterruptedException,ExecutionException
    {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Matrix m1= MatrixFactory.getRandomMatrix(10,10,null);
		Matrix m2= MatrixFactory.getRandomMatrix(10,10,null);
		System.out.println("m1\n"+m1);
		System.out.println("m2\n"+m2);
		MatrixMulTask task = new MatrixMulTask(m1,m2,null);
		ForkJoinTask<Matrix> result= forkJoinPool.submit(task);
		Matrix pr= result.get();
		System.out.println("pr\n"+pr);
    }
}
