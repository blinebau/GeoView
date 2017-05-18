package geoview.data;

import java.util.ArrayList;
import java.util.Map;

import javafx.concurrent.Task;

public class QueryTask extends Task<ArrayList<Map<String, Object>>> {
	
	private ArrayList<Map<String, Object>> attrColl;
	
	private int low;
	
	private int high;
	
	private String searchField;
	
	public QueryTask(ArrayList<Map<String, Object>> newAttr, int lowerBound, int higherBound, String fieldName) {
		attrColl = newAttr;
		low = lowerBound;
		high = higherBound;
		searchField = fieldName;
	}
	
	@Override
	protected ArrayList<Map<String, Object>> call() {
		ArrayList<Map<String, Object>> resultAttr = new ArrayList<>();
		resultAttr = searchInRange();
		return resultAttr;
	}
	
	private ArrayList<Map<String, Object>> searchInRange() {
		ArrayList<Map<String, Object>> resultAttr = new ArrayList<>();
		for(Map<String, Object> attrMap : attrColl) {
			Integer sci = (Integer) attrMap.get(searchField);
			if(sci >= low && sci <= high) {
				resultAttr.add(attrMap);
			}
		}
		return resultAttr;
	}
}
