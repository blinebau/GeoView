
package geoview.controller;

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


public class ImportedMapController {
        private ArcGISMap map;
        private MapView mapView;    
	
	@FXML
	private StackPane mapArea;
	
	@FXML
	public void initialize() {
            /*
            Current Implementation imports from portal. In the future we will ask whether user
            wants to import from portal or upload a feature table.
            */
            assert(mapArea != null);  

            Portal portal = new Portal("http://www.arcgis.com");
            PortalItem portalItem = new PortalItem(portal, "fddc173f1d3043f184054db18342aa3e");
            map = new ArcGISMap(portalItem);
            mapView = new MapView();
            mapView.setMap(map);
            mapArea.getChildren().add(mapView);
	}	

}
