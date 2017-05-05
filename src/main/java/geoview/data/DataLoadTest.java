package geoview.data;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.data.ServiceFeatureTable.FeatureRequestMode;

public class DataLoadTest {
	
	public static void main(String [] args) throws InterruptedException, ExecutionException {
		//http://services7.arcgis.com/DQPcd87LglSVJI8c/arcgis/rest/services/Sewer_Test_Map/FeatureServer/1
		ServiceFeatureTable sewer_table = new ServiceFeatureTable("http://services7.arcgis.com/DQPcd87LglSVJI8c/arcgis/rest/services/Sewer_Test_Map/FeatureServer/1");
		sewer_table.setFeatureRequestMode(FeatureRequestMode.MANUAL_CACHE);
		QueryParameters query = new QueryParameters();
		query.setWhereClause("OBJECTID NOT LIKE '" + "'");
		ArrayList<String> fields_pop = new ArrayList<>();
		fields_pop.add("*");
		ListenableFuture<FeatureQueryResult> result = sewer_table.populateFromServiceAsync(query, false, fields_pop);
		FeatureQueryResult fqr = result.get();
		if(fqr.iterator().hasNext()) {
			Feature f = fqr.iterator().next();
			f.getAttributes().entrySet().stream().forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
		}
		
	}

}
