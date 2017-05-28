package geoview.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.data.ServiceFeatureTable.FeatureRequestMode;

import geoview.exporters.ExportTask;
import javafx.concurrent.Task;

public class FeatureData {
	
	private List<Map<String, Object>> attrColl;
	
	public FeatureData() {
		attrColl = new ArrayList<>();
	}
	
	public void retrieveMapData(DataTask dataTask, String serviceURL) {
		ServiceFeatureTable service_table = new ServiceFeatureTable(serviceURL);
		service_table.setFeatureRequestMode(FeatureRequestMode.MANUAL_CACHE);
		QueryParameters query = new QueryParameters();
		query.setWhereClause("OBJECTID NOT LIKE '" + "'");
		List<String> fields_pop = new ArrayList<>();
		fields_pop.add("*");
		ListenableFuture<FeatureQueryResult> query_result = service_table.populateFromServiceAsync(query, false, fields_pop);
		try {
			FeatureQueryResult result = query_result.get();
			dataTask.setQueryResult(result);
			initiateTask(dataTask);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}
	
	public void prepareTask(Task<List<Map<String, Object>>> task, String[] taskDetails) {

		task.setOnSucceeded(t -> {
			initiateTask(new ExportTask(task.getValue(), taskDetails));
		});
		initiateTask(task);
	}
	
	public void setAttrCollection(List<Map<String, Object>> newColl) {
		attrColl = newColl;
	}
	
	public List<Map<String, Object>> getAttrCollection() {
		return attrColl;
	}
	
	private void initiateTask(Task<?> task) {
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
}

