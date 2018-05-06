package se2018project.rubus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class status extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String netid;
    String selectedRoute;
    String selectedStart;
    String selectedStop;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        netid = intent.getStringExtra("netid");
        selectedRoute = intent.getStringExtra("route");
        selectedStart = intent.getStringExtra("start");
        selectedStop = intent.getStringExtra("stop");

        TextView route = findViewById(R.id.route);
        TextView start = findViewById(R.id.start);
        TextView stop = findViewById(R.id.stop);

        route.setText(selectedRoute);
        start.setText(selectedStart);
        stop.setText(selectedStop);

        // firebase stuff
        login.firebase.child("bus").push().setValue(new Bus(netid,selectedStart, selectedStop, selectedRoute, sdf.format(new Date()), sdfTime.format(new Date())));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, route_select.class);
            startActivity(intent);
            finish();
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
            finish();

        } else if (id == R.id.nav_settings) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://friendlychat-c8aef.firebaseapp.com/"));
            startActivity(intent);

        } else if (id == R.id.nav_info) {

            Intent intent = new Intent(this, info.class);
            startActivity(intent);
            finish();

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
}
