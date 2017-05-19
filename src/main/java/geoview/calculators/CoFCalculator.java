package geoview.calculators;

import geoview.exceptions.InvalidInputException;


public class CoFCalculator extends RiskModelCalculator { 
    public CoFCalculator(){
        riskTag = "COF";
        criteriaTags = new String[]{"WATER_BODIES", "STREET_TYPE", "CRITICAL_FACILITIES", "PIPE_MATERIAL", "DEPTH", "LENGTH", "RADIUS"};
        weights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    }
    
    @Override   
    protected int specialCalcCases(String tag, Object value) throws InvalidInputException{
        try{
            if(tag.equals("PIPE_MATERIAL")){
                String val = value.toString();
                switch(val){
                    case "CLAY":
                        return 8;
                    case "IRON":
                        return 3;
                    case "PLASTIC":
                        return 5;
                    case "ORANGEBURG":
                        return 7;
                    default:
                        throw new InvalidInputException(tag, val, "Error: Invalid Input");
                }
            }
            else if(tag.equals("DEPTH")){
                return Math.min(Integer.parseInt(value.toString())/3, 10);
            }
            else if(tag.equals("LENGTH")){
                return Math.min(Integer.parseInt(value.toString())/10, 10);
            }
            else if(tag.equals("RADIUS")){
                return Math.min(Integer.parseInt(value.toString())/10, 10);
            }
        }
        catch(NumberFormatException e){
            throw new InvalidInputException(tag, value.toString(), "Error: Invalid Input");
        }
        return -1;
    }
    
}
