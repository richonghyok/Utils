package kp.chonghyok.network.callback;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class ArrayCallback<T> implements Callback {
    public abstract void onSuccess(List<T> responseBodyEntity);

    public abstract void onFailure();

    public void onProcess(Response response, Type typeOfT) {
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                try {
                    String result = body.string();
                    Gson gson = new Gson();
                    List<T> resArray = gson.fromJson(result, typeOfT);
                    if (resArray != null) {
                        onSuccess(resArray);
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