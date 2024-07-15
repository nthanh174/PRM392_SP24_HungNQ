package com.example.shopf.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.shopf.Activities.ChangePwActivity;
import com.example.shopf.Activities.LoginActivity;
import com.example.shopf.Activities.UserActivity;
import com.example.shopf.R;

public class User extends Fragment {

    private View view;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user";
    private static final String KEY_USERNAME = "username";

    private ConstraintLayout orderHistoryBtn;
    private ConstraintLayout accountManager;
    private ConstraintLayout favoriteProductBtn;
    private ConstraintLayout orderStatusBtn;
    private ConstraintLayout changePw2;
    private ConstraintLayout btnLogout;

    private void onClickAccountManager(View view) {
        Intent i = new Intent(getActivity(), UserActivity.class);
        i.putExtra("username", sharedPreferences.getString(KEY_USERNAME, ""));
        startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve any arguments if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        bindingView();
        bindingAction();
        return view;
    }

    private void bindingView() {
        orderHistoryBtn = view.findViewById(R.id.orderHistoryBtn);
        accountManager = view.findViewById(R.id.accountManager);
        favoriteProductBtn = view.findViewById(R.id.favoriteProductBtn);
        orderStatusBtn = view.findViewById(R.id.orderStatusBtn);
        btnLogout = view.findViewById(R.id.btnLogout);
        changePw2 = view.findViewById(R.id.changePw2);
        TextView txtusername = view.findViewById(R.id.txtusername);
        ImageView imgAva = view.findViewById(R.id.imgAva);

        String username = sharedPreferences.getString(KEY_USERNAME, "");
        txtusername.setText(username);
        imgAva.setImageResource(R.drawable.profile);
    }

    private void bindingAction() {
        accountManager.setOnClickListener(this::onClickAccountManager);
        btnLogout.setOnClickListener(this::onBtnLogoutClick);
        changePw2.setOnClickListener(this::onClickChangePw);
    }

    private void onClickChangePw(View view) {
        Intent i = new Intent(getActivity(), ChangePwActivity.class);
        i.putExtra(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, ""));
        startActivity(i);
    }

    private void onBtnLogoutClick(View view) {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERNAME);  // Clearing the username
        editor.apply();
        startActivity(i);
        getActivity().finish();  // Optional: Finish the current activity
    }
}
