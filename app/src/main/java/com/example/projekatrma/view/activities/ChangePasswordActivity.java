package com.example.projekatrma.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projekatrma.R;
import com.example.projekatrma.utils.PasswordCheck;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText firstPassword;
    private EditText secondPassword;
    private TextView warningFirstPassword;
    private TextView warningSecondPassword;
    private Button button;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        initView();
        initListeners();
    }
    private void initView() {
        firstPassword = findViewById(R.id.firstPasswordInput);
        secondPassword = findViewById(R.id.secondPasswordInput);
        warningFirstPassword = findViewById(R.id.warningFirstPassword);
        warningSecondPassword = findViewById(R.id.warningSecondPassword);
        button = findViewById(R.id.submit_button);

        warningFirstPassword.setVisibility(TextView.INVISIBLE);
        warningSecondPassword.setVisibility(TextView.INVISIBLE);


    }
    private void initListeners() {
        button.setOnClickListener(v -> {

            String firstPasswordInput = firstPassword.getText().toString();
            String secondPasswordInput = secondPassword.getText().toString();
            if(areInputsEmpty(firstPasswordInput,secondPasswordInput)){
                return;
            }
            if(!arePasswordsTheSame(firstPasswordInput,secondPasswordInput)){
                return;
            }
            if(!checkPasswordValidation(firstPasswordInput)){
                return;
            }
            if(!sameAsCurrentPassword(firstPasswordInput)){
                return;
            }

            PasswordCheck.savePassword(this.getApplicationContext(),sharedPreferences.getString("username",""),firstPasswordInput);
            Toast.makeText(this, "Sifra je promenjena", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    public boolean sameAsCurrentPassword(String firstPasswordInput) {
        if (firstPasswordInput.equals(sharedPreferences.getString("password",""))) {
            Toast.makeText(this, "Sifra mora biti razlicita od trenutne", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean areInputsEmpty(String firstPasswordInput, String secondPasswordInput) {
        if (firstPasswordInput.isEmpty()) {
            warningFirstPassword.setVisibility(TextView.VISIBLE);
            return true;
        }
        if (secondPasswordInput.isEmpty()) {
            warningSecondPassword.setVisibility(TextView.VISIBLE);
            return true;
        }
        return false;
    }
    public boolean arePasswordsTheSame(String firstPasswordInput, String secondPasswordInput) {
        if (!firstPasswordInput.equals(secondPasswordInput)) {
            Toast.makeText(this, "Sifre se ne poklapaju", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean checkPasswordValidation(String firstPasswordInput) {
        if (!PasswordCheck.isPasswordCharacterValid(firstPasswordInput)) {
            Toast.makeText(this, "Sifra mora da sadrzi bar jedno veliko slovo i bar jedan broj", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
