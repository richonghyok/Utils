package kp.chonghyok.net.request.get;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import kp.chonghyok.net.response.ResponseEntity;
import kp.chonghyok.net.response.ResponseResult;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static kp.chonghyok.net.cookie.LocalCookieJar.getCookieJar;

public class GetSync {
    public static <T extends ResponseEntity> List<T> getSync(String url, Type typeOfT) {
        Log.d("getSync", url);
        Thread mThread;
        ResponseResult<List<T>> responseResult = new ResponseResult<>();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        Runnable runnable = () -> {
            try {
                Response response = call.execute();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        try {
                            String result = body.string();
                            Gson gson = new Gson();
                            responseResult.setResult(gson.fromJson(result, typeOfT));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        mThread = new Thread(runnable);
        mThread.start();
        try {
            mThread.join();
            return responseResult.getResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends ResponseEntity> T getSync(String url, Class<T> classOfT) {
        Log.d("getSync", url);
        Thread mThread;
        ResponseResult<T> responseResult = new ResponseResult<>();
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        Runnable runnable = () -> {
            try {
                Response response = call.execute();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        try {
                            String result = body.string();
                            Gson gson = new Gson();
                            try {
                                T res = gson.fromJson(result, classOfT);
                                responseResult.setResult(res);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        mThread = new Thread(runnable);
        mThread.start();
        try {
            mThread.join();
            return responseResult.getResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSync(String url) {
        Log.d("getSync", url);
        Thread mThread;
        ResponseResult<String> responseResult = new ResponseResult<>();
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
        Runnable runnable = () -> {
            try {
                Response response = call.execute();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        responseResult.setResult(body.string());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        mThread = new Thread(runnable);
        mThread.start();
        try {
            mThread.join();
            return responseResult.getResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
