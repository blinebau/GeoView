
package geoview.importers;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.PortalItem;
import com.esri.arcgisruntime.portal.Portal;


import java.io.IOException;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
//fddc173f1d3043f184054db18342aa3e

public class ImportPortalMap {
	
	public static MapView importMap(String portalId) {
	        Portal portal = new Portal("http://www.arcgis.com");
	        PortalItem portalItem = new PortalItem(portal, portalId);
	        ArcGISMap map = new ArcGISMap(portalItem);
	        MapView mapView = new MapView();
	        mapView.setMap(map);
	        return mapView;
	} 

}
