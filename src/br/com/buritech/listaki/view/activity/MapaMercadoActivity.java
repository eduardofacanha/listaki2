package br.com.buritech.listaki.view.activity;

import android.graphics.Color;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.buritech.listaki.presenter.GpsTracker;


public class MapaMercadoActivity extends FragmentActivity {

    private static final String TAG = MapaMercadoActivity.class.getSimpleName();
    //private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GpsTracker gps;
    private LatLng SUPER_MERCADO_DB = new LatLng(-3.090183,-60.057365); //SETANDO O VALOR EM LATITUDE E LONGITUDE
    private Marker marker;
    private Polyline polyline;
    private List<LatLng> list;
    private long distance;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mercado_mapa);


        setUpMap();

    }


    @Override
    protected void onResume() {

        super.onResume();


    }


    void setUpMap(){

        gps  = new GpsTracker(MapaMercadoActivity.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // mMap = map;
        if(gps.canGetLocation()) {
            LatLng latLngAtual = new LatLng(gps.getLatitude(), gps.getLongitude());

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLngAtual, 15);

            map.animateCamera(update);
            map.setMyLocationEnabled(true);

            addMarker(SUPER_MERCADO_DB, "DB", "Supermercado");

            getRoute(latLngAtual, SUPER_MERCADO_DB);

        } else {
            gps.showSettingsAlert();
        }


    }


    void addMarker(LatLng latLngMercado, String nomeMercado, String descricao){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLngMercado).title(nomeMercado).snippet(descricao);
        Marker marker = map.addMarker(markerOptions);

    }

    // WEB CONNECTION
    public void getRoute(final LatLng origin, final LatLng destination){
        new Thread(){
            public void run(){
						/*String url= "http://maps.googleapis.com/maps/api/directions/json?origin="
								+ origin+"&destination="
								+ destination+"&sensor=false";*/
                String url= "http://maps.googleapis.com/maps/api/directions/json?origin="+origin.latitude+","+origin.longitude+"&destination="+destination.latitude+","+destination.longitude+"&sensor=true&mode=driving";


                HttpResponse response;
                HttpGet request;
                AndroidHttpClient client = AndroidHttpClient.newInstance("route");

                request = new HttpGet(url);
                try {
                    response = client.execute(request);
                    final String answer = EntityUtils.toString(response.getEntity());

                    runOnUiThread(new Runnable(){
                        public void run(){
                            try {
                                //Log.i("Script", answer);
                                list = buildJSONRoute(answer);
                                drawRoute();
                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //Desenha a rota
    public void drawRoute(){
        PolylineOptions po;

        if(polyline == null){
            po = new PolylineOptions();

            for(int i = 0, tam = list.size(); i < tam; i++){
                po.add(list.get(i));
            }

            po.color(Color.RED).width(4);
            polyline = map.addPolyline(po);
        }
        else{
            polyline.setPoints(list);
        }
    }

    // PARSER JSON
    public List<LatLng> buildJSONRoute(String json) throws JSONException{
        JSONObject result = new JSONObject(json);
        JSONArray routes = result.getJSONArray("routes");

        distance = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getInt("value");

        JSONArray steps = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        List<LatLng> lines = new ArrayList<LatLng>();

        for(int i=0; i < steps.length(); i++) {
            Log.i("Script", "STEP: LAT: "+steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat")+" | LNG: "+steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));


            String polyline = steps.getJSONObject(i).getJSONObject("polyline").getString("points");

            for(LatLng p : decodePolyline(polyline)) {
                lines.add(p);
            }

            Log.i("Script", "STEP: LAT: "+steps.getJSONObject(i).getJSONObject("end_location").getDouble("lat")+" | LNG: "+steps.getJSONObject(i).getJSONObject("end_location").getDouble("lng"));
        }

        return(lines);
    }


    // DECODE POLYLINE
    private List<LatLng> decodePolyline(String encoded) {

        List<LatLng> listPoints = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            Log.i("Script", "POL: LAT: "+p.latitude+" | LNG: "+p.longitude);
            listPoints.add(p);
        }
        return listPoints;
    }
}











