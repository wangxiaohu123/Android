package com.ykx.organization.pages.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DateUtil;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.authentication.AuthenticationActivity;
import com.ykx.organization.pages.bases.RoleFragment;
import com.ykx.organization.pages.home.operates.brandmanager.BrandManagerMainActivity;
import com.ykx.organization.servers.WorkbenchServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.RoleModule;
import com.ykx.organization.storage.vo.WorkbenchTJInfo;

import java.util.ArrayList;
import java.util.Date;
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

public class WorkbenchFragment extends RoleFragment {

    private BaseActivity context;

    private View certification,summary,teaching,news;

    private ListView messageListView;
    private MessageAdapter messageAdapter;

    private ImageView ysmView,yrzView;
    private TextView bkView,lsView,xqView;

    private TextView jrfkAllView,jrfkpView,jrfkflagView;
    private TextView jrzxAllView,jrzxpView,jrzxflagView;
    private TextView jrddAllView,jrddpView,jrddflagView;
    private TextView jrjeAllView,jrjepView,jrjeflagView;

    private WorkbenchTJInfo workbenchTJInfo;



    @Override
    protected int getContentViewResource() {

        return R.layout.fragment_home_workbench;
    }


    @Override
    protected void initUI() {
        context=(BaseActivity) getActivity();
//        LinearLayout contentview=find(R.id.content_view,null);
//        ExceptionVIew exceptionVIew = ExceptionVIew.loadView(activity(),contentview,R.drawable.kfz,getString(R.string.sys_exception_kfz_message),null);
//        exceptionVIew.setbgCorlor(Color.WHITE);

        find(R.id.smdj_content_view, djlistener);
        find(R.id.ppdj_content_view, djlistener);

        summary=find(R.id.item_jygk_infor_view,null);
        certification=find(R.id.item_dj_infor_view,null);
        teaching=find(R.id.item_xqbkls_infor_view,null);
        news=find(R.id.item_message_infor_view,null);
//        find(R.id.item_jygk_infor_view,null);

        messageListView=find(R.id.message_list_view,null);
        messageAdapter=new MessageAdapter(getActivity(),null);
        messageListView.setAdapter(messageAdapter);
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        messageListView.setFocusable(false);

        yrzView=find(R.id.yrz_pic_flag_view,null);
        ysmView=find(R.id.ysm_pic_flag_view,null);
        bkView=find(R.id.bks_view,null);
        lsView=find(R.id.jss_view,null);
        xqView=find(R.id.xqs_view,null);

        jrfkAllView=find(R.id.jrfk_all_view,null);
        jrfkpView=find(R.id.jrfk_p_view,null);
        jrfkflagView=find(R.id.jrfk_flag_view,null);
        jrzxAllView=find(R.id.jrzx_all_view,null);
        jrzxpView=find(R.id.jrzx_p_view,null);
        jrzxflagView=find(R.id.jrzx_flag_view,null);
        jrddAllView=find(R.id.jrdd_all_view,null);
        jrddpView=find(R.id.jrdd_p_view,null);
        jrddflagView=find(R.id.jrdd_flag_view,null);
        jrjeAllView=find(R.id.jrje_all_view,null);
        jrjepView=find(R.id.jrje_p_view,null);
        jrjeflagView=find(R.id.jrje_flag_view,null);

        TextView titletime=find(R.id.time_title,null);
        String message=String.format(getResources().getString(R.string.workbench_fragment_jykg_title),String.valueOf(DateUtil.format(new Date(),DateUtil.DATE_FORMAT_TO_DAY)));
        titletime.setText(message);

        loadData();
    }

    private View.OnClickListener djlistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            boolean isview=RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.panel,RoleConstants.panel_certification,RoleConstants.role_view);
//            if (isview) {
                if (v.getId() == R.id.smdj_content_view) {
                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                    getActivity().startActivity(intent);
                } else if (v.getId() == R.id.ppdj_content_view) {

                    Intent intent = new Intent(getActivity(), BrandManagerMainActivity.class);
                    getActivity().startActivity(intent);
                }
