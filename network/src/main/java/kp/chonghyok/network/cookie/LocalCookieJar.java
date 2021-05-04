package kp.chonghyok.network.cookie;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class LocalCookieJar implements CookieJar {

    public static void clear(){
        cookiesMap = new HashMap<>();
    }

    private static Map<String, List<Cookie>> cookiesMap = new HashMap<>();

    @Override
    public synchronized void saveFromResponse(HttpUrl url, @NotNull List<Cookie> cookies) {
        String host = url.host();
        cookiesMap.put(host, cookies);
    }

    @NotNull
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookiesList = cookiesMap.get(url.host());
        return cookiesList != null ? cookiesList : new ArrayList<>();
    }
}
