package com.ykx.baselibs.https;


/**
 * <p>
 * Description：Http返回类
 * </p>
 *
 * @author wangxiaohu
 */
public class HttpResult {

    private Object data;
    private int ret;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
