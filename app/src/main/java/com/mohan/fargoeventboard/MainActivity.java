package com.mohan.fargoeventboard;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toolbar;

import com.mohan.fargoeventboard.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar(toolbar);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
