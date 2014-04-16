package labplc.codigocdmx.dejatecaer;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import labplc.codigocdmx.dejatecaer.utils.GPSTracker;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMyLocationButtonClickListener {

    GPSTracker gps;
    MapFragment mapaFragment;
    GoogleMap mapa;
    LatLng latlng;
    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapaFragment = (MapFragment) this.getFragmentManager().findFragmentById(R.id.mapa);
        mapa = mapaFragment.getMap();
        mapa.setOnMyLocationButtonClickListener(this);
        initMap();


    }

    @Override
    protected void onDestroy() {
        gps.stopUsingGPS();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMyLocationChange(Location location) {
        //Log.d("Location", location.toString());
    }

    private void initMap(){
        mapa.setMyLocationEnabled(true);
        //mapa.addMarker(new MarkerOptions().position(m))
        //mapa.get

        mapa.setOnMyLocationChangeListener(this);

        gps = new GPSTracker(this);

        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            lat = latitude;
            lon = longitude;
            latlng = new LatLng(lat, lon);


            mapa.addMarker(new MarkerOptions().position(latlng));
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));
            Toast.makeText(getApplicationContext(), "Tu ubicacion es: \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}
