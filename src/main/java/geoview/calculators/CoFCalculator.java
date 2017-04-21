/*
    
*/

package geoview.calculators;
import java.util.ArrayList;

public class CoFCalculator extends RiskModelCalculator {
    //private DataSheetManager data;
    private final String riskTag = "COF";
    private String[] criteriaTags = {"WATER", "STREET", "CRITICALFACTILITIES", "PIPETYPE"};
    private double[] weights = {1.0, 1.0, 1.0, 1.0};
    private ArrayList<Double> weightedRisk;
    
    public CoFCalculator(){
        //data = new DataSheetManager();
    }
}
