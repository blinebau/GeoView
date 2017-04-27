package geoview.importers;

import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.MobileMapPackage;
import com.esri.arcgisruntime.mapping.view.MapView;

public class ImportFileMap implements ImportedMap {
	
	private MapView view;
	
	public ImportFileMap(String pathId) {
		view = importMap(pathId);
	}
	
	public MapView importMap(String pathId) {
		MobileMapPackage mmpk = new MobileMapPackage(pathId);
		MapView view = new MapView();
		mmpk.loadAsync();
		
		mmpk.addDoneLoadingListener(() -> {
			if(mmpk.getLoadStatus() == LoadStatus.LOADED) {
				view.setMap(mmpk.getMaps().get(0));
			}
		});
		return view;
	}
	
	public MapView getView() {
		return view;
	}
}
