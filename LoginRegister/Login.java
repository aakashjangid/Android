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


public class Login extends AppCompatActivity {

    EditText emailBox, passwordBox;
    Button loginButton;
    TextView registerLink;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailBox = (EditText) findViewById(R.id.emailBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerLink = (TextView)findViewById(R.id.registerLink);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailBox.getText().toString();
                password = passwordBox.getText().toString();
                new HttpRequestTask(email,password).execute();

            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, User> {

        private String email, password;

        public String getEmail() {
            return this.email;
        }

        public String getPassword() {
            return this.password;
        }

        public HttpRequestTask(String email, String password){
            this.email=email;
            this.password=password;
        }

        @Override
        protected User doInBackground(Void... params) {
            try{
                final String url="http://192.168.43.190:8080/auctionapp/users/"+this.getEmail()+"/"+this.getPassword();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                User user = restTemplate.getForObject(url,User.class);
                return user;
            }catch (Exception e){
                Log.e("Login",e.getMessage(),e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            if(user!=null){
                Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,Dashboard.class));
            }else{
                Toast.makeText(getApplicationContext(),"Wrong Credentials", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,Login.class));
            }
        }
    }

}