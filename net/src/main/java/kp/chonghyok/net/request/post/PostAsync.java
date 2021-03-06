package kp.chonghyok.net.request.post;

import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import kp.chonghyok.net.callback.ArrayCallback;
import kp.chonghyok.net.callback.ObjCallback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kp.chonghyok.net.cookie.LocalCookieJar.getCookieJar;
import static kp.chonghyok.net.request.Constant.JSON;

public class PostAsync {
    public static <T1, T2> void postAsync(String url, T2 reqEntity, Class<T1> classOfT, ObjCallback<T1> callback) {
        Log.d("postAsync", url);
        RequestBody requestBody = RequestBody.create(new Gson().toJson(reqEntity), JSON);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cookieJar(getCookieJar())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.onProcess(response, classOfT);
            }
        });
    }

    public static <T1, T2> void postAsync(String url, T2 reqEntity, Type typeOfT, ArrayCallback<T1> callback) {
        Log.d("postAsync", url);
        RequestBody requestBody = RequestBody.create(new Gson().toJson(reqEntity), JSON);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cookieJar(getCookieJar())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.onProcess(response, typeOfT);
            }
        });
    }

    public static <T> void postAsync(String url, RequestBody reqBody, ObjCallback<Response> callback) {
        Log.d("postAsync", url);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .cookieJar(getCookieJar())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.onSuccess(response);
            }
        });
    }
}
