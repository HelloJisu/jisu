package com.reziena.user.reziena_1;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    LinearLayout login_signin, signin_signin;
    ImageView logincheck;
    public  Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        login_signin = findViewById(R.id.login_signin);
        signin_signin = findViewById(R.id.signin_sigin);
        logincheck = findViewById(R.id.sang);
        Drawable alphalogin = login_signin.getBackground();
        Drawable alphasignin = signin_signin.getBackground();

        alphalogin.setAlpha(50);
        alphasignin.setAlpha(50);


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("Range")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = etEmail.getText().toString();

                if( email.contains("@")&& email.contains(".")) {
                    etEmail.setTextColor(Color.parseColor("#450969"));
                    // 비밀번호 일치 검사
                    etPasswordConfirm.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @SuppressLint("Range")
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String password = etPassword.getText().toString();
                            String confirm = etPasswordConfirm.getText().toString();

                            if( password.equals(confirm) ) {
                                logincheck.setVisibility(View.VISIBLE);
                                etPasswordConfirm.setTextColor(Color.parseColor("#450969"));
                                logincheck.setImageResource(R.drawable.logincheck);
                                alphasignin.setAlpha(255);//알파값 20

                            } else {
                                logincheck.setVisibility(View.VISIBLE);
                                etPasswordConfirm.setTextColor(Color.parseColor("#9E0958"));
                                logincheck.setImageResource(R.drawable.loginx);

                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    signin_signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // 이메일 입력 확인
                            if( etEmail.getText().toString().length() == 0 ) {
                                Toast.makeText(SigninActivity.this, "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                                etEmail.requestFocus();
                                return;
                            }

                            // 비밀번호 입력 확인
                            if( etPassword.getText().toString().length() == 0 ) {
                                Toast.makeText(SigninActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                                etPassword.requestFocus();
                                return;
                            }

                            // 비밀번호 확인 입력 확인
                            if( etPasswordConfirm.getText().toString().length() == 0 ) {
                                Toast.makeText(SigninActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                                etPasswordConfirm.requestFocus();
                                return;
                            }

                            // 비밀번호 일치 확인
                            if( !etPassword.getText().toString().equals(etPasswordConfirm.getText().toString()) ) {
                                Toast.makeText(SigninActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                                etPassword.setText("");
                                etPasswordConfirm.setText("");
                                etPassword.requestFocus();
                                return;
                            }

                            Intent intent = new Intent(getApplicationContext(),Signin2Activity.class);
                            intent.putExtra("email",etEmail.getText().toString());
                            intent.putExtra("password",etPassword.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    });

                    etPasswordConfirm.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String password = etPassword.getText().toString();
                            String confirm = etPasswordConfirm.getText().toString();

                            if(password.equals(confirm)){

                            }
                            else{

                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Signin2Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
