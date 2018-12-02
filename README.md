# NOW-统计文字数量

## 先使用单线程统计

要实现文字统计，我们需要做以下的事情：

- 读取某个文件夹下所有的文件，保存文件路径
- 遍历每个文件，加载文件内容，记录文本数字

我们需要过滤掉空格，换行，超链接等，这里没有做详细的过滤
![](/img/使用单线程.png)

## 使用多线程统计-线程池

先看一下统计字数线程的代码
```java
public class CountNumTask implements Runnable{

	private String file ;
	private FilterProcessManager processManager;
	
	/**
	 * 统计结果
	 */
	private static long countResult = 0;
	
	
	
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
	
}

```

使用线程池可以带来效率的提高，同时减少了创建线程带来的消耗。不过引入了新的问题，就是线程间的同步，这里的静态变量`countResult`会造成
统计的结果比实际结果小
![](/img/使用线程池.png)

线程间的同步有很多种手段：
- 使用sychronized关键字
- 使用Lock锁
- 使用原子变量 

这里我们简单使用原子变量AtomicLong来实现同步

最后可以看到，单线程统计的结果和使用线程池的结果一样

