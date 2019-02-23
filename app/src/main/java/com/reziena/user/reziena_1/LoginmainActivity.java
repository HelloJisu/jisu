package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginmainActivity extends AppCompatActivity {

    private EditText etID, etPassword;
    private Button btnLogin;
    private String id;
    LinearLayout login,signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);


        etID = findViewById(R.id.loginemail);
        etPassword = findViewById(R.id.loginpasswor);
        signin = findViewById(R.id.signin_login);
        login = findViewById(R.id.login_login);

        Drawable alphalogin = login.getBackground();
        Drawable alphasignin = signin.getBackground();

        alphalogin.setAlpha(50);
        alphasignin.setAlpha(50);

        etID.setText("reziena");
        //etPassword.setText("1234");


        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("Range")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = etID.getText().toString();

                if( email.contains("@")&& email.contains(".")) {

                    etPassword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @SuppressLint("Range")
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String password = etPassword.getText().toString();
                            alphalogin.setAlpha(255);//알파값 20
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else{

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etID.getText().toString();
                String pw = etPassword.getText().toString();

                Login task = new Login();
                task.execute("http://"+HomeActivity.IP_Address+"/login.php", id, pw);

            }
        });
    }

    class Login extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.e("onPostExecute", "response - " + result);

            if (result == null){
                Log.e("onPostExecute", "erre");
            }
            else {
                showResult(result);
            }
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            id = params[1];
            String pw = params[2];

            String postParameters = "id="+id+"&pw="+pw;

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);;

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.e("postParameters", postParameters);
                outputStream.flush();
                outputStream.close();

                // response
                InputStream inputStream;
                int responseStatusCode = httpURLConnection.getResponseCode();
                String responseStatusMessage = httpURLConnection.getResponseMessage();
                Log.e("response", "POST response Code - " + responseStatusCode);
                Log.e("response", "POST response Message - "+ responseStatusMessage);

                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    // 정상적인 응답 데이터
                    Log.e("inputstream: ", "정상적");
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    // error
                    Log.e("inputstream: ", "비정상적: " + httpURLConnection.getErrorStream());
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {
                Log.e("ERROR_loginmainActivity", "InsertDataError ", e);
            }
            return null;
        }

        private void showResult(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("login");

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);

                    String name = item.getString("name");

                    SharedPreferences sp_userName = getSharedPreferences("userName", MODE_PRIVATE);
                    SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sp_userName.edit();
                    SharedPreferences.Editor editor2 = sp_userID.edit();
                    editor1.putString("userName", name);
                    editor2.putString("userID", id);
                    editor1.commit();
                    editor2.commit();
                    Log.e("Login ", name+"님 로그인");
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            } catch (JSONException e) {
                Log.d("JSON", "showResult : ", e);
            }

        }
    }
}
