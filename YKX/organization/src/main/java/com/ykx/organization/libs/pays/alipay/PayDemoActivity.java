package com.ykx.organization.libs.pays.alipay;

import java.util.Map;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.libs.pays.alipay.util.OrderInfoUtil2_0;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends BaseActivity {
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016080700187063";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "2088102170328132";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "2088102170328132";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEpQIBAAKCAQEAxyt6TaurVl1e8n0LvxePeii8iJWzjOCaEQSIFsqFPg6Jyt8hhzjmVqrEMlqjFrMmezriNzuFt0nZjQpOp8eP+zX4uGpzb4KE0Ylvr3+XiU+aOUQLF7VTL7hw6G4b3ZJV+4ti+1byNR6L1JZCa98KKi5v/KX8fJB1Aq6y1V5GdnNhkuaXWuguL+ecYKqXIhrGh0ThPxI7zOR2ExIUVOd7Dl2aM4TLWQNsd0vl5gt0eBM/OF0OHsOcGoKAwJXDzeG2kb1a6B+b+3YP65JyQNIrXrGHqw70A1ppZ7t0V0sLY6QS6LF2eGfc3ieFcewTR8pyrtfB6IAPUgi1N8cS7NSRFwIDAQABAoIBAQCizTVBu+tnwxbTW8d4QgfzsMy7ULLdmtIUywF6r7xOR4/2loFPiQEvIpMRHellqrbvz+gNgcgBVIpRsXUtzAAzNaDDDbCiG6v5oz/50m/Ju5my6Z2vmXYP8dHJDAgXC4DhBsS38hA1v2xJLzHH/x7N7/t2Jy/FmkcrFCX2JwOt2Z1k25ECSb0DGNsYhaYK0VXTzEnrFPgUnug4EES7hLStulFiitf18K2i8MCXc+266sbh8Yne/op37osoUqSFqMRQ8lse6ycBzzfzra2ChfKn2wv9coLKrt4Z/lCxAa/1XnLMxkTMT5pAmULwYdEESotYWdhFHi0ZzMbehjS58DyxAoGBAOOio8W0TDuqFCKjPqNqhB81t5XFGv8kdi2UNh6KP84UMWq7wNQP7T5jdfMm+RBTbUKehg6peTxLX87co0WeZxfZmWlD+3rSGhcU71s0PmG/cTclo9sEdbJAmQdM5LX+7cvM+WXeYS4boQc0BXvoTZsqaB6eA229lDG0xkJPo2CNAoGBAN/80KnrraRMqatzsN58zYBilT+DafrTxQ1xsjL/2u6d3sXwOm4LWeNR4bm0qUCHDQctgKfQAlnQcxtzKLpqeztXQvBYRY2C5zoreNSL5YJGjuhGMUhbfSyhcOGDPAlged99FivAgzmSxLcaAiYROQD7wcNj6t4Kk/R56KW4COkzAoGAAnA1jOKXhnQV7yEMhLeZXrQ2mTYZSe5cDbTut3kHoe95cAk+0YayJd7u3oHmXRRnsTT9fp62G706835NiWte81RzXjtKMFCb0u94eOOgX2+riMmwbaIomJY0jW13bbOjXHF/omW1aMcG/IrEHEWqvyS/JMTQiJZlRfsHvKIZv+kCgYEAgzI/Org8DjeFTlLGb5tkd2XHC9u6kNvH2RcWjL7GiuvXl9MdNmXZsce9UpwaYxx3qhOh/ylcpyTst1Bds8s5AUocgFED9F6Pd8gMz1peBU+01yJ+LWbBTAoH51+KI+fpmtlWyivJ8Ughhyppxchivku4c+0rngFOJm3dsmu4dHcCgYEAyKqxm4lk+9A63bmCjG3yevZ8e6DrT+vCTG40Nvqo2XqZ8Lhx1y4goFr/iEDm8QOFPp9zQ1Y91TWElUlSxBK85aHq9cexZO+fWb9neUFStV6OOTiF4mZdPfjhGqo0WuxQgzjw5C46diekEU5J13zftgk9BHSRFoCFgJumNFBMOoI=";
	public static final String RSA_PRIVATE = "MIIEpQIBAAKCAQEAxyt6TaurVl1e8n0LvxePeii8iJWzjOCaEQSIFsqFPg6Jyt8hhzjmVqrEMlqjFrMmezriNzuFt0nZjQpOp8eP+zX4uGpzb4KE0Ylvr3+XiU+aOUQLF7VTL7hw6G4b3ZJV+4ti+1byNR6L1JZCa98KKi5v/KX8fJB1Aq6y1V5GdnNhkuaXWuguL+ecYKqXIhrGh0ThPxI7zOR2ExIUVOd7Dl2aM4TLWQNsd0vl5gt0eBM/OF0OHsOcGoKAwJXDzeG2kb1a6B+b+3YP65JyQNIrXrGHqw70A1ppZ7t0V0sLY6QS6LF2eGfc3ieFcewTR8pyrtfB6IAPUgi1N8cS7NSRFwIDAQABAoIBAQCizTVBu+tnwxbTW8d4QgfzsMy7ULLdmtIUywF6r7xOR4/2loFPiQEvIpMRHellqrbvz+gNgcgBVIpRsXUtzAAzNaDDDbCiG6v5oz/50m/Ju5my6Z2vmXYP8dHJDAgXC4DhBsS38hA1v2xJLzHH/x7N7/t2Jy/FmkcrFCX2JwOt2Z1k25ECSb0DGNsYhaYK0VXTzEnrFPgUnug4EES7hLStulFiitf18K2i8MCXc+266sbh8Yne/op37osoUqSFqMRQ8lse6ycBzzfzra2ChfKn2wv9coLKrt4Z/lCxAa/1XnLMxkTMT5pAmULwYdEESotYWdhFHi0ZzMbehjS58DyxAoGBAOOio8W0TDuqFCKjPqNqhB81t5XFGv8kdi2UNh6KP84UMWq7wNQP7T5jdfMm+RBTbUKehg6peTxLX87co0WeZxfZmWlD+3rSGhcU71s0PmG/cTclo9sEdbJAmQdM5LX+7cvM+WXeYS4boQc0BXvoTZsqaB6eA229lDG0xkJPo2CNAoGBAN/80KnrraRMqatzsN58zYBilT+DafrTxQ1xsjL/2u6d3sXwOm4LWeNR4bm0qUCHDQctgKfQAlnQcxtzKLpqeztXQvBYRY2C5zoreNSL5YJGjuhGMUhbfSyhcOGDPAlged99FivAgzmSxLcaAiYROQD7wcNj6t4Kk/R56KW4COkzAoGAAnA1jOKXhnQV7yEMhLeZXrQ2mTYZSe5cDbTut3kHoe95cAk+0YayJd7u3oHmXRRnsTT9fp62G706835NiWte81RzXjtKMFCb0u94eOOgX2+riMmwbaIomJY0jW13bbOjXHF/omW1aMcG/IrEHEWqvyS/JMTQiJZlRfsHvKIZv+kCgYEAgzI/Org8DjeFTlLGb5tkd2XHC9u6kNvH2RcWjL7GiuvXl9MdNmXZsce9UpwaYxx3qhOh/ylcpyTst1Bds8s5AUocgFED9F6Pd8gMz1peBU+01yJ+LWbBTAoH51+KI+fpmtlWyivJ8Ughhyppxchivku4c+0rngFOJm3dsmu4dHcCgYEAyKqxm4lk+9A63bmCjG3yevZ8e6DrT+vCTG40Nvqo2XqZ8Lhx1y4goFr/iEDm8QOFPp9zQ1Y91TWElUlSxBK85aHq9cexZO+fWb9neUFStV6OOTiF4mZdPfjhGqo0WuxQgzjw5C46diekEU5J13zftgk9BHSRFoCFgJumNFBMOoI=";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
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
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(PayDemoActivity.this,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(PayDemoActivity.this,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_ali_main);
	}
	
	/**
	 * 支付宝支付业务
	 * 
	 * @param v
	 */
	public void payV2(View v) {
//		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialoginterface, int i) {
//							//
//							finish();
//						}
//					}).show();
//			return;
//		}
	
		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo的获取必须来自服务端；
		 */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//		final String orderInfo = orderParam + "&" + sign;
//		toastMessage("orderInfo="+orderInfo);
//		Log.e("xx","orderInfo="+orderInfo);

		final String orderInfo="app_id=2016080700187063&biz_content=%7B%22body%22%3A%22100U%E5%B8%81+19%E5%85%83%22%2C%22subject%22%3A%22%E4%BC%98%E8%AF%BE%E5%AD%A6-U%E5%B8%81%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22U2017051115020000%22%2C%22total_amount%22%3A%220.01%22%2C%22seller_id%22%3A%222088102170328132%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22buy%2Bsome%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fhelei112g.github.io%2F&return_url=https%3A%2F%2Fhelei112g.github.io%2F&sign_type=RSA2&timestamp=2017-05-11+15%3A02%3A15&version=1.0&sign=JRK0iUidWsf9oxQxTHfclbboUG%2BJb%2BgDhAU0%2Fox%2BZBoZFWgA%2Fn%2BnGwYlgZcfGZ%2FL0T4VLgAqOj07jsMFgc1XI%2BIhr8qJaO2QlDr0a42wCZLiy0VYRAB9HaepH7XQQsoeURjsRkrKCy051reyIohZ3%2FMpnfrqdEnoJbd1e%2FDNu81QHZm297yfD0yEMQ%2Bg%2Fh8fCXVyqu2cDQ2AWHjJcbaKRHsn6RO9%2FZG0ZDf4xm4e63BANpq2%2B5rcq8V5w%2BjuKEaKXwj%2BGjblRlZLv5yTZuSew6uFQct0P4DyLVay4lWJY%2BCQUWuKP%2FQaYCYEx%2BPlN2ryVadV7%2FFfzyoYP4RLYEDZzA%3D%3D";

//		final String orderInfo11="app_id=2016080700187063&biz_content=%7B%22body%22%3A%22100U%E5%B8%81+19%E5%85%83%22%2C%22subject%22%3A%22%E4%BC%98%E8%AF%BE%E5%AD%A6-U%E5%B8%81%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22U2017051113590000%22%2C%22total_amount%22%3A%220.01%22%2C%22seller_id%22%3A%222088102170328132%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22buy%2Bsome%22%2C%22it_b_pay%22%3A%22-87342m%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fhelei112g.github.io%2F&return_url=https%3A%2F%2Fhelei112g.github.io%2F&sign_type=RSA2&timestamp=2017-05-11+13%3A59%3A19&version=1.0&sign=FVrjlJEqdjENH3fYPFXvdsooeUfItV4L9h4RF8phblTR7FbQdyhXO%2F0791qLEzy5NY89J07lmg1r8BUvrjB4V61fq5DlwLuftPFU2D6kNjodv4khuaFV99rOhc0d8WU7JQ4R53NTExY%2FMOnbig8dlmg%2BNYdGqIP%2BOzlANsBUexhu8l0KxiPbV%2BtGjkTw%2F1cO1Xa6J1qdl17G2Wceec4xjy4GsJUmEaF5Iby7KSuJNXFWx6YQzVF%2B9yEJX4XeMv4yE7wNsvx%2F5ch2LqOq0jVkoX7GcpOcfQDHbFKdQC5ooSIT4WApuFAokV%2FUonLHgm4tbyTWHOaKqG7OkEW%2FPJMGIQ%3D%3D";

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
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

	/**
	 * 支付宝账户授权业务
	 * 
	 * @param v
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
				|| TextUtils.isEmpty(TARGET_ID)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * authInfo的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		
		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;

		toastMessage("authInfo="+authInfo);
		Log.e("xx","authInfo="+authInfo);
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 */
		String url = "http://m.taobao.com";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

}
