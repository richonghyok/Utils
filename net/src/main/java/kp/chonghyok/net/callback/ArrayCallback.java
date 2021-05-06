package kp.chonghyok.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class ArrayCallback<T> implements Callback {

    public abstract void onSuccess(List<T> responseBodyEntity);

    public abstract void onFailure(String exception);

    public void onProcess(Response response, Type typeOfT) {
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                try {
                    String result = body.string();
                    Gson gson = new Gson();
                    try {
                        List<T> resArray = gson.fromJson(result, typeOfT);
                        if (resArray != null) {
                            onSuccess(resArray);
                        } else {
                            onFailure("gson转换为空");
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        onFailure(e.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(e.getMessage());
                }
            } else {
                onFailure("body为空");
            }
        } else {
            onFailure("请求失败(非200)");
        }
    }
}