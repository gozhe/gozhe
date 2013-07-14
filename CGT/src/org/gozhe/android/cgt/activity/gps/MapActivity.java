package org.gozhe.android.cgt.activity.gps;

import org.gozhe.android.R;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnLongPressListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.ags.query.QueryTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {

	MapView mMapView = null;
	ArcGISTiledMapServiceLayer tileLayer;
	ArcGISDynamicMapServiceLayer myLayer;
	MyTouchListener myListener;
	String restUrl = "";
	GraphicsLayer graphicsLayer;
	
	ProgressDialog progress;
	
	Button btn_draw;
	
	String cgtURL="http://192.168.1.104:8399/arcgis/rest/services/CGT/MapServer";
	String mapURL = "http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/PublicSafety/PublicSafetyBasemap/MapServer";
	
	final String[] geometryTypes = new String[] { "Point", "Polyline","Polygon" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.map);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_b);
		
	    TextView tv_title=(TextView)findViewById(R.id.tv_title_info);
	    
	    tv_title.setText("地图显示");
	    
		mMapView = (MapView) findViewById(R.id.map);
		
		/* create a @ArcGISTiledMapServiceLayer */
		tileLayer = new ArcGISTiledMapServiceLayer(mapURL);
		
		/* create a @ArcGISDynamicMapServiceLayer */
		myLayer = new ArcGISDynamicMapServiceLayer(cgtURL);
		// Add tiled layer to MapView
		mMapView.addLayer(myLayer);
		
			
		graphicsLayer =  new GraphicsLayer();
		mMapView.addLayer(graphicsLayer);
		myListener = new MyTouchListener(MapActivity.this, mMapView);
		mMapView.setOnTouchListener(myListener);
		btn_draw=(Button)findViewById(R.id.map_btn_draw);
		btn_draw.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
		
		Layer[] layers = mMapView.getLayers();
		
		mMapView.setOnLongPressListener(this.longPressListener);
	}
	
	
	
	private OnLongPressListener longPressListener = new OnLongPressListener(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void onLongPress(float lx, float ly) {
			// TODO Auto-generated method stub
			
			graphicsLayer.removeAll();
			
			SimpleRenderer sr = new SimpleRenderer(
					new SimpleFillSymbol(Color.RED));
			graphicsLayer.setRenderer(sr);
			
			
			String targetLayer = cgtURL.concat("/57");//区县级行政区划
			String[] queryParams = { targetLayer, "FID > 2" };
			AsyncQueryTask ayncQuery = new AsyncQueryTask();
			ayncQuery.execute(queryParams);
			
			
			
			//Point pt = mMapView.toMapPoint(lx, ly);		
			//String targetLayer = cgtURL.concat("/57");//区县级行政区划
			//Query query = new Query();
			//query.setGeometry(pt);
			//query.setGeometry(new Envelope(120.97530215999998,38.71983303,123.51892211999998,40.20518997));
			//query.setOutSpatialReference(SpatialReference.create(4326));
			//query.setSpatialRelationship(SpatialRelationship.INTERSECTS);//相交
			//query.setReturnGeometry(true);
			
			//query.setWhere("FID > 2");
			//QueryTask querytask = new QueryTask(targetLayer);
			
		}
		
	};
	
	
	private void showDialog(){
		new AlertDialog.Builder(MapActivity.this)
			.setTitle("选择类型")
			.setItems(geometryTypes, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					graphicsLayer.removeAll();
					String geomType = geometryTypes[which];
					if (geomType.equalsIgnoreCase("Polygon")) {
						myListener.setType("POLYGON");
						
					} else if (geomType.equalsIgnoreCase("Polyline")) {
						myListener.setType("POLYLINE");
						
					} else if (geomType.equalsIgnoreCase("Point")) {
						myListener.setType("POINT");
						
					}
				}
			}).create().show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.unpause();
	}
	
	
	
	private class myAsyncQueryTask extends AsyncTask<String,>{
		
	}
	
	/**
	 * 
	 * Query Task executes asynchronously.
	 * 
	 */
	private class AsyncQueryTask extends AsyncTask<String, Void, FeatureSet> {

		protected void onPreExecute() {
			progress = ProgressDialog.show(MapActivity.this, "",
					"Please wait....query task is executing");

		}

		/**
		 * First member in parameter array is the query URL; second member is
		 * the where clause.
		 */
		protected FeatureSet doInBackground(String... queryParams) {
			if (queryParams == null || queryParams.length <= 1)
				return null;
			

			String url = queryParams[0];
			Query query = new Query();
			String whereClause = queryParams[1];
			SpatialReference sr = SpatialReference.create(4326);
			query.setGeometry(new Envelope(120.97530215999998,38.71983303,123.51892211999998,40.20518997));
			query.setOutSpatialReference(sr);
			query.setReturnGeometry(true);
			query.setWhere(whereClause);

			QueryTask qTask = new QueryTask(url);
			FeatureSet fs = null;

			try {
				fs = qTask.execute(query);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return fs;
			}
			return fs;

		}

		protected void onPostExecute(FeatureSet result) {
		
			if (result != null) {
				Graphic[] grs = result.getGraphics();

				if (grs.length > 0) {
					graphicsLayer.addGraphics(grs);					
				}

			}
			progress.dismiss();
		}

	}
	
	/*
	 * MapView's touch listener
	 */
	class MyTouchListener extends MapOnTouchListener {
		
		MultiPath poly;
		String type = "";
		Point startPoint = null;

		public MyTouchListener(Context context, MapView view) {
			
			super(context, view);
		}

		public void setType(String geometryType) {
			this.type = geometryType;
		}

		public String getType() {
			return this.type;
		}
		
		/*
		 * Invoked when user single taps on the map view. This event handler
		 * draws a point at user-tapped location, only after "Draw Point" is
		 * selected from Spinner.
		 * 
		 * @see
		 * com.esri.android.map.MapOnTouchListener#onSingleTap(android.view.
		 * MotionEvent)
		 */
		public boolean onSingleTap(MotionEvent e) {
			if (type.length() > 1 && type.equalsIgnoreCase("POINT")) {
				graphicsLayer.removeAll();
				Graphic graphic = new Graphic(mMapView.toMapPoint(new Point(e.getX(), e
						.getY())),new SimpleMarkerSymbol(Color.RED,25,STYLE.CIRCLE));
				//graphic.setGeometry();
				graphicsLayer.addGraphic(graphic);
				
				return true;
			}
			return false;

		}

		/*
		 * Invoked when user drags finger across screen. Polygon or Polyline is
		 * drawn only when right selected is made from Spinner
		 * 
		 * @see
		 * com.esri.android.map.MapOnTouchListener#onDragPointerMove(android
		 * .view.MotionEvent, android.view.MotionEvent)
		 */
		public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
			if (type.length() > 1
					&& (type.equalsIgnoreCase("POLYLINE") || type
							.equalsIgnoreCase("POLYGON"))) {

				Point mapPt = mMapView.toMapPoint(to.getX(), to.getY());

				/*
				 * if StartPoint is null, create a polyline and start a path.
				 */
				if (startPoint == null) {
					graphicsLayer.removeAll();
					poly = type.equalsIgnoreCase("POLYLINE") ? new Polyline()
							: new Polygon();
					startPoint = mMapView.toMapPoint(from.getX(), from.getY());
					poly.startPath((float) startPoint.getX(),
							(float) startPoint.getY());

					/*
					 * Create a Graphic and add polyline geometry
					 */
					Graphic graphic = new Graphic(startPoint,new SimpleLineSymbol(Color.RED,5));

					/*
					 * add the updated graphic to graphics layer
					 */
					graphicsLayer.addGraphic(graphic);
				}

				poly.lineTo((float) mapPt.getX(), (float) mapPt.getY());
				
				return true;
			}
			return super.onDragPointerMove(from, to);

		}

		@Override
		public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {
			if (type.length() > 1
					&& (type.equalsIgnoreCase("POLYLINE") || type
							.equalsIgnoreCase("POLYGON"))) {

				/*
				 * When user releases finger, add the last point to polyline.
				 */
				if (type.equalsIgnoreCase("POLYGON")) {
					poly.lineTo((float) startPoint.getX(),
							(float) startPoint.getY());
					graphicsLayer.removeAll();
					graphicsLayer.addGraphic(new Graphic(poly,new SimpleFillSymbol(Color.RED)));
					
				}
				graphicsLayer.addGraphic(new Graphic(poly,new SimpleLineSymbol(Color.BLUE,5)));
				startPoint = null;
				
				return true;
			}
			return super.onDragPointerUp(from, to);
		}
	}
	
}
