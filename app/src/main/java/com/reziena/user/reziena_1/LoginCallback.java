package com.reziena.user.reziena_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginCallback extends AppCompatActivity implements FacebookCallback<LoginResult> {
    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e("Callback :: ", "onSuccess");
        requestMe(loginResult.getAccessToken());
    }


    // 로그인 창을 닫을 경우, 호출됩니다.
    @Override
    public void onCancel() {
        Log.e("Callback :: ", "onCancel");
    }

    // 로그인 실패 시에 호출됩니다.
    @Override
    public void onError(FacebookException error) {
        Log.e("Callback :: ", "onError : " + error.getMessage());
    }


    // 사용자 정보 요청
    public void requestMe(AccessToken token) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String email = response.getJSONObject().getString("email").toString();
                            String name = response.getJSONObject().getString("name").toString();
                            String profile = String.valueOf(Profile.getCurrentProfile().getProfilePictureUri(300, 300));
                            Intent intent = new Intent(getApplicationContext(),Signin2Activity.class);
                            intent.putExtra("name",email);
                            intent.putExtra("profile",profile);
                            intent.putExtra("email",email);
                            startActivity(intent);

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