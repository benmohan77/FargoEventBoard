package com.mohan.fargoeventboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.fragments.EventListFragment;
import com.mohan.fargoeventboard.fragments.LoginFragment;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, EventListFragment.OnListFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static String SHARED_PREF_NAME = "sharedPrefs";
    public static final String LOGIN_TOKEN_PREF = "TOKEN_PREF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(LOGIN_TOKEN_PREF, "");
        setSupportActionBar(toolbar);
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.eventListFragment);
        AppBarConfiguration config = new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(findViewById(R.id.nav_host_fragment)), config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().clear().commit();
                NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
                controller.navigate(R.id.action_global_loginFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Event event) {

    }
}
