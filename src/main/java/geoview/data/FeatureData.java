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
	
	public FeatureData() {
		attrColl = new ArrayList<>();
	}
	
	public void retrieveMapData() {
		ServiceFeatureTable service_table = new ServiceFeatureTable("http://services7.arcgis.com/DQPcd87LglSVJI8c/arcgis/rest/services/Sewer_Test_Map/FeatureServer/1");
		service_table.setFeatureRequestMode(FeatureRequestMode.MANUAL_CACHE);
		QueryParameters query = new QueryParameters();
		query.setWhereClause("OBJECTID NOT LIKE '" + "'");
		ArrayList<String> fields_pop = new ArrayList<>();
		fields_pop.add("*");
		ListenableFuture<FeatureQueryResult> query_result = service_table.populateFromServiceAsync(query, false, fields_pop);
		try {
			FeatureQueryResult result = query_result.get();
			DataTask data_task = new DataTask(result);
			data_task.setOnSucceeded(t -> { 
				attrColl = data_task.getValue(); 
			});
			Thread th = new Thread(data_task);
			th.setDaemon(true);
			th.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void queryBySCI(int fromSCI, int toSCI) {
		QueryTask queryTask = new QueryTask(this.attrColl, fromSCI, toSCI, true);
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
	
	public void queryByPredictiveFailure(int fromAge, int toAge) {
		
	}
	
	public void queryMaintenancePlan(int year, String method, int budget) {
		//call to maintenance plan(year, method, budget, attrColl);
		//found in calculators
	}
	
	public ArrayList<Map<String, Object>> getAttrColl() {
		return attrColl;
	}
}
