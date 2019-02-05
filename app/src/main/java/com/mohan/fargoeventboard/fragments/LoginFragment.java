package com.mohan.fargoeventboard.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mohan.fargoeventboard.R;
import com.mohan.fargoeventboard.ViewModel.LoginViewModel;

import javax.inject.Inject;


public class LoginFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    @BindView(R.id.username)
    public EditText usernameInput;

    @BindView(R.id.password)
    public EditText passwordInput;

    @BindView(R.id.login_btn)
    public Button loginButton;

    @Inject
    private LoginViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the LoginFragment
     * @return
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view );
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.handleLogin(usernameInput.getText().toString(), passwordInput.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
