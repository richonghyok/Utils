package kp.chonghyok.network.request.get;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import kp.chonghyok.network.response.ResponseEntity;
import kp.chonghyok.network.response.ResponseResult;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetSync {
    public static <T extends ResponseEntity> List<T> getSync(String url, Type typeOfT) {
        Thread mThread = null;
        ResponseResult<List<T>> responseResult = new ResponseResult<>();
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
            if (mThread != null)
                mThread.join();
            return responseResult.getResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends ResponseEntity> T getSync(String url, Class<T> classOfT) {
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
                            responseResult.setResult(gson.fromJson(result, classOfT));
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
