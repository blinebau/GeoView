package geoview.calculators;
import java.util.Map;

import geoview.exceptions.InvalidInputException;
/*
    This class and it's subclasses will be utilized when the map data is uploaded into the program.
    Its purpose is to gather all various criteria for forming LoF and CoF, and using this criteria to calculate the LoF and CoF, for each pipe.
*/

public abstract class RiskModelCalculator {
        
        protected String riskTag;
        protected String[] criteriaTags;
        protected double[] weights;
    
    
    public int calcWeightedCriteriaValues(Map<String, Object> attributes) throws InvalidInputException{
        int usedTags = 0;
        double weightedCriteriaScore =  0.0;
        
        for(int tagIndex = 0; tagIndex < criteriaTags.length; tagIndex++){
                String tag = criteriaTags[tagIndex];
                Object value = attributes.get(tag);
                int specialCasesValue;
                if(value != null){
                    double tagValue;
                    if((specialCasesValue = specialCalcCases(tag, value)) != -1){
                        tagValue =  (double) specialCasesValue;
                    }
                    else{
                        tagValue = new Double(value.toString());
                    }
                    weightedCriteriaScore += tagValue * weights[tagIndex];
                    usedTags++;
                }
        }
        //Check for division by 0.
        if(!(usedTags < 1)){
            return (int) weightedCriteriaScore/usedTags;
        }
        return 0; //Or throw exception... Determine whether completely blank input should just calculate to 0.
    }
    
    public void changeWeight(int index, double value){
        weights[index] = value;
    }
    
    public String[] getCriteriaTags(){
        return criteriaTags;
    }
           
    protected abstract int specialCalcCases(String tag, Object value) throws InvalidInputException;
    
    
}
