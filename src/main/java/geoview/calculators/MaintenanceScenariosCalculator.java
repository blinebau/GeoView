package geoview.calculators;

import java.util.List;
import java.util.Map;


public class MaintenanceScenariosCalculator {
    //Make Pipe material a dropdown with options. //Include these options in schema check //Could make this into a map.
    //clay, iron, plastic, orangeburg. (12 inch length, 12 inch radius)
    private static double[] pipeMaterialCosts = {10.0, 10.0, 10.0, 10.0};
    
    //per foot?
    private static double pipeReplacementAvgCost = 15.0;
    private static double trenchlessRehabAvgCost = 30.0;
    
    //For now this is implemented by calculating cost for the highest SCI pipes.
    //The highest SCI pipes will be returned, until the budget is filled.
    //If there are no high SCI pipes that fit within the budget, lower SCI pipes are considered.
    public static List<Map<String, Object>> calculateMaintenanceScenarios(int year, String maintenanceType, int budget, List<Map<String, Object>> features){
        int calculatedCost;
        for(Map<String, Object> feature : features){
            calculatedCost = calculateFeatureCost(maintenanceType, feature);
        }
        return features;
    }
    
    //For now since we are treating all of these values as
    private static int calculateFeatureCost(String maintenanceType, Map<String, Object> feature){
        int length = Integer.parseInt(feature.get("LENGTH").toString());
        int depth = Integer.parseInt(feature.get("DEPTH").toString());
        String pipeMaterial = feature.get("PIPE_MATERIAL").toString();
        int radius = Integer.parseInt(feature.get("RADIUS").toString());
        
        return 0;
    }
    
}
