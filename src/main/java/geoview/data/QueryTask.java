package geoview.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.concurrent.Task;

public class QueryTask extends Task<List<Map<String, Object>>> {
	
	private List<Map<String, Object>> attrColl;
	
	private int low;
	
	private int high;
	
	private String searchField;
	
	public QueryTask(List<Map<String, Object>> newAttr, int lowerBound, int higherBound, String fieldName) {
		attrColl = newAttr;
		low = lowerBound;
		high = higherBound;
		searchField = fieldName;
	}
	
	@Override
	protected List<Map<String, Object>> call() {
		List<Map<String, Object>> resultAttr = new ArrayList<>();
		resultAttr = searchInRange();
		return resultAttr;
	}
	
	private List<Map<String, Object>> searchInRange() {
		List<Map<String, Object>> resultAttr = new ArrayList<>();
		for(Map<String, Object> attrMap : attrColl) {
			Integer sci = (Integer) attrMap.get(searchField);
			if(sci >= low && sci <= high) {
				resultAttr.add(attrMap);
			}
		}
		return resultAttr;
	}
}
