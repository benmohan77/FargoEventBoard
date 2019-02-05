package com.mohan.fargoeventboard.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mohan.fargoeventboard.R;
import com.mohan.fargoeventboard.ViewModel.LoginViewModel;
import com.mohan.fargoeventboard.data.AppRepository;

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
    ViewModelProvider.Factory viewModelFactory;

    private LoginViewModel viewModel;
    private View view;

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


        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view );

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.handleLogin(usernameInput.getText().toString(), passwordInput.getText().toString(), new AppRepository.LoginCallback() {
                    @Override
                    public void onResponse(Boolean success) {
                        if(success){
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_eventListFragment);
                        }
                    }
                });

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstnaceState){
        super.onActivityCreated(savedInstnaceState);
        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        if(viewModel.getLoginStatus()){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_eventListFragment);
        }
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
