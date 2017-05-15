package geoview.calculators;


public class SCICalculator {
    private static double cofWeight = 1.0;
    private static double lofWeight = 1.0;

    public static int calculateSCI(int cofValue, int lofValue){
        double cofWeightedValue = (double) cofValue * cofWeight;
        double lofWeightedValue = (double) lofValue * lofWeight;
        return (int)(cofWeightedValue + lofWeightedValue)/2;
    }
    
    public static void setCOFWeight(double newWeight){
        cofWeight = newWeight;
    }
    
    public static void setLOFWeight(double newWeight){
        lofWeight = newWeight;
    }
    
}
