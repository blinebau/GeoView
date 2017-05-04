package geoview.data;

import java.util.ArrayList;
import java.util.Map;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.portal.PortalItem;

public class FeatureData {
	
	private PortalItem portalItem;
	private ArrayList<Map<String, String>> featureAttributesCollection;
	
	public FeatureData(PortalItem newItem) {
		portalItem = newItem;
		featureAttributesCollection = collectAttributeMaps(newItem);
	}
	
	private ArrayList<Map<String, String>> collectAttributeMaps() {
		FeatureTable featureTable = loadFeatureTable();
		ArrayList<Map<String, String>> collectedMaps = new ArrayList<Map<String, String>>();
		return collectedMaps;
	}
	
	private FeatureTable loadFeatureTable() {
		FeatureLayer featurLayer = new FeatureLayer(portalItem, 1);
		featureLayer.loadAsync();
		
	}
	
	private Map<String, String> extractFeatureAttributes(Feature feature) {
		return null;
	}

}
