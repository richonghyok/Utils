package kp.chonghyok.net.response;


import com.google.gson.Gson;

public class ResponseEntity {
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
