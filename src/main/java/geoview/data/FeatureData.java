package geoview.data;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import javafx.stage.FileChooser;

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
			e.printStackTrace();
		}
	}
	
	public void prepareTask(Task<List<Map<String, Object>>> task, List<String> taskDetails) {
		task.setOnSucceeded(t -> {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("pdf files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(null);
			Task<File> exportTask = new ExportTask(task.getValue(), taskDetails, file.getPath());
			exportTask.setOnSucceeded(e -> {
				try {
					Desktop.getDesktop().open(exportTask.get());
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				} finally {
					
				}
			});
			exportTask.setOnFailed(e -> {
				System.out.println(exportTask.getException().getMessage());
			});
			initiateTask(exportTask);
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

