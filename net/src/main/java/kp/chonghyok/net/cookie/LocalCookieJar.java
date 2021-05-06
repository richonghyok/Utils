package kp.chonghyok.net.cookie;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class LocalCookieJar implements CookieJar {
    static LocalCookieJar cookieJar = new LocalCookieJar();
    private static Map<HttpUrl, List<Cookie>> cookiesMap = new HashMap<>();

    public static void clear() {
        cookiesMap = new HashMap<>();
    }

    public static LocalCookieJar getCookieJar() {
        return cookieJar;
    }

    @Override
    public synchronized void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
        Log.d("cookie", "saveFromResponse: " + cookiesMap);
        cookiesMap.put(url, cookies);
    }

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
        Log.d("cookie", "loadForRequest: " + cookiesMap);
        List<Cookie> cookiesList = cookiesMap.get(url);
        return cookiesList != null ? cookiesList : new ArrayList<>();
    }

    public List<Cookie> getCookies(String host, String name) {
        List<Cookie> cookieList = new ArrayList<>();
        cookiesMap.forEach((httpUrl, cookies) -> {
            if (httpUrl.host().equals(host)) {
                cookies.forEach(cookie -> {
                    if (cookie.name().equals(name)) {
                        cookieList.add(cookie);
                    }
                });
            }
        });
        return cookieList;
    }
}
