package com.example.registerapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.registerapp.R;
import com.example.registerapp.model.User;
import com.example.registerapp.UserRepository;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button bSignup;
    EditText etName, etAge, etUsername, etPassword;

    private String spinnerItemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.signupName);
        etAge = (EditText) findViewById(R.id.signupAge);
        etUsername = (EditText) findViewById(R.id.signupUsername);
        etPassword = (EditText) findViewById(R.id.signupPassword);
        bSignup = (Button) findViewById(R.id.signUpFormButton);
        final Spinner countrySpinner = (Spinner) findViewById(R.id.countrySpinner);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Spinner spinner = findViewById(R.id.countrySpinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User registeredUser = null;
                String username = etUsername.getText().toString();

                try {
                    String password = etPassword.getText().toString();
                    String name = etName.getText().toString();
                    int age = Integer.parseInt(etAge.getText().toString());
                    String language = spinnerItemSelected;

                    registeredUser = new User(name, username, password, age, language);

                    Toast.makeText(getApplicationContext(), registeredUser.toString(), Toast.LENGTH_SHORT).show();

                } catch (NumberFormatException eNFE) {
                    Toast.makeText(getApplicationContext(), "That is not an age", Toast.LENGTH_SHORT).show();
                }

                UserRepository userRepository = new UserRepository(getApplicationContext());

                if (registeredUser != null) {
                    if (!userRepository.userExists(username)) {
                        userRepository.registerUser(registeredUser);
                        System.out.println(userRepository.getUser(username).toString() + " has been registered");
                    } else {
                        System.out.println("User already exists");
                    }
                }
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerItemSelected = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
