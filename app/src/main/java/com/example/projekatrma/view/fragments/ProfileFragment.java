package com.example.projekatrma.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projekatrma.R;
import com.example.projekatrma.view.activities.ChangePasswordActivity;
import com.example.projekatrma.view.activities.LoginActivity;


public class ProfileFragment extends Fragment {
    TextView textViewUsername;
    TextView textViewEmail;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }
    private void init(View view) {
        initView(view);
        initListeners(view);

    }
    private void initView(View view) {
        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        textViewUsername.setText(username);
        textViewEmail.setText(email);
    }
    private void initListeners(View view) {
        view.findViewById(R.id.buttonLogout).setOnClickListener(v -> {
            // U fragment-u koristimo getChildFragmentManager() za dobijanje fragment managera

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
            Context context = getContext();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            getActivity().finish();

        });
        view.findViewById(R.id.buttonChangePassword).setOnClickListener(v -> {
            // U fragment-u koristimo getChildFragmentManager() za dobijanje fragment managera

            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);


        });


        }


    }


