package com.reziena.user.reziena_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Map;

import com.reziena.user.reziena_1.model.UserModel;

import pyxis.uzuki.live.sociallogin.facebook.FacebookLogin;
import pyxis.uzuki.live.sociallogin.google.GoogleLogin;
import pyxis.uzuki.live.sociallogin.impl.OnResponseListener;
import pyxis.uzuki.live.sociallogin.impl.ResultType;
import pyxis.uzuki.live.sociallogin.impl.SocialType;
import pyxis.uzuki.live.sociallogin.impl.UserInfoType;
import pyxis.uzuki.live.sociallogin.kakao.KakaoLogin;
import pyxis.uzuki.live.sociallogin.naver.NaverLogin;

public class LoginActivityy extends AppCompatActivity {

    private Context mContext;
    private KakaoLogin kakaoModule;
    private GoogleLogin googleModule;
    private NaverLogin naverModule;
    private FacebookLogin facebookModule;

    ImageView facebook, logo, google, twittericon ,kakao;

    SocialType loginType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();

        UiInit();
        ModuleInit();
    }

    private void ModuleInit() {
        kakaoModule = new KakaoLogin(this, new OnResponseListener() {
            @Override
            public void onResult(SocialType social, ResultType result, Map<UserInfoType, String> resultMap) {
                if( result == ResultType.SUCCESS )
                {
                    //        Log.d("Login ::", resultMap.get(UserInfoType.NICKNAME));

                    // TODO : 빈틈 서버에 토큰 전송
                    // 조회 요청


                    // TODO : IF OK
                    //                 if(Success)
                    {
                        doLogin(resultMap,SocialType.KAKAO);
                    }

                }
            }
        });
        googleModule = new GoogleLogin(this, new OnResponseListener() {
            @Override
            public void onResult(SocialType social, ResultType result, Map<UserInfoType, String> resultMap) {
                // Log.d("Login :: Google", resultMap.get(UserInfoType.NAME));
                if( result == ResultType.SUCCESS )
                {
                    doLogin(resultMap,SocialType.GOOGLE);
                }
            }
        });
        facebookModule = new FacebookLogin(this, new OnResponseListener() {
            @Override
            public void onResult(SocialType social, ResultType result, Map<UserInfoType, String> resultMap) {
                Log.d("Login :: Facebook", resultMap.get(UserInfoType.NAME));
                doLogin(resultMap,SocialType.FACEBOOK);
            }
        });
    }

    private void UiInit() {
        google = findViewById(R.id.google);
        kakao = findViewById(R.id.message);
        facebook = findViewById(R.id.facebook);
        logo = findViewById(R.id.logo);

        google.setOnClickListener( v -> {
            googleModule.onLogin();
            loginType = SocialType.GOOGLE;
        });
        kakao.setOnClickListener( v -> {
            kakaoModule.onLogin();
            loginType = SocialType.KAKAO;
        });
        facebook.setOnClickListener( v -> {
            facebookModule.onLogin();
            loginType = SocialType.FACEBOOK;
        });
        logo.setOnClickListener( v -> {
            kakaoModule.logout();
            googleModule.logout();
            facebookModule.logout();
        });
    }

    private void doLogout(){
    }

    private void doLogin(Map<UserInfoType, String> resultMap, SocialType type) {
        //      Log.d("Login :: Google", resultMap.get(UserInfoType.NAME));
        Bundle bundle = new Bundle();
        UserModel userModel = new UserModel();
        userModel.setEmail( resultMap.get(UserInfoType.EMAIL));
        userModel.setLoginType( type );
        userModel.setPhotoUrl( resultMap.get(UserInfoType.PROFILE_PICTURE));
        userModel.setUserName( resultMap.get(UserInfoType.NAME));
        bundle.putSerializable("UserModel",userModel);
        Intent intent = new Intent(LoginActivityy.this, Signin2Activity.class);
        intent.putExtras( bundle );
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        kakaoModule.onDestroy();
        googleModule.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch( loginType )
        {
            case KAKAO:
                kakaoModule.onActivityResult(requestCode,resultCode,data);
                break;
            case GOOGLE:
                googleModule.onActivityResult(requestCode,resultCode,data);
                break;
            case FACEBOOK:
                facebookModule.onActivityResult(requestCode,resultCode,data);
                break;
        }
    }
}