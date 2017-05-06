package geoview.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;

import javafx.concurrent.Task;

public class DataTask extends Task<ArrayList<Map<String, Object>>> {
	
	private FeatureQueryResult result;
	
	public DataTask(FeatureQueryResult newResult) {
		result = newResult;
	}

	@Override
	protected ArrayList<Map<String, Object>> call() throws Exception {
		ArrayList<Map<String, Object>> featureAttr = new ArrayList<>();
		Iterator<Feature> feature_it = result.iterator();
		while(feature_it.hasNext()) {
			Feature feature = feature_it.next();
			featureAttr.add(feature.getAttributes());
		}
		System.out.println(featureAttr.size());
		return featureAttr;
	}

}
