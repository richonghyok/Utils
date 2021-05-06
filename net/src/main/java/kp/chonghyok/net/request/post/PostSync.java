package kp.chonghyok.net.request.post;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import kp.chonghyok.net.response.ResponseEntity;
import kp.chonghyok.net.response.ResponseResult;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static kp.chonghyok.net.cookie.LocalCookieJar.getCookieJar;
import static kp.chonghyok.net.request.Constant.JSON;

public class PostSync {
    @Nullable
    public static <T1 extends ResponseEntity, T2> List<T1> postSync(String url, T2 reqEntity, Type typeOfT) {
        Thread mThread;
        RequestBody requestBody = RequestBody.create(new Gson().toJson(reqEntity), JSON);
        ResponseResult<List<T1>> responseResult = new ResponseResult<>();
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(getCookieJar())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
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
                                List<T1> t1List = gson.fromJson(result, typeOfT);
                                responseResult.setResult(t1List);
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

    @Nullable
    public static <T1 extends ResponseEntity, T2> T1 postSync(String url, T2 reqEntity, Class<T1> classOfT) {
        Thread mThread;
        ResponseResult<T1> responseResult = new ResponseResult<>();
        RequestBody requestBody = RequestBody.create(new Gson().toJson(reqEntity), JSON);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(getCookieJar())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
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
                                T1 t1 = gson.fromJson(result, classOfT);
                                responseResult.setResult(t1);
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
}
