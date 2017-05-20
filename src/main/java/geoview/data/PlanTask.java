package geoview.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import geoview.calculators.MaintenanceScenariosCalculator;
import javafx.concurrent.Task;

public class PlanTask extends Task<List<Map<String, Object>>> {
	
	private int year;
	
	private String maintenanceType;
	
	private int budget;
	
	private List<Map<String, Object>> featureAttr;
	
	public PlanTask () {}
	
	public PlanTask(int newYear, String newMaintenanceType, int newBudget, List<Map<String, Object>> newFeatures) {
		year = newYear;
		maintenanceType = newMaintenanceType;
		budget = newBudget;
		featureAttr = newFeatures;
	}
	
	@Override
	public List<Map<String, Object>> call() {
		List<Map<String, Object>> featureAttr = new ArrayList<>();
		featureAttr = MaintenanceScenariosCalculator.calculateMaintenanceScenarios(year, maintenanceType, budget, featureAttr);
		return featureAttr;
	}

}
