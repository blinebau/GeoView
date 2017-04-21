/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geoview.calculators;
import java.util.ArrayList;

public class LoFCalculator extends RiskModelCalculator {
       
    //private DataSheetManager data;
    private final String riskTag = "LOF";
    private String[] criteriaTags = {"CCTV", "GRIT", "TROUBLE", "FAULT", "SOIL", "AGE"};
    private double[] weights = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    private ArrayList<Double> weightedRisk;
    
    
    public LoFCalculator(){
        //data = new DataSheetManager();
    }   
}
