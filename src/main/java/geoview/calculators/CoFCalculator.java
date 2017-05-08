package geoview.calculators;


public class CoFCalculator extends RiskModelCalculator { 
    public CoFCalculator(){
        riskTag = "COF";
        criteriaTags = new String[]{"WATER_BODIES", "STREET_TYPE", "CRITICAL_FACILITIES", "PIPE_MATERIAL", "DEPTH", "LENGTH", "RADIUS"};
        weights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    }
    
}
