package geoview.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import geoview.calculators.MaintenanceScenariosCalculator;
import javafx.concurrent.Task;

public class PlanTask extends Task<List<Map<String, Object>>> {
	
	private int year;
	
	private String maintenanceType;
	
	private double budget;
	
	private List<Map<String, Object>> featureAttr;
	
	public PlanTask () {}
	
	public PlanTask(int newYear, String newMaintenanceType, double newBudget) {
		year = newYear;
		maintenanceType = newMaintenanceType;
		budget = newBudget;
	}
	
	@Override
	public List<Map<String, Object>> call() {
		List<Map<String, Object>> featureAttr = new ArrayList<>();
		featureAttr = MaintenanceScenariosCalculator.calculateMaintenanceScenarios(year, maintenanceType, budget, featureAttr);
		return featureAttr;
	}
	
	public void setFeatureAttr(List<Map<String, Object>> featureColl) {
		featureAttr = featureColl;
	}

	public int getYear() {
		return year;
	}
	
	public String getMaintenanceType() {
		return maintenanceType;
	}
	
	public double getBudget() {
		return budget;
	}

}
