package servinco.infosys_sol.com.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import servinco.infosys_sol.com.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    private static final String TAG = "MapActivity";
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //Vars
    private boolean mLocationPermissionGranted = false;
    // private FusedLocationProviderClient mFusedLocationProviderClient;


    private GoogleMap mMap;
    private Location mLastKnownLocation;

    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getLocationPermission();

       // mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void initMap() {
        Log.d(TAG, "initMap: Initialization Map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: Permissions for map");
        String[] permissions = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(MapsActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "getLocationPermission:  permissions granted");
                mLocationPermissionGranted = true;
                initMap();
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Requesting permissions");
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;

                }
                initMap();
                break;
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "Map is working fine", Toast.LENGTH_SHORT).show();


        if (mLocationPermissionGranted) {
            getLocation();



            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                return;
            }



            if(mLastKnownLocation != null){
                latitude = mLastKnownLocation.getLatitude();
                longitude = mLastKnownLocation.getLongitude();

                LatLng myPosition = new LatLng(latitude,longitude);

                CameraPosition position= new  CameraPosition.Builder().
                        target(myPosition).zoom(17).bearing(19).tilt(30).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                mMap.addMarker(new MarkerOptions().position(myPosition).title("start"));

                //initViews();

            }
        }
    }
    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: Move the camera to lat long");

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }
    protected void getLocation() {
        if (mLocationPermissionGranted) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastKnownLocation = locationManager.getLastKnownLocation(bestProvider);
            if (mLastKnownLocation != null) {
                Log.e("TAG", "GPS is on");
                latitude = mLastKnownLocation.getLatitude();
                longitude = mLastKnownLocation.getLongitude();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), DEFAULT_ZOOM));
                Toast.makeText(MapsActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

            } else {
                //This is what you need:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        }
        else {
            Log.d(TAG, "getLocation: Please enable the location");
            Toast.makeText(this, "Please enable the location", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }


    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            LatLng myPosition = new LatLng(latitude, longitude);

            CameraPosition position = new CameraPosition.Builder().
                    target(myPosition).zoom(17).bearing(19).tilt(30).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
            mMap.addMarker(new MarkerOptions().position(myPosition).title("start"));

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}