package geoview.calculators;

import java.util.ArrayList;
import java.util.Map;


public class MaintenanceScenariosCalculator {
    //Make Pipe material a dropdown with options. //Include these options in schema check //Could make this into a map.
    //clay, iron, plastic, orangeburg. (12 inch length, 12 inch radius)
    private static double[] pipeMaterialCosts = {10.0, 10.0, 10.0, 10.0};
    
    //per foot?
    private static double pipeReplacementAvgCost = 15.0;
    private static double trenchlessRehabAvgCost = 30.0;
    
    public static ArrayList<Map<String, Object>> calculateMaintenanceScenarios(int year, String maintenanceType, int budget, ArrayList<Map<String, Object>> features){
        //Calculate here.
        return features;
    }
    
}
