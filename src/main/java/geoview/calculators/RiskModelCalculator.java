package geoview.calculators;
import java.util.ArrayList;
/*
    This class and it's subclasses will be utilized when the data sheet is uploaded into the program.
    Its purpose is to gather all various criteria for forming LoF and CoF, and using this criteria to calculate the LoF and CoF, for each pipe.
*/

public abstract class RiskModelCalculator {
        
        //private DataSheetManager data;
        private String riskTag;
        private String[] criteriaTags;
        private double[] weights;
        private ArrayList<Double> weightedRisk;
    
    
    public void calcWeightedCriteriaValues(){
        //data.openFileForRead();
        //for(int pipeIndex = 0; pipeIndex = data.fileLength; pipeIndex++){
            double weightedCriteriaScore = 0.0;
            for(int tagIndex = 0; tagIndex < criteriaTags.length; tagIndex++){
                String tag = criteriaTags[tagIndex];
                //weightedCriteriaScore += data.getCriteria(tag) * weights[tagIndex];
            }
            //weightedRisk.add(weightedCriteriaScore);
        //}
    }
    
    public void setRiskDataValues(){
        //data.openFileForWrite();
        //for(int pipeIndex = 0; pipeIndex = data.fileLength; pipeIndex++){
            //data.setCriteria(riskTag, weightedRisk.get(pipeIndex);
        //}
    }
    
    public void changeWeight(int index, double value){
        weights[index] = value;
    }
    
    public ArrayList<Double> getWeightedRisk(){
        return weightedRisk;
    }
    
}
