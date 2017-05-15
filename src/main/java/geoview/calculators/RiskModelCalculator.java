package geoview.calculators;
import java.util.ArrayList;
import java.util.Map;
/*
    This class and it's subclasses will be utilized when the data sheet is uploaded into the program.
    Its purpose is to gather all various criteria for forming LoF and CoF, and using this criteria to calculate the LoF and CoF, for each pipe.
*/

public abstract class RiskModelCalculator {
        
        //private DataSheetManager data;
        protected String riskTag;
        protected String[] criteriaTags;
        protected double[] weights;
    
    
    public int calcWeightedCriteriaValues(Map<String, Object> attributes){
        int usedTags = 0;
        double weightedCriteriaScore =  0.0;
        
        for(int tagIndex = 0; tagIndex < criteriaTags.length; tagIndex++){
                String tag = criteriaTags[tagIndex];
                if(attributes.get(tag) != null){
                    double tagValue = new Double(attributes.get(tag).toString());
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
    
    
}
