package geoview.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.concurrent.Task;

public class QueryTask extends Task<List<Map<String, Object>>> {
	
	private List<Map<String, Object>> featureAttr;
	
	private int low;
	
	private int high;
	
	private String searchField;
	
	public QueryTask(int lowerBound, int higherBound, String fieldName, List<Map<String, Object>> newColl) {
		low = lowerBound;
		high = higherBound;
		searchField = fieldName;
		featureAttr = newColl;
	}
	
	@Override
	protected List<Map<String, Object>> call() {
		List<Map<String, Object>> resultAttr = new ArrayList<>();
		resultAttr = searchInRange();
		return resultAttr;
	}
	
	private List<Map<String, Object>> searchInRange() {
		List<Map<String, Object>> resultAttr = new ArrayList<>();
		for(Map<String, Object> attrMap : featureAttr) {
			Integer val = (Integer) attrMap.get(searchField);
			if(val >= low && val <= high) {
				resultAttr.add(attrMap);
			}
		}
		return resultAttr;
	}
}
