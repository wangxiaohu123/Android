package com.ykx.organization.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.widget.ImageView;

//import com.easemob.redpacketsdk.RPInitRedPacketCallback;
//import com.easemob.redpacketsdk.RPValueCallback;
//import com.easemob.redpacketsdk.RedPacket;
//import com.easemob.redpacketsdk.bean.RedPacketInfo;
//import com.easemob.redpacketsdk.bean.TokenData;
//import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoApplication;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.commons.Constant;
import com.ykx.organization.R;
import com.ykx.organization.libs.utils.autoimagedownload.AuthImageDownloader;
import com.ykx.organization.pages.HomeActivity;
import com.zxy.tiny.Tiny;

import java.io.File;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by wangxiaohu on 2017/3/7.
 */

public class OrganizationApp extends BaseApplication {

//    public static final String TAG =OrganizationApp.class.getName() ;
//
//    private Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        DemoApplication.createInstances(this,this);
        MultiDex.install(this);

        loadImageLoadConfig();
        ShareSDK.initSDK(this);

        hxInit();
        umengInit();
        Tiny.getInstance().init(this);
    }

    private void loadImageLoadConfig() {

        File cacheDir = new File(Constant.PIC_CACHE_PATH);// StorageUtils.getOwnCacheDirectory(this, AppConstant.PIC_PATH);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(5) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 由原先的discCache -> diskCache
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径UnlimitedDiscCache(cacheDir)
                .imageDownloader(new AuthImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    public void getDisplayImageOptions(String imageUrl, ImageView mImageView){
        if ((mImageView==null)||(imageUrl==null)){
            return;
        }
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.svg_home_user_center_mrtx)
                .showImageForEmptyUri(R.drawable.svg_image_loading_fail_new)
                .showImageOnFail(R.drawable.svg_image_loading_fail_new)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(DensityUtil.dip2px(this,3)))
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
//                .displayer(new FadeInBitmapDisplayer(388))
                .build();
        String[] urlss=imageUrl.split("e=");
        String key=urlss[0];
//        Log.e("xx","key="+key);
        ImageLoader.getInstance().displayImage(key,imageUrl, mImageView, options);
//        ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);
//        ImageLoader.getInstance().displayImage(imageUrl, mImageView);
    }

    public static void showShare(String title,String content,String url,String photoUrl) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);

        if ((photoUrl!=null)&&(photoUrl.length()>0)) {
//        oks.setImageArray(photoUrls);
            oks.setImagePath(photoUrl);
        }
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl(photoUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(content);
//        oks.setPlatform("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(title);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(BaseApplication.application);
    }

    private void hxInit(){
//        int pid = android.os.Process.myPid();
//        String processAppName = getAppName(pid);
//        // 如果APP启用了远程的service，此application:onCreate会被调用2次
//        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
//        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
//        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
//            Log.e(TAG, "enter the service process!");
//            // 则此application::onCreate 是被service 调用的，直接返回
//            return;
//        }
//
//
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        options.setAutoLogin(false);
//        //初始化
//        EMClient.getInstance().init(this, options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
//        //注册一个监听连接状态的listener
//        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
//
//        EaseUI.getInstance().init(this, options);


        DemoHelper.exceptionActivity= HomeActivity.class;
        //init demo helper
        DemoHelper.getInstance().init(this);
        //red packet code : 初始化红包SDK，开启日志输出开关
//        RedPacket.getInstance().initRedPacket(this, RPConstant.AUTH_METHOD_EASEMOB, new RPInitRedPacketCallback() {
//
//            @Override
//            public void initTokenData(RPValueCallback<TokenData> callback) {
//                TokenData tokenData = new TokenData();
//                tokenData.imUserId = EMClient.getInstance().getCurrentUser();
//                //此处使用环信id代替了appUserId 开发者可传入App的appUserId
//                tokenData.appUserId = EMClient.getInstance().getCurrentUser();
//                tokenData.imToken = EMClient.getInstance().getAccessToken();
//                //同步或异步获取TokenData 获取成功后回调onSuccess()方法
//                callback.onSuccess(tokenData);
//            }
//
//            @Override
//            public RedPacketInfo initCurrentUserSync() {
//                //这里需要同步设置当前用户id、昵称和头像url
//                String fromAvatarUrl = "";
//                String fromNickname = EMClient.getInstance().getCurrentUser();
//                EaseUser easeUser = EaseUserUtils.getUserInfo(fromNickname);
//                if (easeUser != null) {
//                    fromAvatarUrl = TextUtils.isEmpty(easeUser.getAvatar()) ? "none" : easeUser.getAvatar();
//                    fromNickname = TextUtils.isEmpty(easeUser.getNick()) ? easeUser.getUsername() : easeUser.getNick();
//                }
//                RedPacketInfo redPacketInfo = new RedPacketInfo();
//                redPacketInfo.fromUserId = EMClient.getInstance().getCurrentUser();
//                redPacketInfo.fromAvatarUrl = fromAvatarUrl;
//                redPacketInfo.fromNickName = fromNickname;
//                return redPacketInfo;
//            }
//        });
//        RedPacket.getInstance().setDebugMode(true);
        //end of red packet code
    }

//    //实现ConnectionListener接口
//    private class MyConnectionListener implements EMConnectionListener {
//        @Override
//        public void onConnected() {
//        }
//        @Override
//        public void onDisconnected(final int error) {
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e(TAG, "EMConnectionListener error="+error);
//                    if(error == EMError.USER_REMOVED){
//                        // 显示帐号已经被移除
//                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                        // 显示帐号在其他设备登录
//                    } else {
//                        if (NetUtils.hasNetwork(OrganizationApp.this));
//                            //连接不到聊天服务器
//                        else{//当前网络不可用，请检查网络设置
//
//                        }
//                    }
//                }
//            },1000);
//        }
//    }
//
//    private String getAppName(int pID) {
//        String processName = null;
//        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//        List l = am.getRunningAppProcesses();
//        Iterator i = l.iterator();
////        PackageManager pm = this.getPackageManager();
//        while (i.hasNext()) {
//            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
//            try {
//                if (info.pid == pID) {
//                    processName = info.processName;
//                    return processName;
//                }
//            } catch (Exception e) {
//                // Log.d("Process", "Error>> :"+ e.toString());
//            }
//        }
//        return processName;
//    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void umengInit(){
    }

}
