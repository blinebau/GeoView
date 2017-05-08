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
        protected double weightedCriteriaScore = 0.0;
    
    
    public int calcWeightedCriteriaValues(Map<String, Object> attributes){
        int usedTags = 0;
        try{
            for(int tagIndex = 0; tagIndex < criteriaTags.length; tagIndex++){
                    String tag = criteriaTags[tagIndex];
                    if(attributes.get(tag) != null){
                        double tagValue = new Double(attributes.get(tag).toString());
                        weightedCriteriaScore += tagValue * weights[tagIndex];
                        usedTags++;
                    }
            }
        }catch(Exception e){
            System.err.println(e);
            System.out.println("Determine whether to catch or throw.../nthis error may possibly be displayed when user attempts to load map that isn't correct format");
        }
        return (int) weightedCriteriaScore/usedTags;
    }
    
    public void changeWeight(int index, double value){
        weights[index] = value;
    }
    
    
}
