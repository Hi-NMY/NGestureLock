package com.example.gesturelock;

public interface IGestureListener {

    void isSuccessful(String key);

    void isError(String key);

    void isSetUp(String key);
}
