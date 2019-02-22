package com.reziena.user.reziena_1.LoginTemple;

import android.content.Context;

import pyxis.uzuki.live.sociallogin.naver.NaverLogin;

public final class LoginFactory {

    public enum LoginType {
        None, Kakao, Facebook, Instagram, Google
    }

    public static ILogin createLogin(Context context, LoginType type) {
        return createLogin(context, type, null, null, null);
    }

    public static ILogin createLogin(Context context, LoginType type, String clientId, String clientSecret) {
        return createLogin(context, type, clientId, clientSecret, null);
    }

    public static ILogin createLogin(Context context, LoginType type, String clientId, String clientSecret, String redirectUrl) {
        switch (type) {
            case Kakao:
                return new KakaoLogin(context);
            case Facebook:
                return new FacebookLogin();
            case Instagram:
                return new InstagramLogin(context, clientId, redirectUrl);
            case Google:
                return new GoogleLogin(context);
        }

        return new NoLogin();
    }
}