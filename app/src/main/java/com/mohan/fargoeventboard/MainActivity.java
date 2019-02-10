package com.mohan.fargoeventboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.fragments.EventListFragment;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements  EventListFragment.OnListFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static String SHARED_PREF_NAME = "sharedPrefs";
    public static final String LOGIN_TOKEN_PREF = "TOKEN_PREF";
    public static final String LAST_LOAD_EVENTS = "LAST_LOAD_EVENTS";

    public static AlertDialog.Builder alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = new AlertDialog.Builder(this);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(LOGIN_TOKEN_PREF, "");
        setSupportActionBar(toolbar);
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.eventListFragment);
        AppBarConfiguration config = new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(findViewById(R.id.nav_host_fragment)), config);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        //Handle Logout and back button
        switch (id){
            case android.R.id.home:
                controller.navigateUp();
                break;
            case R.id.action_logout:
                this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().clear().commit();
                controller.navigate(R.id.action_global_loginFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Event event) {

    }
}
