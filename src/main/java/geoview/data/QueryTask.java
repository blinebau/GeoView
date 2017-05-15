package geoview.data;

import java.util.ArrayList;
import java.util.Map;

import javafx.concurrent.Task;

public class QueryTask extends Task<ArrayList<Map<String, Object>>> {
	
	private ArrayList<Map<String, Object>> attrColl;
	
	private int low;
	
	private int high;
	
	private boolean bySCI;
	
	public QueryTask(ArrayList<Map<String, Object>> newAttr, int lowerBound, int higherBound, boolean type) {
		attrColl = newAttr;
		low = lowerBound;
		high = higherBound;
		bySCI = type;
	}
	
	@Override
	protected ArrayList<Map<String, Object>> call() {
		ArrayList<Map<String, Object>> resultAttr = new ArrayList<>();
		if(bySCI) {
			resultAttr = searchBySCI();
		} else {
			resultAttr = searchByPredictiveFailure();
		}
		return resultAttr;
	}
	
	private ArrayList<Map<String, Object>> searchBySCI() {
		ArrayList<Map<String, Object>> resultAttr = new ArrayList<>();
		for(Map<String, Object> attrMap : attrColl) {
			Integer sci = (Integer) attrMap.get("SCI");
			if(sci >= low && sci <= high) {
				resultAttr.add(attrMap);
			}
		}
		return resultAttr;
	}
	
	
	private ArrayList<Map<String, Object>> searchByPredictiveFailure() {
		ArrayList<Map<String, Object>> resultAttr = new ArrayList<>();
		for(Map<String, Object> attrMap : attrColl) {
			//Predictive Failure Value
		}
		return resultAttr;
	}

}
