package geoview.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.data.ServiceFeatureTable.FeatureRequestMode;

import geoview.exporters.ExportTask;

public class FeatureData {
	
	private ArrayList<Map<String, Object>> attrColl;
	
	private String importStatus;
	
	public FeatureData() {
		attrColl = new ArrayList<>();
		importStatus = "";
	}
	
	public String retrieveMapData() {
		ServiceFeatureTable service_table = new ServiceFeatureTable("http://services7.arcgis.com/DQPcd87LglSVJI8c/arcgis/rest/services/Sewer_Test_Map/FeatureServer/1");
		service_table.setFeatureRequestMode(FeatureRequestMode.MANUAL_CACHE);
		QueryParameters query = new QueryParameters();
		query.setWhereClause("OBJECTID NOT LIKE '" + "'");
		ArrayList<String> fields_pop = new ArrayList<>();
		fields_pop.add("*");
		ListenableFuture<FeatureQueryResult> query_result = service_table.populateFromServiceAsync(query, false, fields_pop);
		try {
			FeatureQueryResult result = query_result.get();
			DataTask dataTask = new DataTask(result);
			dataTask.setOnSucceeded(t -> { 
				attrColl = dataTask.getValue();
				importStatus = dataTask.getMessage();
			});
			dataTask.setOnFailed(t -> {
				if(dataTask.getException() != null) {
					importStatus = dataTask.getException().getMessage();
				}
			});
			Thread th = new Thread(dataTask);
			th.setDaemon(true);
			th.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return importStatus;
	}
	
	public void queryMaintenancePlan(int year, String method, int budget) {
		//call to maintenance plan(year, method, budget, attrColl);
		//found in calculators
	}
	
	public void initiateRangeQueryTask(int lower, int higher, String fieldName) {
		QueryTask queryTask = new QueryTask(this.attrColl, lower, higher, fieldName);
		queryTask.setOnSucceeded(t -> {
			ExportTask exportTask = new ExportTask(queryTask.getValue());
			Thread th = new Thread(exportTask);
			th.setDaemon(true);
			th.start();
		});
		Thread th = new Thread(queryTask);
		th.setDaemon(true);
		th.start();
	}
}

