package me.bkkn.users.network;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedTokenInterceptor implements Interceptor {
    SharedPreferences sharedPreferences;

    public ReceivedTokenInterceptor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String token = response.header("Token");
        if (token != null && !token.isEmpty()) {
            sharedPreferences.edit().putString("token", token).apply();
        }
        return response;
    }
}
