package com.ykx.baselibs.https;

/**
 * <p>
 * Description：请求回调
 * </p>
 *
 * @author wangxiaohu
 */
public abstract class HttpCallBack<T> {

    public abstract void onSuccess(T data);

    public abstract void onFail(String msg);
}
