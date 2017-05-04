package geoview.importers;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.PortalItem;
import com.esri.arcgisruntime.portal.Portal;

public class ImportPortalMap implements ImportedMap {
	
	private MapView view;
	
	public ImportPortalMap(PortalItem portalItem) {
		view = importMap(portalItem);
	}
	
	public MapView importMap(PortalItem portalItem) {
	        ArcGISMap map = new ArcGISMap(portalItem);
	        MapView view = new MapView();
	        view.setMap(map);
	        return view;
	} 
	
	public MapView getView() {
		return view;
	}
}
