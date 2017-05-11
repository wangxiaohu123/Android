package com.ykx.organization.libs.pays.alipay;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ykx.baselibs.pages.BaseActivity;

import java.util.Map;

/*********************************************************************************
 * Project Name  : YKX
 * Package       : com.ykx.apps
 * <p>
 * <p>
 * Copyright  优课学技术部  Corporation 2017 All Rights Reserved
 * <p>
 * <p>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/5/11.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class AlipayManager {


    private static final int SDK_PAY_FLAG = 1;

    private BaseActivity baseActivity;

    public AlipayManager(BaseActivity baseActivity){
        this.baseActivity=baseActivity;
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(baseActivity, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(baseActivity, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 支付宝支付业务
     */
    public void payV2() {
        final String orderInfo="app_id=2017051007190547&biz_content=%7B%22body%22%3A%22100U%E5%B8%81+19%E5%85%83%22%2C%22subject%22%3A%22%E4%BC%98%E8%AF%BE%E5%AD%A6-U%E5%B8%81%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22U2017051117300000%22%2C%22total_amount%22%3A%220.01%22%2C%22seller_id%22%3A%222088102170328132%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22buy%2Bsome%22%2C%22it_b_pay%22%3A%2210m%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwx.ykx100.com%2Falipay%2Fnotify&return_url=http%3A%2F%2Fwx.ykx100.com%2Falipay%2Fnotify&sign_type=RSA2&timestamp=2017-05-11+17%3A30%3A52&version=1.0&sign=O%2BIAozyQVFkTeYisp95EwxmLQfTh7SpDbhoclKCUf5lPXtZ8vKJz5kReI4Cesyy%2FGshQaIx19iP94RacktNoU59yPz4Nl1AIF3e7MBfktMpSOXlQnzl9MKNZ6Q77YYaq8AnQlF%2Bm9PNQvu2Crr5Guu2WGG3Hf7OwsK%2BUU00pvm5iJUaDik9qAWOHqSQ33xUaAGNXbaruB2kGnx8pBgqBagWbaSFqc6SjUM7QxgSnPvf3LEHgp0CqDn7W08OcHafvRdX6wKmQy8ex0VyK7b2T9nDhBev5%2BGc0geGAyas25pUvEi2eKV4nIltXcrZPabLgBSG8vS6qeC17PMn%2BVrsLZw%3D%3D";
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(baseActivity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
