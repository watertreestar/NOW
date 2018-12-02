/**
 * 
 */
package com.ranger.now.bootstrap;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ranger.now.filter.FilterProcessManager;
import com.ranger.now.thread.CountNumTask;
import com.ranger.now.util.FileOperation;

/**
 * @author Ranger
 *
 */
public class BootStrap {
	public static void main(String[] args) throws Exception {
		String directoryPath = "D:\\blogroom\\blog\\source\\_posts";
		
		if(args.length >0 && args[0] != null) {
			directoryPath = args[0];
		}
		List<String> files = FileOperation.getAllFiles(directoryPath);
		try {
			
			// 使用默认的filter,如果有其它需求可以扩展Filter并添加到FilterProcessManger中
			FilterProcessManager manager = new FilterProcessManager();
			
			long start = System.currentTimeMillis();
			CountNumTask task = new CountNumTask(files,manager);
			Thread t = new Thread(task);
			t.start();
			
			t.join();
			
			
			long total = task.getResult();
			long end = System.currentTimeMillis();
			long time = end-start;
			System.out.println("total sum=[{"+total+"}],[{"+time+"}] ms");
		}catch(Exception e) {
			System.out.println("Error");
		}
		
//		long start = System.currentTimeMillis();
//		ExecutorService pool = Executors.newFixedThreadPool(10);
//		FilterProcessManager manager = new FilterProcessManager();
//		for(String s : files) {
//			pool.execute(new CountNumTask(s,manager));
//		}
//		
//		pool.shutdown();
//		try {
//		  pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		long end = System.currentTimeMillis();
//		long time = end-start;
//		System.out.println("total sum=[{"+CountNumTask.getAtomicResult()+"}],[{"+time+"}] ms");
//		
		
		
		
		
		
		
	}
}
