package geoview.extrnal;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;

public class ExternalData {
	
	public static void main(String [] args) {
	
		Portal portal = new Portal("http://www.arcgis.com");
		PortalItem portalItem = new PortalItem(portal, "684270f597f54475af0f251d4ff7c7bd");
		
		FeatureLayer layer = new FeatureLayer(portalItem, 4);
		layer.loadAsync();
		
		while(layer.getLoadStatus() != LoadStatus.LOADED) {}
		
		FeatureTable table = layer.getFeatureTable();
		
		table.loadAsync();
		
		while(table.getLoadStatus() != LoadStatus.LOADED) {}
		
		
		QueryParameters query = new QueryParameters();
		query.setWhereClause("Creator='" + "HatfieldPA" + "'");
		
		ListenableFuture<FeatureQueryResult> result = table.queryFeaturesAsync(query);
			
		FeatureQueryResult fqr = null;
		
		try {
			while(result.get() == null) {}
			fqr = result.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(f.getAttributes().size());
		
		Iterator it = fqr.iterator();
		while(it.hasNext()) {
			Feature f = (Feature) it.next();
			System.out.println(f.getAttributes().entrySet());
		}
		
/*		for(Feature f : fqr) {
			f.getAttributes().entrySet().stream().forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
		}*/
	}

	
}
