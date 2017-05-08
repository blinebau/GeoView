package geoview.data;

import geoview.calculators.CoFCalculator;
import geoview.calculators.LoFCalculator;
import geoview.calculators.SCICalculator;

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
			featureAttr.add(setRiskModelValues(feature));
		}
		return featureAttr;
	}
        
    private Map<String, Object> setRiskModelValues(Feature feature){

        Map<String, Object> attributes = feature.getAttributes();

        CoFCalculator cofCalculator = new CoFCalculator();
        LoFCalculator lofCalculator = new LoFCalculator();

        int cofValue = cofCalculator.calcWeightedCriteriaValues(attributes);
        int lofValue = lofCalculator.calcWeightedCriteriaValues(attributes);
        int sciValue = SCICalculator.calculateSCI(cofValue, lofValue);

        attributes.replace("COF", cofValue);
        attributes.replace("LOF", lofValue);
        attributes.replace("SCI", sciValue);

        return attributes;
    }

}
