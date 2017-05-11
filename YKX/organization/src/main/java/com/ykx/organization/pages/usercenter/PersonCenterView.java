package com.ykx.organization.pages.usercenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.ui.MainActivity;
import com.hyphenate.chatuidemo.ui.VideoCallActivity;
import com.hyphenate.chatuidemo.ui.VoiceCallActivity;
import com.hyphenate.util.EasyUtils;
import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.HomeActivity;
import com.ykx.organization.pages.LoginActivity;
import com.ykx.organization.pages.home.operates.brandmanager.BrandDetailInfoActivity;
import com.ykx.organization.pages.home.operates.brandmanager.BrandManagerMainActivity;
import com.ykx.organization.pages.usercenter.setting.SettingMainActivity;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.AgenciesVO;
import com.ykx.organization.storage.vo.LoginReturnInfo;
import com.ykx.organization.storage.vo.UserInfoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ykx.organization.R.id.phone;

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
 * Created by 2017/3/16.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class PersonCenterView extends LinearLayout implements View.OnClickListener{


    private static final int sleepTime = 1;

    private HomeActivity context;


    private ImageView logoview;
    private TextView nameview,phoneview;

    private ImageView brandTagView;
    private ListView brandListView;
    private BrandAdapter brandAdapter;
    private TextView selecteBrandNameView;
    private ImageView selecteSMImageView,selectePPImageView;

    private View addBrandView;


    public static ImageView userHeaderImageView;

    public interface CallBackListener{
        void callBack();
    }

    private CallBackListener callBackListener;
    private View personContentView,bgView;

    public PersonCenterView(HomeActivity context,CallBackListener callBackListener) {
        super(context);
        this.callBackListener=callBackListener;
        initUI(context);
    }

    private void initUI(final HomeActivity context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.view_person_center,null);
        bgView=view.findViewById(R.id.close_persion_center_view);
        bgView.setOnClickListener(closePersonListener);
        personContentView=view.findViewById(R.id.perser_center_view);
        personContentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addBrandView=view.findViewById(R.id.add_brand_view);
        view.findViewById(R.id.add_buttom_brand_view).setOnClickListener(this);
        brandListView= (ListView) view.findViewById(R.id.brand_list_content_view);
        selecteBrandNameView= (TextView) view.findViewById(R.id.brand_selected_item_name);
        selecteSMImageView= (ImageView) view.findViewById(R.id.brand_selected_item_sm);
        selectePPImageView= (ImageView) view.findViewById(R.id.brand_selected_item_dj);
        brandTagView = (ImageView) view.findViewById(R.id.brand_selected_item_show_view);
        userHeaderImageView=brandTagView;
        addView(view,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        view.findViewById(R.id.pcv_setting).setOnClickListener(this);
        view.findViewById(R.id.authentication_view).setOnClickListener(this);
        view.findViewById(R.id.khfw_view).setOnClickListener(this);
        view.findViewById(R.id.user_info_ppzy_view).setOnClickListener(this);
        view.findViewById(R.id.brand_selected_content_view).setOnClickListener(this);

        logoview= (ImageView) findViewById(R.id.organization_imageview);
        nameview= (TextView) findViewById(R.id.organization_nameview);
        phoneview= (TextView) findViewById(R.id.organization_phoneview);

        logoview.setOnClickListener(this);

        brandAdapter=new BrandAdapter(context,null);
        brandListView.setAdapter(brandAdapter);
        loadBrandInfos();
        brandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final AgenciesVO brandVO = (AgenciesVO) parent.getItemAtPosition(position);

                String brandId = PreManager.getInstance().getData(PreManager.DEFAULT_BRANDID,"");
                if (String.valueOf(brandVO.getAgency_id()).equals(brandId)){
                    return;
                }
                context.showLoadingView();
                new UserServers().handleAgence(String.valueOf(brandVO.getAgency_id()), new HttpCallBack<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        PreManager.getInstance().saveData(PreManager.DEFAULT_BRANDID,String.valueOf(brandVO.getAgency_id()));
                        PreManager.getInstance().saveData(PreManager.DEFAULT_BRANDNAME,String.valueOf(brandVO.getBrand_name()));
                        loadBrandInfos();
                        context.refresh();
                        context.hindLoadingView();
                    }

                    @Override
                    public void onFail(String msg) {
                        context.toastMessage(context.getResources().getString(R.string.brandmanager_activity_base_info_selected_fail_toast));
                        context.hindLoadingView();
                    }
                });
            }
        });

    }

    private void resetSelectedAgenciesVO(){
        AgenciesVO agenciesVO = LoginReturnInfo.getSelectedAgenciesVO();
        if (agenciesVO!=null){
            selecteBrandNameView.setText(agenciesVO.getBrand_name());
            if (agenciesVO.isCert()){
                selecteSMImageView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_ysm));
            }else{
                selecteSMImageView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_wsm));
            }
            if (agenciesVO.isBranding()){
                selectePPImageView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_ydj));
            }else{
                selectePPImageView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_wdj));
            }

