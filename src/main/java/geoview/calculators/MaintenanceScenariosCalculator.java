package geoview.calculators;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class MaintenanceScenariosCalculator {
    //Avg Cost Per Foot.
    private static double sameSizeReplacementAvgCost = 220.0;
    private static double upSizeReplacementAvgCost = 250.0;
    private static double fullTrenchlessRehabAvgCost = 150.0;
    private static double partialTrenchlessRehabAvgCost = 100.0;
    
    //Material cost (by weight, with clay being average).
    private static double clayPipeCost = 1.0;
    private static double ironPipeCost = 2.0;
    private static double plasticPipeCost = 0.7;
    private static double orangeburgPipeCost = 0.6;
    
    //Average depth
    private static double averageDepth = 10.0;
    private static double averageRadius = 50.0;
    
    
    //For now this is implemented by calculating cost for the highest SCI pipes.
    //The highest SCI pipes will be returned, until the budget is filled.
    //If there are no high SCI pipes that fit within the budget, lower SCI pipes are considered.
    public static List<Map<String, Object>> calculateMaintenanceScenarios(int year, String maintenanceType, double budget, List<Map<String, Object>> features){
        double calculatedCost = 0.0;
        List<Map<String,Object>> budgetedFeatures = new ArrayList<>();
        
        for(Map<String, Object> feature : features){
            double featureCost = calculateFeatureCost(maintenanceType, feature);
            if((calculatedCost + featureCost) <= budget){
                calculatedCost += featureCost;
                budgetedFeatures.add(feature);
            }
        }
        return budgetedFeatures;
    }
    
    //For now since we are treating all of these values as
    private static double calculateFeatureCost(String maintenanceType, Map<String, Object> feature){
        double length = Double.parseDouble(feature.get("LENGTH").toString());
        double depth = Double.parseDouble(feature.get("DEPTH").toString());
        String pipeMaterial = feature.get("PIPE_MATERIAL").toString();
        double radius = Double.parseDouble(feature.get("RADIUS").toString());
        
        double costPerFoot = length * getPipeTypeValue(pipeMaterial) * getReplacementTypeValue(maintenanceType) * getDepthWeight(depth) * getRadiusWeight(radius);
        
        return costPerFoot;
    }
    
    private static double getPipeTypeValue(String pipeMaterial){
        switch(pipeMaterial){
            case "CLAY":
                return clayPipeCost;
            case "IRON":
                return ironPipeCost;
            case "PLASTIC":
                return plasticPipeCost;
            case "ORANGEBURG":
                return orangeburgPipeCost;
            default:
                return 1.0;
        }
    }
    
    private static double getReplacementTypeValue(String maintenanceType){
        switch(maintenanceType){
            case "Same-Size Replacement":
                return sameSizeReplacementAvgCost;
            case "Up-Size Replacement":
                return upSizeReplacementAvgCost;
            case "Full Trench Rehab":
                return fullTrenchlessRehabAvgCost;
            case "Partial Trench Rehab":
                return partialTrenchlessRehabAvgCost;
            default:
                return 0.0;
                //Throw new exception if user does not enter value? Or disallow empty values in GUI controllers.
        }
    }
    
    private static double getDepthWeight(double depth){
        double returnWeight = 1.0;
        double depthModifier = depth - averageDepth;
        return Math.max(returnWeight + (depthModifier/10.0), 0.8);   
    }
    
    private static double getRadiusWeight(double radius){
        double returnWeight = 1.0;
        double radiusModifier = radius - averageRadius;
        return Math.max(returnWeight + (radiusModifier/30.0), 0.3);   
    }
    
}
