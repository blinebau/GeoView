package geoview.importers;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.PortalItem;
import com.esri.arcgisruntime.portal.Portal;

public class ImportPortalMap implements ImportedMap {
	
	private MapView view;
	
	public ImportPortalMap(String portalId) {
		view = importMap(portalId);
	}
	
	public MapView importMap(String portalId) {
	        Portal portal = new Portal("http://www.arcgis.com");
	        PortalItem portalItem = new PortalItem(portal, portalId);
	        ArcGISMap map = new ArcGISMap(portalItem);
	        MapView view = new MapView();
	        view.setMap(map);
	        return view;
	} 
	
	public MapView getView() {
		return view;
	}
}