//            }else{
//                showToast(R.string.sys_role_fail_toast);
//            }
        }
    };

    private void loadData(){

        contentView.setVisibility(View.INVISIBLE);
        final Handler handler=new Handler();
        new WorkbenchServers().getWorkbenchMainDatas(new HttpCallBack<WorkbenchTJInfo>() {
            @Override
            public void onSuccess(final WorkbenchTJInfo data) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        workbenchTJInfo = data;
                        resetView();
                        resetRoleView();
                        contentView.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    private void resetView(){
        if (workbenchTJInfo!=null){

            WorkbenchTJInfo.Certification certification = workbenchTJInfo.getCertification();
            if (certification!=null){
                if (!certification.isCert()){
                    ysmView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_wsm));
                }else{
                    ysmView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_ysm));
                }

                if (!certification.isBranding()){
                    yrzView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_wdj));
                }else{
                    yrzView.setImageDrawable(BitmapUtils.getDrawable(context,R.mipmap.bq_ydj));

                }
            }

            WorkbenchTJInfo.Summary summary = workbenchTJInfo.getSummary();
            if (summary!=null){
                jrfkAllView.setText(String.valueOf(summary.getTodayHit()));
                jrfkpView.setText(summary.getDiffHit()+"%");
                jrfkflagView.setText(getFlag(summary.getDiffHit(),jrfkpView,jrfkflagView));

                jrzxAllView.setText(String.valueOf(summary.getTodayCounsel()));
                jrzxpView.setText(summary.getDiffCounsel()+"%");
                jrzxflagView.setText(getFlag(summary.getDiffCounsel(),jrzxpView,jrzxflagView));

                jrddAllView.setText(summary.getTodayOrder()+"单");
                jrddpView.setText(summary.getDiffOrder()+"%");
                jrddflagView.setText(getFlag(summary.getDiffOrder(),jrddpView,jrddflagView));

                jrjeAllView.setText("¥"+summary.getTodayAmount());
                jrjepView.setText(summary.getDiffAmount()+"%");
                jrjeflagView.setText(getFlag(summary.getDiffAmount(),jrjepView,jrjeflagView));

            }

            WorkbenchTJInfo.Teaching teaching = workbenchTJInfo.getTeaching();
            if (teaching!=null){
                bkView.setText(String.valueOf(teaching.getCourse_count()));
                lsView.setText(String.valueOf(teaching.getTeacher_count()));
                xqView.setText(String.valueOf(teaching.getCampus_count()));

            }
            List<WorkbenchTJInfo.News> news = workbenchTJInfo.getNews();
            if (news!=null){
                messageAdapter.refresh(news);
            }

        }
    }

    private String getFlag(int ndiff,TextView pview,TextView selfView){
        if (ndiff>0){
            selfView.setTextColor(Color.RED);
            pview.setTextColor(Color.RED);
            return "↑";
        }
        selfView.setTextColor(BaseApplication.application.getResources().getColor(R.color.theme_main_background_color));
        pview.setTextColor(BaseApplication.application.getResources().getColor(R.color.theme_main_background_color));
        return "↓";
    }


    @Override
    protected void onViewDidLoad() {

    }

    @Override
    public void onClick(View v) {

    }


    class MessageAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<WorkbenchTJInfo.News> datas;

        public MessageAdapter(Context context, List<WorkbenchTJInfo.News> datas){
            layoutInflater=LayoutInflater.from(context);
            if (datas==null){
                datas=new ArrayList<>();
            }
            this.datas=datas;

        }

        public void refresh(List<WorkbenchTJInfo.News> datas){
            if (datas==null){
                datas=new ArrayList<>();
            }
            this.datas=datas;

            messageListView.getLayoutParams().height= DensityUtil.dip2px(BaseApplication.application,51)*this.datas.size();


            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            TextView messageview;
            View lineView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.activity_list_item,null);
                viewHolder.messageview= (TextView) convertView.findViewById(R.id.message_name_item_view);
                viewHolder.lineView=convertView.findViewById(R.id.line_view);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            WorkbenchTJInfo.News nnew=datas.get(position);
            viewHolder.messageview.setText(nnew.getTitle());
            if ((datas.size()-1)==position){
                viewHolder.lineView.setVisibility(View.GONE);
            }

            return convertView;
        }
    }

    /**
     * 权限处理
     */

    @Override
    public void refreshWithRole(final List<RoleModule> roleModel) {
        super.refreshWithRole(roleModel);
        if (createViewFlag) {
            loadData();
        }

    }

    private void resetRoleView(){

        if (roleModel==null){
            roleModel= MMCacheUtils.getUserInfoVO().getPower();
        }

        boolean isEable1 = RoleConstants.isEnable(roleModel,RoleConstants.panel,RoleConstants.panel_certification,null);
        certification.setVisibility(isEable1?View.VISIBLE:View.GONE);

        boolean isEable2 = RoleConstants.isEnable(roleModel,RoleConstants.panel,RoleConstants.panel_news,null);
        news.setVisibility(isEable2?View.VISIBLE:View.GONE);

        boolean isEable3 = RoleConstants.isEnable(roleModel,RoleConstants.panel,RoleConstants.panel_summary,null);
        summary.setVisibility(isEable3?View.VISIBLE:View.GONE);

        boolean isEable4 = RoleConstants.isEnable(roleModel,RoleConstants.panel,RoleConstants.panel_teaching,null);
        teaching.setVisibility(isEable4?View.VISIBLE:View.GONE);
    }

}