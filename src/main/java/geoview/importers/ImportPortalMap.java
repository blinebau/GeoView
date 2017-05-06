package geoview.importers;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.PortalItem;

public class ImportPortalMap implements ImportedMap {
	
	private MapView view;
	
	public ImportPortalMap(PortalItem portalItem) {
		view = new MapView();
		ArcGISMap map = new ArcGISMap(portalItem);
		map.loadAsync();
		view.setMap(map);
	}
	
	public MapView getView() {
		return view;
	}
}
