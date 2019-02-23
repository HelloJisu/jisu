package com.reziena.user.reziena_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private String tokenfb = null;
    private AppPreferences appPreferences = null;
    private AuthenticationDialog authenticationDialog = null;
    private View info = null;
    String fbname, fbemail, fbprofile, fbid;
    String gomail, goname, goprofile, goid;
    String kaname, kaemail, kaprofile, kaid;
    String isname, isprofile, isid;
    AccessToken accessToken;
    AccessToken tokenfab;
    public static Activity loginactivity;
    Drawable alphalogin;
    Drawable alphasignin;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginactivity = LoginActivity.this;
        mLoginCallback = new LoginCallback();
        appPreferences = new AppPreferences(this);
        accessToken = AccessToken.getCurrentAccessToken();



        Log.e("start", "login");
        SharedPreferences userName = getSharedPreferences("userName", MODE_PRIVATE);
        String name = userName.getString("userName", "userName=none");
        Log.e("userName", name);
        if (!name.equals("userName=none")) {
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

        if(tokenfb != null){
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
            loginManager.registerCallback(callbackManager, mLoginCallback);
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

        alphalogin = login.getBackground();
        alphasignin = signin.getBackground();

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
        isid = appPreferences.getString(AppPreferences.USER_ID); //인스타그램 아이디
        isname = appPreferences.getString(AppPreferences.USER_NAME); //인스타그램 이름

        getUser task = new getUser();
        task.execute("http://"+R.string.IP_Address+"/getUser.php", isid, isname, isprofile, "instagram");
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
                kaid = String.valueOf(userProfile.getId());
                kaname = userProfile.getNickname();//카카오톡


                Log.e("success", "prifileUrl:" + kaprofile); //카카오톡 프로필 url
                Log.e("success", "userId:" + kaid); //카카오톡 userid
                Log.e("success", "userName:" + kaname); //카카오톡 이름

                getUser task = new getUser();
                task.execute("http://"+R.string.IP_Address+"/getUser.php", kaid, kaname, kaprofile, "kakao");
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
                goid = acct.getId();
                String tokenKey = acct.getServerAuthCode();

                Log.e("GoogleLogin", "personName=" + goname); //구글 이름
                Log.e("GoogleLogin", "personId=" + goid); //구글 아이디
                Log.e("GoogleLogin", "tokenKey=" + tokenKey);

                getUser task = new getUser();
                task.execute("http://"+R.string.IP_Address+"/getUser.php", goid, goname, goprofile, "google");

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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

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
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
                loginManager.registerCallback(callbackManager, mLoginCallback);
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

    public class LoginCallback implements FacebookCallback<LoginResult> {
        // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.

        String name;
        String profile;
        String email;
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("Callback :: ", "onSuccess");
            requestMe(loginResult.getAccessToken());
            tokenfab=loginResult.getAccessToken();
            tokenfb=loginResult.getAccessToken().toString();
        }


        // 로그인 창을 닫을 경우, 호출됩니다.
        @Override
        public void onCancel() {
            Log.e("Callback :: ", "onCancel");

            getUser task = new getUser();
            task.execute("http://"+R.string.IP_Address+"/getUser.php", fbid, fbname, fbprofile, "facebook");
        }

        // 로그인 실패 시에 호출됩니다.
        @Override
        public void onError(FacebookException error) {
            Log.e("Callback :: ", "onError : " + error.getMessage());
        }


        // 사용자 정보 요청
        public void requestMe(AccessToken token) {// 페이스북
            GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                fbname = response.getJSONObject().getString("name").toString();
                                fbprofile = String.valueOf(Profile.getCurrentProfile().getProfilePictureUri(300, 300));
                                fbid = String.valueOf(Profile.getCurrentProfile().getId());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        }
    }

    class getUser extends AsyncTask<String, Void, String> {
        String id, name, profile, kind;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.e("login-onPostExecute", "response - " + result);

            if (result.contains("yes")) {
                SharedPreferences sp_userName = getSharedPreferences("userName", MODE_PRIVATE);
                SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
                SharedPreferences sp_profile = getSharedPreferences("profile", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp_userName.edit();
                SharedPreferences.Editor editor2 = sp_userID.edit();
                SharedPreferences.Editor editor3 = sp_profile.edit();
                editor1.putString("userName", name);
                editor2.putString("userID", id);
                editor3.putString("profile", profile);
                editor1.commit();
                editor2.commit();
                editor3.commit();
                Log.e("Login ", name+"님 로그인");
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.e("onPostExecute", "회원없음");
                Intent intent1 = new Intent(getApplicationContext(),Signin2Activity.class);
                intent1.putExtra("id",id);
                intent1.putExtra("name",name);
                intent1.putExtra("profile",profile);
                startActivity(intent1);
                finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            id = params[1];
            name = params[2];
            profile = params[3];
            kind = params[4];

            id += "_"+kind;

            String postParameters = "id="+id;

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
                Log.e("ERROR", "InsertDataError ", e);
            }
            return null;
        }
    }

    public void onResume() {
        super.onResume();
        alphalogin.setAlpha(255);
        alphasignin.setAlpha(255);

    }
}