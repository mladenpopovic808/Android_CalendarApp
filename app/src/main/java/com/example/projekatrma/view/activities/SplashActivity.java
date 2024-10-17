package com.example.projekatrma.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.projekatrma.R;


public class SplashActivity extends AppCompatActivity {



    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSplashScreen();
        //trebas da proveris da li je korisnik ulogovan,za vreme ucitavanja splashScreena.
        setContentView(R.layout.activity_main);
    }
    private void loadSplashScreen(){
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);

        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean isUserLoggedIn=isLoggedIn();
            Intent intent;
            if(isUserLoggedIn){
                intent = new Intent(this, MainActivity.class);
            }else{
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
            return false;

        });

    }
    private boolean isLoggedIn(){
        // Retrieve the username and password values from the SharedPreferences object.
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        //treba reci aplikaciji koji korisnik je u pitanju.
        return !(username.isEmpty() || password.isEmpty());

    }
}