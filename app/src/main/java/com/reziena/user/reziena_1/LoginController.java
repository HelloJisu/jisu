package com.reziena.user.reziena_1;

import android.content.Context;

import com.reziena.user.reziena_1.LoginTemple.ILogin;
import com.reziena.user.reziena_1.LoginTemple.LoginFactory;
import com.reziena.user.reziena_1.LoginTemple.LoginFactory.LoginType;

/**
 * Created by thomas on 16. 2. 29..
 */
public final class LoginController {

    private static LoginController instance;
    private final Context context;
    private ILogin login;

    public static LoginController from(Context context) {
        if (instance == null || instance.context != context) {
            instance = new LoginController(context);
        }

        return instance;
    }

    private LoginController(Context context) {
        this.context = context;

        setup(LoginType.None);
    }

    public ILogin setup(LoginType type) {
        switch (type) {
            case Instagram:
                login = LoginFactory.createLogin(context, type, "c3bbe70b4b274e4b948bd974928da387", null, "https://api.instagram.com/");
                break;
            default:
                login = LoginFactory.createLogin(context, type);
                break;
        }

        return login;
    }

    public ILogin getCurrentLogin() {
        return login;
    }
}