package kp.chonghyok.net.request.post;

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

import static kp.chonghyok.net.request.Constant.JSON;

public class PostAsync {
    public static <T1,T2> void postAsync(String url, T2 reqBody, Class<T1> classOfT, ObjCallback<T1> callback) {
        RequestBody requestBody = RequestBody.create(new Gson().toJson(reqBody), JSON);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.onProcess(response, classOfT);
            }
        });
    }

    public static <T1,T2> void postAsync(String url, T2 reqBody, Type typeOfT, ArrayCallback<T1> callback) {
        RequestBody requestBody = RequestBody.create(new Gson().toJson(reqBody), JSON);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.onProcess(response, typeOfT);
            }
        });
    }
}
