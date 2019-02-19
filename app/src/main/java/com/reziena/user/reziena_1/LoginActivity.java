package com.reziena.user.reziena_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener,AuthenticationListener{
    LinearLayout login, signin;
    private InputMethod.SessionCallback callback;
    SignInButton btn_login;
    LoginButton facebook_login;
    // 구글로그인 result 상수
    private static final int RC_SIGN_IN = 1000;
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    ImageView facebook, messageicon,google, twittericon,kakao;
    private LoginCallback mLoginCallback;
    private CallbackManager callbackManager;
    Intent intent;
    private Context mContext;
    private SessionCallback mKakaocallback;
    private String tokeninsta = null;
    private AppPreferences appPreferences = null;
    private AuthenticationDialog authenticationDialog = null;
    private View info = null;
    String fbname, fbemail, fbprofile;
    String gomail, goname, goprofile;
    String kaname, kaemail, kaprofile;
    String isname, isprofile;
    public static Activity loginactivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginactivity = LoginActivity.this;
        mLoginCallback = new LoginCallback();
        appPreferences = new AppPreferences(this);

        SharedPreferences sp_userName = getSharedPreferences("userName", MODE_PRIVATE);
        if (sp_userName!=null) {
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        tokeninsta = appPreferences.getString(AppPreferences.TOKEN);
        if (tokeninsta != null) {
            getUserInfoByAccessToken(tokeninsta);
        }

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        kakao=findViewById(R.id.message);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        signin = findViewById(R.id.signin);
        login = findViewById(R.id.login);
        btn_login = findViewById(R.id.googlelogin);
        twittericon=findViewById(R.id.twitter);
        tokeninsta = appPreferences.getString(AppPreferences.TOKEN);

        getAppKeyHash();
        twittericon.setOnClickListener(this);
        kakao.setOnClickListener(this);
        google.setOnClickListener(this);
        signin.setOnClickListener(this);
        login.setOnClickListener(this);
        facebook.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }


    public void login() {
        isprofile = appPreferences.getString(AppPreferences.PROFILE_PIC); //인스타그램 프로필
        //Picasso.with(this).load().into(pic);
        String id = appPreferences.getString(AppPreferences.USER_ID); //인스타그램 아이디
        isname = appPreferences.getString(AppPreferences.USER_NAME); //인스타그램 이름
        Intent intent = new Intent(getApplicationContext(),Signin2Activity.class);
        intent.putExtra("name",isname);
        intent.putExtra("profile",isprofile);
        startActivity(intent);

    }

    public void logout() {
        tokeninsta = null;
        info.setVisibility(View.GONE);
        appPreferences.clear();
    }

    public void onTokenReceived(String auth_token) {
        if (auth_token == null)
            return;
        appPreferences.putString(AppPreferences.TOKEN, auth_token);
        tokeninsta = auth_token;
        getUserInfoByAccessToken(tokeninsta);
    }

    private void getUserInfoByAccessToken(String token) {
        new RequestInstagramAPI().execute();
    }
    private class RequestInstagramAPI extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(getResources().getString(R.string.get_user_info_url) + tokeninsta);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("response", jsonObject.toString());
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    if (jsonData.has("id")) {
                        //сохранение данных пользователя
                        appPreferences.putString(AppPreferences.USER_ID, jsonData.getString("id")); //인스타그램 아이디
                        appPreferences.putString(AppPreferences.USER_NAME, jsonData.getString("username")); //인스타그램 이름
                        appPreferences.putString(AppPreferences.PROFILE_PIC, jsonData.getString("profile_picture")); //인스타그램 프로필 사진
                        isprofile = jsonData.getString("profile_picture");
                        isname = jsonData.getString("username");
                        //TODO: сохранить еще данные
                        login();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                //Toast toast = Toast.makeText(getApplicationContext(),"Ошибка входа!",Toast.LENGTH_LONG);
                //toast.show();
            }
        }
    }



    private void isKakaoLogin() {
        // 카카오 세션을 오픈한다
        mKakaocallback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(mKakaocallback);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, LoginActivity.this);
    }

    protected void KakaorequestMe() {

        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Toast.makeText(getApplicationContext(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG" , "오류로 카카오로그인 실패 ");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG" , "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                kaprofile = userProfile.getProfileImagePath();
                String userId = String.valueOf(userProfile.getId());
                kaname = userProfile.getNickname();
                kaemail = userProfile.getEmail();

                Log.e("success", "prifileUrl:" + kaprofile); //카카오톡 프로필 url
                Log.e("success", "userId:" + userId); //카카오톡 userid
                Log.e("success", "userName:" + kaname); //카카오톡 이름
                Log.e("success","usereemail"+kaemail); //카카오톡 이메일

                Intent intent = new Intent(getApplicationContext(),Signin2Activity.class);
                intent.putExtra("name",kaname);
                intent.putExtra("profile",kaprofile);
                intent.putExtra("email",kaemail);
                startActivity(intent);
            }

            @Override
            public void onNotSignedUp() {
                // 자동가입이 아닐경우 동의창
            }
        });
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            // 사용자 정보를 가져옴, 회원가입 미가입시 자동가입 시킴
            KakaorequestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.d("Fail", "Session CallBack Error > " + exception.getMessage());
        }
    }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                if (result.isSuccess()) {
                    // 로그인 성공 했을때

                    GoogleSignInAccount acct = result.getSignInAccount();
                    firebaseAuthWithGoogle(acct);

                    goprofile = acct.getPhotoUrl().toString();
                    goname = acct.getDisplayName();
                    gomail = acct.getEmail();
                    String personId = acct.getId();
                    String tokenKey = acct.getServerAuthCode();

                    Log.e("GoogleLogin", "personName=" + goname); //구글 이름
                    Log.e("GoogleLogin", "personEmail=" + gomail); //구글 이메일
                    Log.e("GoogleLogin", "personId=" + personId); //구글 아이디
                    Log.e("GoogleLogin", "tokenKey=" + tokenKey);

                    Intent intent = new Intent(getApplicationContext(),Signin2Activity.class);
                    intent.putExtra("name",goname);
                    intent.putExtra("profile",goprofile);
                    intent.putExtra("email",gomail);
                    startActivity(intent);

                } else {
                    Log.e("GoogleLogin", "login fail cause=" + result.getStatus().getStatusMessage());
                    // 로그인 실패 했을때
                }
            }
        }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "실패", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "성공", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signinintent (String name, String email, String profile){

    }

    private void initFacebook() {//FaceBook Init
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", String.valueOf(loginResult.getAccessToken()));
                        Log.d("Success", String.valueOf(Profile.getCurrentProfile().getId())); //페이스북 아이디
                        Log.d("Success", String.valueOf(Profile.getCurrentProfile().getName())); //페이스북 이름
                        Log.d("Success", String.valueOf(Profile.getCurrentProfile().getProfilePictureUri(300, 300))); //페이스북 이미지
                        requestUserProfile(loginResult);
                    }

                    @Override
                    public void onCancel() {

                        Log.e("나간다","나간다고");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }

    public void requestUserProfile(LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String email = response.getJSONObject().getString("email").toString();
                            String name = response.getJSONObject().getString("name").toString();
                            String profile = String.valueOf(Profile.getCurrentProfile().getProfilePictureUri(300, 300));
                            Intent intent = new Intent(getApplicationContext(),Signin2Activity.class);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            intent.putExtra("profile",profile);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                intent = new Intent(getApplicationContext(), LoginmainActivity.class);
                startActivity(intent);
                break;
            case R.id.facebook:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
                initFacebook();
                break;
            case R.id.google:
                btn_login.performClick();
                break;
            case R.id.googlelogin:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent,RC_SIGN_IN);
                break;
            case R.id.message:
                isKakaoLogin();
                break;
            case R.id.twitter:
                authenticationDialog = new AuthenticationDialog(this, this);
                authenticationDialog.setCancelable(true);
                authenticationDialog.show();
                break;

        }
    }
}
