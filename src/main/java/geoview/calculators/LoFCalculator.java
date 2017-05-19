package geoview.calculators;

import geoview.exceptions.InvalidInputException;


public class LoFCalculator extends RiskModelCalculator {
    public LoFCalculator(){
        riskTag = "LOF";
        criteriaTags = new String[]{"CCTV", "GRIT", "TROUBLE_SPOTS", "FAULT_PROXIMITY", "CORROSIVE_SOIL", "AGE"};
        weights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    }
    
    @Override
    protected int specialCalcCases(String tag, Object value) throws InvalidInputException{
        if(tag.equals("AGE")){
            try{
                return Math.min((int)Double.parseDouble(value.toString())/20, 10); 
            } 
            catch(NumberFormatException e){    
                throw new InvalidInputException(tag, value.toString(), "Error: Invalid Input");
            }
        }
        return -1;
    }
}
