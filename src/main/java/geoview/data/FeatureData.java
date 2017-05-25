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
			initiateDataTask(dataTask);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}
	
	public void initiateTask(Task<List<Map<String, Object>>> task, String[] taskDetails) {
		task.setOnSucceeded(t -> {
			initiateExport(task.getValue(), taskDetails);
		});
		initiateDataTask(task);
	}
	
	public void setAttrCollection(List<Map<String, Object>> newColl) {
		attrColl = newColl;
	}
	
	private void initiateExport(List<Map<String, Object>> exportedFeatures, String[] exportDetails) {
		ExportTask exportTask = new ExportTask(exportedFeatures, exportDetails);
		initiateExportTask(exportTask);
	}
	
	private void initiateDataTask(Task<List<Map<String, Object>>> task) {
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
	
	private void initiateExportTask(Task<Void> task) {
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
}

