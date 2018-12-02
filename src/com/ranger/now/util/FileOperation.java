/**
 * 
 */
package com.ranger.now.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ranger
 *
 */
public class FileOperation {
	/**
	  *  获取某个文件夹下所有的文件路径
	 * @param dirPath 文件夹路径
	 * @return
	 */
	public static List<String> getAllFiles(String dirPath)throws Exception{
		List<String> allFile = new ArrayList<>();
		File f = new File(dirPath);
		if(!f.exists()) {
			throw new FileNotFoundException("dir not exist");
		}
		
		File[] files = f.listFiles();
		for(File file : files) {
			if(file.isDirectory()) {
				// 如果是文件夹
				String dir = file.getPath();
				allFile.addAll(getAllFiles(dir));
			}else {
				String path = file.getPath();
				if(!path.endsWith(".md")) {
					continue;
				}
				allFile.add(path);
			}
		}
		
		return allFile;
	}
}
