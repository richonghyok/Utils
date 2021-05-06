package kp.chonghyok.net.callback;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class ObjCallback<T> implements Callback {
    public abstract void onSuccess(T responseBodyEntity);

    public abstract void onFailure();

    public void onProcess(Response response, Class<T> cls) {
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                try {
                    String result = body.string();
                    Gson gson = new Gson();
                    T obj = gson.fromJson(result, cls);
                    if (obj != null) {
                        onSuccess(obj);
                    } else {
                        onFailure();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure();
                }
            } else {
                onFailure();
            }
        } else {
            onFailure();
        }
    }
}