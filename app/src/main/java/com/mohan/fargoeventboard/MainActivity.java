package com.mohan.fargoeventboard;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toolbar;

import com.mohan.fargoeventboard.data.AppRepository;
import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.fragments.EventListFragment;
import com.mohan.fargoeventboard.fragments.LoginFragment;
import com.mohan.fargoeventboard.fragments.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, EventListFragment.OnListFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static String SHARED_PREF_NAME = "sharedPrefs";
    public static final String LOGIN_TOKEN_PREF = "TOKEN_PREF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(LOGIN_TOKEN_PREF, "");
        setActionBar(toolbar);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Event event) {

    }
}
