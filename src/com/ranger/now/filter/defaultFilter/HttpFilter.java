/**
 * 
 */
package com.ranger.now.filter.defaultFilter;

import com.ranger.now.filter.Filter;

/**
 * @author Ranger
 * 去除超链接
 */
public class HttpFilter implements Filter{

	
	@Override
	public String process(String msg) {
		 msg = msg.replaceAll("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+","");

	     return msg ;
	}
	
}
