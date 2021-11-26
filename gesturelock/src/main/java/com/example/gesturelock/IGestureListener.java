package com.example.gesturelock;

/**
 * @author nmynmy
 * @title: IGestureListener
 * @projectName NGestureLock
 * @description: TODO
 * @date 2021-11-14 22:39
 */
public interface IGestureListener {

    /**
     * @param key 返回正确密码
     */
    void isSuccessful(String key);

    /**
     * @param key 返回错误密码
     */
    void isError(String key);

    /**
     * @param key 返回设置密码
     */
    void isSetUp(String key);
}
