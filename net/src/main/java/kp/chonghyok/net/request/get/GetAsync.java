package kp.chonghyok.net.request.get;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import kp.chonghyok.net.callback.ArrayCallback;
import kp.chonghyok.net.callback.ObjCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static kp.chonghyok.net.cookie.LocalCookieJar.getCookieJar;

public class GetAsync {
    public static <T> void getAsync(String url, Class<T> classOfT, ObjCallback<T> callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(getCookieJar())
                .build();
        Request request = new Request.Builder()
                .url(url)
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

    public static <T> void getAsync(String url, Type typeOfT, ArrayCallback<T> callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(getCookieJar())
                .build();
        Request request = new Request.Builder().url(url).build();
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

    public static void getAsync(String url, ObjCallback<String> callback) {
        Log.d("getSync", url);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(getCookieJar())
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String html = body.string();
                        callback.onSuccess(html);
                    } else {
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }
        });
    }
}
