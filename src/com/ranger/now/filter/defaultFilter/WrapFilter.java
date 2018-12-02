/**
 * 
 */
package com.ranger.now.filter.defaultFilter;

import com.ranger.now.filter.Filter;

/**
 * @author Ranger
 * 去空格和换行
 */
public class WrapFilter implements Filter{

	
	@Override
	public String process(String msg) {
		 msg = msg.replaceAll("\\s*", "");

	     return msg ;
	}
	
}
