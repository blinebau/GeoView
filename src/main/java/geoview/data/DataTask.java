package geoview.data;

import geoview.calculators.CoFCalculator;
import geoview.calculators.LoFCalculator;
import geoview.calculators.SCICalculator;
import geoview.exceptions.SchemaException;
import geoview.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;

import javafx.concurrent.Task;

public class DataTask extends Task<ArrayList<Map<String, Object>>> {
	
    private FeatureQueryResult result;
    private CoFCalculator cofCalculator;
    private LoFCalculator lofCalculator;
    
    public DataTask() {
    	cofCalculator = new CoFCalculator();
    	lofCalculator = new LoFCalculator();
    }

    public DataTask(FeatureQueryResult newResult) {
        result = newResult;
        cofCalculator = new CoFCalculator();
        lofCalculator = new LoFCalculator();
    }

    @Override
    protected ArrayList<Map<String, Object>> call() throws SchemaException, InvalidInputException {
        boolean firstFeature = true;
        ArrayList<Map<String, Object>> featureAttr = new ArrayList<>();
        Iterator<Feature> featureIt = result.iterator();
        while(featureIt.hasNext()) {
            Feature feature = featureIt.next();
            if (firstFeature){
                checkSchema(feature);
                firstFeature = false;
            }
            featureAttr.add(setRiskModelValues(feature));
        }
        sortBySCI(featureAttr);
        return featureAttr;
    }
    
    public void setQueryResult(FeatureQueryResult newResult) {
    	result = newResult;
    }
    
    //Check to see if each criteria tag (CCTV, GRIT, etc.) is included in the uploaded map data.
    private boolean checkSchema(Feature feature) throws SchemaException{
        ArrayList<String> schemaTags = new ArrayList<>();
        Map<String, Object> featureAttr = feature.getAttributes();
        boolean schemaCorrect = true;
        
        for(String tag : cofCalculator.getCriteriaTags()){
            if(!featureAttr.containsKey(tag)){
                schemaTags.add(tag);
                schemaCorrect = false;
            }
        }
        for(String tag : lofCalculator.getCriteriaTags()){
            if(!featureAttr.containsKey(tag)){
                schemaTags.add(tag);
                schemaCorrect = false;
            }
        }
        if(!schemaCorrect){
            throw new SchemaException(schemaTags, "Error: Wrong Schema"); //FIGURE OUT WHERE/HOW TO HANDLE
        }
        return schemaCorrect;
    }
    
    private void sortBySCI(ArrayList<Map<String, Object>> features){
        Collections.sort(features, new Comparator<Map<String, Object>>(){
            @Override
            public int compare(Map<String, Object> s1, Map<String, Object> s2){
                return Integer.valueOf(s1.get("SCI").toString()).compareTo(Integer.valueOf(s2.get("SCI").toString()));
            }
        });
    }
        
    //calculates and sets SCI/COF/LOF values.
    private Map<String, Object> setRiskModelValues(Feature feature) throws InvalidInputException{

        Map<String, Object> attributes = feature.getAttributes();

        int cofValue = cofCalculator.calcWeightedCriteriaValues(attributes);
        int lofValue = lofCalculator.calcWeightedCriteriaValues(attributes);
        int sciValue = SCICalculator.calculateSCI(cofValue, lofValue);

        attributes.replace("COF", cofValue);
        attributes.replace("LOF", lofValue);
        attributes.replace("SCI", sciValue);

        return attributes;
    }
    
    

}