//            if (agenciesVO.isCert()){
//                selecteSMImageView.setVisibility(VISIBLE);
//            }else{
//                selecteSMImageView.setVisibility(INVISIBLE);
//            }
//            if (agenciesVO.isBranding()){
//                selectePPImageView.setVisibility(VISIBLE);
//            }else{
//                selectePPImageView.setVisibility(INVISIBLE);
//            }
        }
    }

    private void loadBrandInfos(){
        LoginReturnInfo loginReturnInfo =  MMCacheUtils.getLoginReturnInfo();
        if (loginReturnInfo!=null){
            brandAdapter.refresh(loginReturnInfo.getAgencies());
        }

        UserInfoVO userInfoVO = MMCacheUtils.getUserInfoVO();
        if (userInfoVO!=null){
            nameview.setText(userInfoVO.getName());
            phoneview.setText(userInfoVO.getPhone());
            BaseApplication.application.getDisplayImageOptions(userInfoVO.getAvatar(),logoview);
        }

        resetSelectedAgenciesVO();
    }


    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.organization_imageview){

        }else if (v.getId()==R.id.pcv_setting){
            context.startActivity(new Intent(context,SettingMainActivity.class));
        }else if(v.getId()==R.id.authentication_view){
            context.startActivity(new Intent(context, EditUserInfoActivity.class));
        }else if(v.getId()==R.id.khfw_view){
//            toHXAction();
            callKFFW("028-85003886");
        }else if(v.getId()==R.id.user_info_ppzy_view){
            context.startActivity(new Intent(context,BrandDetailInfoActivity.class));
        }else if(v.getId()==R.id.brand_selected_content_view){
            if (brandListView.getVisibility()==View.GONE){
                boolean isadd=RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_brand,RoleConstants.role_add);
                if (!isadd){
                    addBrandView.setVisibility(GONE);
                }else{
                    addBrandView.setVisibility(VISIBLE);
                }
                brandListView.setVisibility(VISIBLE);
                brandTagView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_brand_open));
            }else{
                brandListView.setVisibility(GONE);
                addBrandView.setVisibility(GONE);
                brandTagView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_brand_close));
            }
        }else if(v.getId()==R.id.add_buttom_brand_view){
            Intent intent=new Intent(context,BrandManagerMainActivity.class);
            context.startActivity(intent);
        }
    }


    private OnClickListener closePersonListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (callBackListener!=null){
                callBackListener.callBack();
            }
        }
    };

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void callKFFW(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void toHXAction(){
        new Thread(new Runnable() {
            public void run() {
                if (DemoHelper.getInstance().isLoggedIn()) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
                    if (topActivityName != null && (topActivityName.equals(VideoCallActivity.class.getName()) || topActivityName.equals(VoiceCallActivity.class.getName()))) {
                        // nop
                        // avoid main screen overlap Calling Activity
                    } else {
                        //enter main screen
                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                }else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((BaseActivity)context).finish();
                }
            }
        }).start();
    }

    public View getPersonContentView() {
        return personContentView;
    }

    public View getbgView() {
        return bgView;
    }


    class BrandAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<AgenciesVO> brandVOs;

        public BrandAdapter(Context context, List<AgenciesVO> brandVOs){
            layoutInflater=LayoutInflater.from(context);
            if (brandVOs==null){
                brandVOs=new ArrayList<>();
            }
            this.brandVOs=brandVOs;

        }

        public void refresh(List<AgenciesVO> brandVOs){
            if (brandVOs==null){
                brandVOs=new ArrayList<>();
            }
            this.brandVOs=brandVOs;
            brandListView.getLayoutParams().height= DensityUtil.dip2px(context,71)*brandVOs.size();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return brandVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return brandVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            TextView nameView;
            ImageView smView;
            ImageView pmView;
            ImageView selected;

            View lineView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.view_selected_brand_info_item,null);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.brand_list_item_name);
                viewHolder.smView= (ImageView) convertView.findViewById(R.id.brand_list_item_sm);
                viewHolder.pmView= (ImageView) convertView.findViewById(R.id.brand_list_item_dj);
                viewHolder.lineView=convertView.findViewById(R.id.line_view);
                viewHolder.selected= (ImageView) convertView.findViewById(R.id.brand_list_item_show_view);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            final AgenciesVO brandVO=brandVOs.get(position);

            viewHolder.nameView.setText(brandVO.getBrand_name());
            if (brandVO.isCert()){
                viewHolder.smView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_ysm));
            }else{
                viewHolder.smView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_wsm));
            }
            if (brandVO.isBranding()){
                viewHolder.pmView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_ydj));
            }else{
                viewHolder.pmView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_wdj));
            }
//            if (position==(brandVOs.size()-1)){
//                viewHolder.lineView.setVisibility(GONE);
//            }else{
//                viewHolder.lineView.setVisibility(VISIBLE);
//            }

            String brandid= PreManager.getInstance().getData(PreManager.DEFAULT_BRANDID,"");
            if (String.valueOf(brandVO.getAgency_id()).equals(brandid)){
                viewHolder.selected.setVisibility(View.VISIBLE);
            }else{
                viewHolder.selected.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }
    }

}
