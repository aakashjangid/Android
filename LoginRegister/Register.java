package com.wastedpotential.auctionapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Register extends AppCompatActivity {

    EditText nameBox, emailBox, passwordBox;
    Button registerButton;
    TextView loginLink;
    String name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailBox = (EditText) findViewById(R.id.emailBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        nameBox = (EditText) findViewById(R.id.nameBox);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginLink = (TextView) findViewById(R.id.loginLink);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailBox.getText().toString();
                password = passwordBox.getText().toString();
                name = nameBox.getText().toString();
                new HttpRequestTask(name,email,password).execute();

            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {

        private String name, email, password;

        public String getEmail() {
            return this.email;
        }

        public String getName(){
            return this.name;
        }

        public String getPassword() {
            return this.password;
        }

        public HttpRequestTask(String name, String email, String password){
            this.email=email;
            this.name=name;
            this.password=password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                final String url="http://192.168.43.190:8080/auctionapp/users/register/"+this.getName()+"/"+this.getEmail()+"/"+this.getPassword();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Boolean success = restTemplate.getForObject(url,Boolean.class);
                return success;
            }catch (Exception e){
                Log.e("Login",e.getMessage(),e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this,Login.class));
            }else{
                Toast.makeText(getApplicationContext(),"Registration Unsuccessful ! Please Try Again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this,Register.class));
            }
        }
    }


}
