package com.ykx.organization.pages.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

//import com.easemob.redpacket.utils.RedPacketUtil;
//import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.ui.ConversationListFragment;
import com.hyphenate.chatuidemo.ui.GroupsActivity;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.pages.bases.RoleFragment;
import com.ykx.organization.pages.home.infomessage.MyChatActivity;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.RoleModule;

import java.util.List;

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
 * Created by 2017/3/15.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class InfoMessageFragment extends RoleFragment {

    private BaseActivity baseActivity;

    private ConversationListFragment conversationListFragment;

    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_home_infomessage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initUI() {

//        LinearLayout contentview=find(R.id.content_view,null);
//        ExceptionVIew exceptionVIew = ExceptionVIew.loadView(activity(),contentview,R.drawable.kfz,getString(R.string.sys_exception_kfz_message),null);
//        exceptionVIew.setbgCorlor(Color.WHITE);


        refreshWithRole(MMCacheUtils.getUserInfoVO().getPower());
    }


    @Override
    public void refreshWithRole(List<RoleModule> roleModel) {
        super.refreshWithRole(roleModel);

        Activity activity=getActivity();
        if (activity instanceof BaseActivity){
            EaseBaseFragment.isShowTitle=false;
            baseActivity=(BaseActivity) getActivity();
            conversationListFragment = new ConversationListFragment();
            conversationListFragment.setChatActivity(MyChatActivity.class);
            baseActivity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_view, conversationListFragment).commit();

            registerBroadcastReceiver();
//            refreshUIWithMessage();
        }

    }

    @Override
    protected void onViewDidLoad() {

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        EaseBaseFragment.isShowTitle=false;
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(baseActivity);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(baseActivity);
        super.onStop();
    }


    private void refreshUIWithMessage() {
        baseActivity.runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                // refresh conversation list
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
            }
        });
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(baseActivity);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
//        intentFilter.addAction(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // refresh conversation list
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
                String action = intent.getAction();
                if(action.equals(Constant.ACTION_GROUP_CHANAGED)){
                    if (EaseCommonUtils.getTopActivity(baseActivity).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
                //red packet code : 处理红包回执透传消息
//                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                    if (conversationListFragment != null){
//                        conversationListFragment.refresh();
//                    }
//                }
                //end of red packet code
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
//            for (EMMessage message : messages) {
//                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
//                final String action = cmdMsgBody.action();//获取自定义action
//                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//                    RedPacketUtil.receiveRedPacketAckMessage(message);
//                }
//            }
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };


    private void unregisterBroadcastReceiver(){
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

}
