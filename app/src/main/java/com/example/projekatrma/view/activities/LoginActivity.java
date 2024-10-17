package com.example.projekatrma.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekatrma.R;
import com.example.projekatrma.utils.PasswordCheck;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Button loginButton;
    private TextView warningEmail;
    private TextView warningPassword;
    private TextView warningUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init() {
        initView();
        initListeners();

    }

    private void initView() {

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        emailInput = findViewById(R.id.emailInput);
        loginButton = findViewById(R.id.buttonMedium);
        warningEmail = findViewById(R.id.warningEmail);
        warningPassword = findViewById(R.id.warningPassword);
        warningUsername = findViewById(R.id.warningUsername);

        warningEmail.setVisibility(TextView.INVISIBLE);
        warningPassword.setVisibility(TextView.INVISIBLE);
        warningUsername.setVisibility(TextView.INVISIBLE);
    }

    private void initListeners() {
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            String email = emailInput.getText().toString();
            areInputsEmpty(username,password,email);//prikazace crveni tekst ispod ako je prazno

            if(!PasswordCheck.isPasswordCharacterValid(password)) {
                Toast.makeText(this, "Sifra mora da ima >5 karaktera,barem jedno veliko slovo,barem jedan broj" +
                        " i ne sme sadrzati specijalne karaktere: (~#^|$%&*!) ", Toast.LENGTH_SHORT).show();

            }else if(!PasswordCheck.isPasswordCorrectForUser(this.getApplicationContext(),username,password)){
                Toast.makeText(this, "Korisnik ne postoji ili je sifra pogresna", Toast.LENGTH_SHORT).show();

            }else{
                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                //treba napraviti novog usera i njegov viewModel.
                sharedPreferences.edit().putString("username",username).apply();
                sharedPreferences.edit().putString("password",password).apply();
                sharedPreferences.edit().putString("email",email).apply();

                Toast.makeText(this, "Uspesno ste se ulogovali-Dobrodosli "+username, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });


    }

    private void areInputsEmpty(String username,String password,String email){
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            if (username.isEmpty()) {
                warningUsername.setVisibility(TextView.VISIBLE);
            }
            if (password.isEmpty()) {
                warningPassword.setVisibility(TextView.VISIBLE);
            }
            if (email.isEmpty()) {
                warningEmail.setVisibility(TextView.VISIBLE);
            }
        }

    }

}
