package org.gozhe.android.cgt.utils;

import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;

public class AGSMapUtility {

	MapView map;

	public AGSMapUtility(MapView map) {
		this.map = map;
	}

	public void addDynamicMap(String dynamicMapURL) {
		
		//dynamicMapURL = "http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/Specialty/ESRI_StateCityHighway_USA/MapServer";

		// Creates a dynamic layer using service URL
		ArcGISDynamicMapServiceLayer dynamicLayer = new ArcGISDynamicMapServiceLayer(
				dynamicMapURL);
		// Adds layer into the 'MapView'
		map.addLayer(dynamicLayer);
		
		//map.removeLayer(dynamicLayer);

//		if (map.isLoaded()) {
//			// Retrieves the maps layers
//			Layer[] layers = map.getLayers();
//			// Toggles the dynamic layer's visibility
//			if (layers[1].isVisible()) {
//				layers[1].setVisible(false);
//			} else {
//				layers[1].setVisible(true);
//			}
//		}
	}
	
	
	
	

}
