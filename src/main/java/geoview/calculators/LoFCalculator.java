package geoview.calculators;


public class LoFCalculator extends RiskModelCalculator {
    public LoFCalculator(){
        riskTag = "LOF";
        criteriaTags = new String[]{"CCTV", "GRIT", "TROUBLE_SPOTS", "FAULT_PROXIMITY", "CORROSIVE_SOIL", "AGE"};
        weights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    }
}
