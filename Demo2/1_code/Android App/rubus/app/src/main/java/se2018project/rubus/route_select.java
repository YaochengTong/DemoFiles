package se2018project.rubus;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;

public class route_select extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    GoogleMap mGoogleMap;
    Marker[] marker = new Marker[20];
    String selectedRoute;
    String selectedStart;
    String selectedStop;
    String netid;
    boolean startAlreadySelected;
    boolean stopAlreadySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get netid from login
        Intent intent = getIntent();
        netid = intent.getStringExtra("netid");

        // google map
        if(googleServicesAvailable()) {
            initMap();
        } else {
            // don't load map
            Toast.makeText(getApplicationContext(), "Maps unavailable.",
                    Toast.LENGTH_SHORT).show();
        }

        // set route spinner
        Spinner route = findViewById(R.id.route_spinner);
        ArrayAdapter<CharSequence> route_adapter = ArrayAdapter.createFromResource(this,
                R.array.routes_array, android.R.layout.simple_spinner_item);
        route_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        route.setAdapter(route_adapter);

        // route spinner listener
        route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // get currently selected stop text as String
                selectedRoute = parent.getItemAtPosition(position).toString();

                // fill stop spinner based upon route selection
                switch(selectedRoute) {

                    case "A (Busch and College Ave.)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5027751,-74.4519048, 15);
                        break;

                    case "B (Busch and Livingston)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5214144,-74.4629102, 15);
                        setMarkersB();
                        drawRouteB();
                        break;

                    case "C (Busch)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5233403,-74.4609918, 15);
                        break;

                    case "EE (College Ave. and Cook Douglass)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5027751,-74.4519048, 15);
                        break;

                    case "F (College Ave. and Cook Douglass)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5027751,-74.4519048, 15);
                        break;

                    case "H (Busch and College Ave.)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5233403,-74.4609918, 15);
                        break;

                    case "LX (Livingston and College Ave.)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5027751,-74.4519048, 15);
                        break;

                    case "REX B (Busch. and Cook Douglass)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5233403,-74.4609918, 15);
                        break;

                    case "REX L (Livingston and Cook Douglass)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        goToLocationZoom(40.5233971,-74.4382171, 15);
                        break;

                    case "Weekend 1 (All Campuses)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        break;

                    case "Weekend 2 (All Campuses)":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        break;

                    case "New Brunsquick 1":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        break;

                    case "New Brunsquick 2":
                        startAlreadySelected = false;
                        stopAlreadySelected = false;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_selection) {

            Intent intent = new Intent(this, route_select.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_maps) {

            Intent intent = new Intent(this, maps.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://friendlychat-c8aef.firebaseapp.com/"));
            startActivity(intent);

        } else if (id == R.id.nav_info) {

            Intent intent = new Intent(this, info.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

            Intent intent = new Intent(this, login.class);
            Toast.makeText(getApplicationContext(), "Successfully logged out.",
                    Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // check for google play services
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Unable to connect to Google Play Services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public void setMarkersB () {

        MarkerOptions options = new MarkerOptions()
                .title("Livingston Student Center")
                .snippet("Dining Hall, Dunkin' Donuts, Sbarro, Rock Cafe")
                .position(new LatLng(40.5234446,-74.4367039));
        marker[0] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Quads")
                .snippet("Livingston Gym, Quad I/II/III")
                .position(new LatLng(40.5198387,-74.4335105));
        marker[1] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Health Center")
                .snippet("Health Services")
                .position(new LatLng(40.5234716,-74.4424773));
        marker[2] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Werblin Recreation Center")
                .snippet("Rear Entrance")
                .position(new LatLng(40.5186315,-74.4615638));
        marker[3] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Hill Center")
                .snippet("Physics Lecture Hall, SERC, CORE")
                .position(new LatLng(40.5214144,-74.4629102));
        marker[4] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Science Buildings")
                .snippet("ARC, Life Sciences Building, Pharmacy Building")
                .position(new LatLng(40.5239694,-74.4641867));
        marker[5] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Library of Science and Medicine")
                .snippet("Richardson Apartments, Nichols Apartments")
                .position(new LatLng(40.526319,-74.4661112));
        marker[6] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Busch Suites")
                .snippet("McCormick, Winkler, Thomas, Morrow, BME Building")
                .position(new LatLng(40.5258741,-74.4586444));
        marker[7] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Busch Student Center")
                .snippet("RU Recharge, Panera Bread, Gerlanda's Pizza, Moe's")
                .position(new LatLng(40.5234035,-74.4579497));
        marker[8] = mGoogleMap.addMarker(options);

        options = new MarkerOptions()
                .title("Livingston Plaza")
                .snippet("Starbucks, Rutgers Cinema, Henry's Diner, Qdoba")
                .position(new LatLng(40.5251395,-74.4386539));
        marker[9] = mGoogleMap.addMarker(options);
    }

    public void drawRouteB () {

        PolylineOptions options = new PolylineOptions()
                .add(new LatLng(40.522879,-74.440885))
                .add(new LatLng(40.525531,-74.438221))
                .add(new LatLng(40.524388,-74.436235))
                .add(new LatLng(40.524088,-74.436523))
                .add(new LatLng(40.523759,-74.436465))
                .add(new LatLng(40.523572,-74.436229))
                .add(new LatLng(40.523638,-74.435786))
                .add(new LatLng(40.523967,-74.435482))
                .add(new LatLng(40.523281,-74.434236))
                .add(new LatLng(40.522939,-74.432807))
                .add(new LatLng(40.521455,-74.432695))
                .add(new LatLng(40.520677,-74.432880))
                .add(new LatLng(40.519561,-74.433655))
                .add(new LatLng(40.518940,-74.434233))
                .add(new LatLng(40.522565,-74.440720))
                .add(new LatLng(40.522710,-74.440863))
                .add(new LatLng(40.522800,-74.440870))//here, bottom side of flat parallel
                .add(new LatLng(40.522885,-74.440969))
                .add(new LatLng(40.522916,-74.441163))
                .add(new LatLng(40.523372,-74.442174))
                .add(new LatLng(40.523670,-74.443441))
                .add(new LatLng(40.524068,-74.445166))
                .add(new LatLng(40.525689,-74.450554))
                .add(new LatLng(40.525268,-74.453983))
                .add(new LatLng(40.525458,-74.454734))
                .add(new LatLng(40.525815,-74.454931))
                .add(new LatLng(40.526236,-74.454787))
                .add(new LatLng(40.526456,-74.454438))
                .add(new LatLng(40.526484,-74.453967))
                .add(new LatLng(40.526069,-74.452829))
                .add(new LatLng(40.525429,-74.451949))
                .add(new LatLng(40.524530,-74.451138))
                .add(new LatLng(40.523705,-74.450705))
                .add(new LatLng(40.522603,-74.450485))
                .add(new LatLng(40.521617,-74.450599))
                .add(new LatLng(40.520239,-74.451176))
                .add(new LatLng(40.519311,-74.451805))
                .add(new LatLng(40.518333,-74.452833))
                .add(new LatLng(40.517918,-74.453569))
                .add(new LatLng(40.517795,-74.453904))
                .add(new LatLng(40.517691,-74.456405))
                .add(new LatLng(40.517609,-74.456893))
                .add(new LatLng(40.517397,-74.457387))
                .add(new LatLng(40.516667,-74.458460))
                .add(new LatLng(40.516520,-74.458857))
                .add(new LatLng(40.516455,-74.459275))
                .add(new LatLng(40.516484,-74.459672))
                .add(new LatLng(40.516639,-74.460134))
                .add(new LatLng(40.516948,-74.460632))
                .add(new LatLng(40.517442,-74.461062))
                .add(new LatLng(40.517691,-74.461153))
                .add(new LatLng(40.517820,-74.461087))
                .add(new LatLng(40.517945,-74.461149))
                .add(new LatLng(40.517991,-74.461232))
                .add(new LatLng(40.518001,-74.461289))
                .add(new LatLng(40.518294,-74.461437))
                .add(new LatLng(40.518621,-74.461516))
                .add(new LatLng(40.518923,-74.461665))
                .add(new LatLng(40.519127,-74.461798))
                .add(new LatLng(40.519789,-74.462276))
                .add(new LatLng(40.520226,-74.462526))
                .add(new LatLng(40.521021,-74.462838))
                .add(new LatLng(40.521392,-74.463039))
                .add(new LatLng(40.521567,-74.463154))
                .add(new LatLng(40.521884,-74.463202))
                .add(new LatLng(40.521999,-74.463463))
                .add(new LatLng(40.521959,-74.463605))
                .add(new LatLng(40.522049,-74.463811))
                .add(new LatLng(40.522721,-74.465874))
                .add(new LatLng(40.523286,-74.465478))
                .add(new LatLng(40.523679,-74.464914))
                .add(new LatLng(40.523654,-74.464492))
                .add(new LatLng(40.523691,-74.464400))
                .add(new LatLng(40.523977,-74.464214))
                .add(new LatLng(40.524094,-74.464201))
                .add(new LatLng(40.524363,-74.463983))
                .add(new LatLng(40.525237,-74.463353))
                .add(new LatLng(40.525687,-74.464896))
                .add(new LatLng(40.525875,-74.465296))
                .add(new LatLng(40.526749,-74.466931))
                .add(new LatLng(40.528269,-74.465560))
                .add(new LatLng(40.528322,-74.465468))
                .add(new LatLng(40.528330,-74.465343))
                .add(new LatLng(40.528298,-74.465229))
                .add(new LatLng(40.527394,-74.463470))
                .add(new LatLng(40.527073,-74.462847))
                .add(new LatLng(40.526945,-74.462528))
                .add(new LatLng(40.526702,-74.461684))
                .add(new LatLng(40.526351,-74.460283))
                .add(new LatLng(40.525617,-74.457883))
                .add(new LatLng(40.525455,-74.457952))
                .add(new LatLng(40.525324,-74.457936))
                .add(new LatLng(40.525221,-74.457872))
                .add(new LatLng(40.525100,-74.457749))
                .add(new LatLng(40.524557,-74.458576))
                .add(new LatLng(40.524510,-74.458614))
                .add(new LatLng(40.524441,-74.458636))
                .add(new LatLng(40.524412,-74.458792))
                .add(new LatLng(40.524209,-74.458817))
                .add(new LatLng(40.524139,-74.458769))
                .add(new LatLng(40.524039,-74.458551))
                .add(new LatLng(40.523829,-74.458232))
                .add(new LatLng(40.523729,-74.458139))
                .add(new LatLng(40.523377,-74.457935))
                .add(new LatLng(40.523308,-74.457857))
                .add(new LatLng(40.523295,-74.457686))
                .add(new LatLng(40.523162,-74.457553))
                .add(new LatLng(40.523094,-74.457416))
                .add(new LatLng(40.523012,-74.457089))
                .add(new LatLng(40.522916,-74.456933))
                .add(new LatLng(40.522766,-74.456834))
                .add(new LatLng(40.522207,-74.456707))
                .add(new LatLng(40.521932,-74.456708))
                .add(new LatLng(40.521641,-74.456790))
                .add(new LatLng(40.520933,-74.457171))
                .add(new LatLng(40.520404,-74.457594))
                .add(new LatLng(40.519433,-74.458782))
                .add(new LatLng(40.518050,-74.460775))
                .add(new LatLng(40.517950,-74.461166))
                .color(Color.BLUE)
                .width(30);
        mGoogleMap.addPolyline(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocationZoom(40.5027751,-74.4519048, 15);

        startAlreadySelected = false;
        // listener for window click
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                if (!startAlreadySelected) {
                    selectedStart = marker.getTitle();
                    Toast.makeText(getApplicationContext(), "Starting location selected, please select a destination.",
                            Toast.LENGTH_SHORT).show();
                    startAlreadySelected = true;
                }
                else if(selectedStart.equals(marker.getTitle())) {
                    Toast.makeText(getApplicationContext(), "Starting location and destination cannot be the same.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    selectedStop = marker.getTitle();
                    Toast.makeText(getApplicationContext(), "Destination selected.",
                            Toast.LENGTH_SHORT).show();
                    stopAlreadySelected = true;
                }
            }
        });

    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.animateCamera(update);

    }

    public void go_button (View view) {

        if(startAlreadySelected && stopAlreadySelected) {
            Intent intent = new Intent(this, status.class);
            intent.putExtra("route", selectedRoute);
            intent.putExtra("start", selectedStart);
            intent.putExtra("stop", selectedStop);
            intent.putExtra("netid",netid);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please select a starting location and destination before continuing.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}

