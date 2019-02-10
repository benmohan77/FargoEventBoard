package com.mohan.fargoeventboard.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mohan.fargoeventboard.MainActivity;
import com.mohan.fargoeventboard.R;
import com.mohan.fargoeventboard.ViewModel.LoginViewModel;
import com.mohan.fargoeventboard.data.AppRepository;

import javax.inject.Inject;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class LoginFragment extends Fragment {

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
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                viewModel.handleLogin(usernameInput.getText().toString(), passwordInput.getText().toString(), new AppRepository.LoginCallback() {
                    @Override
                    public void onResponse(Boolean success, ErrorCode errorCode) {
                        if(success){
                          NavigateAndPop(view);
                        } else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch(errorCode){
                                        case LOGIN_FAILED:
                                            MainActivity.alertDialog.setMessage(getString(R.string.alert_error_login));
                                            break;
                                        case INVALID_CREDENTIALS:
                                            MainActivity.alertDialog.setMessage(getString(R.string.alert_error_credentials));
                                            break;
                                        default:
                                            MainActivity.alertDialog.setMessage(errorCode.toString());
                                    }
                                    MainActivity.alertDialog.setTitle(getString(R.string.alert_error_title));
                                    MainActivity.alertDialog.setPositiveButton(getString(R.string.alert_error_positive), null);
                                    MainActivity.alertDialog.create().show();
                                }
                            });
                        }
                    }
                });

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        if(viewModel.getLoginStatus()){
            NavigateAndPop(view);
        }
    }


    private void NavigateAndPop(View view){
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_eventListFragment);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop(){
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

}
