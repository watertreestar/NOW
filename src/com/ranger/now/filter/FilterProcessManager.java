/**
 * 
 */
package com.ranger.now.filter;

import java.util.ArrayList;
import java.util.List;

import com.ranger.now.filter.defaultFilter.HttpFilter;
import com.ranger.now.filter.defaultFilter.WrapFilter;

/**
 * @author Ranger
 *
 */
public class FilterProcessManager {
	
	private List<Filter> filterChain = new ArrayList<>();
	
	public FilterProcessManager() {
		// 默认添加的filter
		filterChain.add(new HttpFilter());
		filterChain.add(new WrapFilter());
	}
	
	public FilterProcessManager add(Filter filter) {
		filterChain.add(filter);
		return this;
	}
	
	public String doProcess(String msg) {
		for(Filter f : filterChain) {
			msg = f.process(msg);
		}
		return msg;
	}
	
}
