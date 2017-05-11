package com.ykx.organization.libs.pays.wx;


import org.json.JSONObject;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ykx.organization.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PayActivity extends Activity {
	
	private IWXAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_wx);
		
		api = WXAPIFactory.createWXAPI(this, "wxd930ea5d5a258f4f");

		Button appayBtn = (Button) findViewById(R.id.appay_btn);
		appayBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				PayReq req = new PayReq();
				//req.appId = "wxf8b4f85f3a794e77";
				req.appId			= "wxd930ea5d5a258f4f";
				req.partnerId		= "1900000109";
				req.prepayId		= "1101000000140415649af9fc314aa427";
				req.packageValue    = "Sign=WXPay";
				req.nonceStr		= "1101000000140429eb40476f8896f4c9";
				req.timeStamp		= "1398746574";
				req.sign			= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
				req.extData			= "app data"; // optional
				boolean isflag = api.sendReq(req);
				Toast.makeText(PayActivity.this,"isflag="+isflag,Toast.LENGTH_LONG).show();
				Log.d("xx","isflag="+isflag);

//				String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
//				Button payBtn = (Button) findViewById(R.id.appay_btn);
//				payBtn.setEnabled(false);
//		        try{
//					byte[] buf = Util.httpGet(url);
//					if (buf != null && buf.length > 0) {
//						String content = new String(buf);
//						Log.e("get server pay params:",content);
//			        	JSONObject json = new JSONObject(content);
//						if(null != json && !json.has("retcode") ){
//							PayReq req = new PayReq();
//							//req.appId = "wxf8b4f85f3a794e77";
//							req.appId			= json.getString("appid");
//							req.partnerId		= json.getString("partnerid");
//							req.prepayId		= json.getString("prepayid");
//							req.nonceStr		= json.getString("noncestr");
//							req.timeStamp		= json.getString("timestamp");
//							req.packageValue	= json.getString("package");
//							req.sign			= json.getString("sign");
//							req.extData			= "app data"; // optionali
//							Toast.makeText(PayActivity.this, "content="+content, Toast.LENGTH_SHORT).show();
//							api.sendReq(req);
//						}else{
//				        	Toast.makeText(PayActivity.this, "json="+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//						}
//					}else{
//			        	Toast.makeText(PayActivity.this, "buf is null", Toast.LENGTH_SHORT).show();
//			        }
//		        }catch(Exception e){
//		        	Log.e("PAY_GET", "Exception="+e.getMessage());
//		        	Toast.makeText(PayActivity.this, "Exception="+e.getMessage(), Toast.LENGTH_SHORT).show();
//		        }
//		        payBtn.setEnabled(true);
			}
		});		
		Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
		checkPayBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
				Toast.makeText(PayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}
