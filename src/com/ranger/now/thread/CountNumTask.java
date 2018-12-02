/**
 * 
 */
package com.ranger.now.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

import com.ranger.now.filter.FilterProcessManager;

/**
 * @author Ranger
 *
 */
public class CountNumTask implements Runnable{

	private String file ;
	private FilterProcessManager processManager;
	
	/**
	 * 统计结果
	 */
	private static long countResult = 0;
	
	private static AtomicLong result = new AtomicLong();
	
	
	
	List<String> files;
	
	/**
	 * 多线程统计模式
	 * @param file
	 * @param processManager
	 */
	public CountNumTask(String file,FilterProcessManager processManager) {
		this.file = file;
		this.processManager = processManager;
	}
	
	/**
	 * 如果使用单线程统计模式
	 * @param files
	 * @param processManager
	 */
	public CountNumTask(List<String> files,FilterProcessManager processManager) {
		this.files = files;
		this.processManager = processManager;
	}
	
	@Override
	public void run() {
		if(files == null && file != null) {
			// file!= null 如果是多个线程计算结果,,当前线程只计算file
			File f = new File(file);
			InputStream is = null;
			try {
				 is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			Scanner scanner = new Scanner(is);
			String msg = null;
			
				
			while(scanner.hasNext()) {
				 msg = scanner.nextLine();
				 msg = processManager.doProcess(msg);
				 countResult += msg.length();
				 // 使用原子变量
				 result.addAndGet(msg.length());
			}
			
		}else if(files != null && file == null) {
			// 单线程计算结果
			for(String s : files) {
				File f = new File(s);
				InputStream is = null;
				try {
					 is = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				Scanner scanner = new Scanner(is);
				String msg = null;
				
					
				while(scanner.hasNext()) {
					 msg = scanner.nextLine();
					 msg = processManager.doProcess(msg);
					 countResult += msg.length();
					// 使用原子变量
					 result.addAndGet(msg.length());
				}
				try {
					Thread.sleep(300);
				}catch(Exception e) {
					System.out.println("thread terminate");
				}
				
				
			}
			
			
		}
		
	}
	
	/**
	 * 获取统计结果
	 * @return
	 */
	public static long getResult() {
		return countResult;
	}
	
	public static long getAtomicResult() {
		return result.get();
	}
	
}
