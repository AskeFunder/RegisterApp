package com.example.registerapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registerapp.R;
import com.example.registerapp.model.User;
import com.example.registerapp.UserLocalStore;
import com.example.registerapp.UserRepository;

public class LoginActivity extends AppCompatActivity {

    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegister;
    UserLocalStore userLocalStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLocalStore = new UserLocalStore(this);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogin = (Button) findViewById(R.id.loginButton);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository userRepository = new UserRepository(getApplicationContext());

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = userRepository.authenticateCredentials(username, password);

                if (user != null) {
                    userLocalStore.storeUserData(user);
                    userLocalStore.setUserLoggedIn(true);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong credentials - try again", Toast.LENGTH_SHORT).show();
                }

                CheckBox cbRemember = findViewById(R.id.cbRemember);
                if (cbRemember.isChecked()) {
                    userLocalStore.setRememberedUsername(etUsername.getText().toString());
                } else {
                    userLocalStore.setRememberedUsername(null);
                }
            }
        });

        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterIntent();
            }
        });

        CheckBox cbRemember = findViewById(R.id.cbRemember);

        String rememberedUsername = userLocalStore.getRememberedUsername();
        if (rememberedUsername != null) {
            etUsername.setText(rememberedUsername);

            cbRemember.setChecked(true);
        } else {
            cbRemember.setChecked(false);
        }
    }

    public void goRegisterIntent(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
