package com.ykx.organization.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.pages.ExceptionVIew;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.home.operates.curriculum.AddCurriculumActivity;
import com.ykx.organization.pages.home.operates.curriculum.CurriculumTimeView;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.CampusVO;
import com.ykx.organization.storage.vo.CurriculumVO;
import com.ykx.organization.storage.vo.TeacherVO;
import com.ykx.organization.storage.vo.TimeVO;

import java.util.ArrayList;
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
 * Created by 2017/3/23.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class CurriculumAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<CurriculumVO> curriculumVOs;

    private CampusVO campusVOInfo;


    private LinearLayout contentview;
    private ExceptionVIew exceptionVIew;

    private boolean editEnable=true;
    private boolean dropEnable=true;

    public CurriculumAdapter(Context context,List<CurriculumVO> campusVOs,CampusVO campusVO){
        this.layoutInflater=LayoutInflater.from(context);
        if (campusVOs==null){
            campusVOs=new ArrayList<>();
        }
        this.curriculumVOs=campusVOs;
        this.context=context;
        this.campusVOInfo=campusVO;
        getrole();
    }

    private void getrole(){
        editEnable= RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.teaching,RoleConstants.teaching_course,RoleConstants.role_edit);
        dropEnable=RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.teaching,RoleConstants.teaching_course,RoleConstants.role_drop);
    }


    public CurriculumAdapter(Context context,List<CurriculumVO> campusVOs){
        this.layoutInflater=LayoutInflater.from(context);
        if (campusVOs==null){
            campusVOs=new ArrayList<>();
        }
        this.curriculumVOs=campusVOs;
        this.context=context;
        getrole();
    }


    public void setCampusVOs(List<CurriculumVO> campusVOs,LinearLayout contentview) {
        this.curriculumVOs = campusVOs;

        refresh(contentview);
//        this.notifyDataSetChanged();


    }

    private void refresh(LinearLayout contentview){
        this.contentview=contentview;
        if ((curriculumVOs!=null)&&(curriculumVOs.size()>0)){
            this.notifyDataSetChanged();
            if (exceptionVIew!=null) {
                this.contentview.removeView(exceptionVIew);
            }
        }else{
            this.contentview.removeAllViews();
            this.exceptionVIew=ExceptionVIew.loadView(context,this.contentview,R.mipmap.bknull,context.getResources().getString(R.string.curriculum_activity_list_null_message),null);
        }
    }


    @Override
    public int getCount() {
        return curriculumVOs.size();
    }

    @Override
    public Object getItem(int position) {
        return curriculumVOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        View contentView;
        ImageView imageView;

        TextView bkNameView;

        TextView studentCountView;
        TextView pricesView;

        TextView desView;
        TextView curriculumView;

        TextView campusNameView;

        View campusContentView;
        TextView campusNameValue;
        ImageView campusLogoView;
        ImageView campusRZStutasView;
        View moreView;

        View buttomView;

        View disableView;
        View toEditView;
        TextView reaseView;
        View disableImageView;

        LinearLayout teacherListView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.activity_curriculum_list_item,null);
            viewHolder.contentView=convertView.findViewById(R.id.content_view);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.bk_logo_imageView);
            viewHolder.bkNameView= (TextView) convertView.findViewById(R.id.xq_name_textview);
            viewHolder.studentCountView= (TextView) convertView.findViewById(R.id.zs_num_view);
            viewHolder.pricesView= (TextView) convertView.findViewById(R.id.zs_price_view);
            viewHolder.desView= (TextView) convertView.findViewById(R.id.zs_des_view);
            viewHolder.buttomView=convertView.findViewById(R.id.buttom_view);
            viewHolder.campusContentView=convertView.findViewById(R.id.campus_content_view);
            viewHolder.campusNameValue = (TextView) convertView.findViewById(R.id.campus_name_view);
            viewHolder.campusLogoView= (ImageView) convertView.findViewById(R.id.campus_logo_view);
            viewHolder.campusRZStutasView= (ImageView) convertView.findViewById(R.id.campus_rz_status_view);
            viewHolder.moreView=convertView.findViewById(R.id.more_view);
            viewHolder.teacherListView= (LinearLayout) convertView.findViewById(R.id.teacher_list_view);
            viewHolder.campusNameView= (TextView) convertView.findViewById(R.id.campus_name_new_view);

            viewHolder.disableView=convertView.findViewById(R.id.xj_view);
            viewHolder.toEditView=convertView.findViewById(R.id.to_edit_view);
            viewHolder.reaseView = (TextView) convertView.findViewById(R.id.reson_view);
            viewHolder.disableImageView=convertView.findViewById(R.id.disable_imageview);

            viewHolder.curriculumView= (TextView) convertView.findViewById(R.id.curriculum_des_view);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        final CurriculumVO curriculumVO=curriculumVOs.get(position);
//        if (position%2!=0){
//            curriculumVO.setStatus("0");
//        }else{
//            curriculumVO.setStatus("1");
//        }

        viewHolder.bkNameView.setText(curriculumVO.getName());

        String zsrss=String.format(context.getResources().getString(R.string.curriculum_activity_add_campus_info_bk_zsrs),String.valueOf(curriculumVO.getPerson()));
//        viewHolder.studentCountView.setText(zsrss);

        String zsrps=String.format(context.getResources().getString(R.string.curriculum_activity_add_campus_info_bk_zsjg),curriculumVO.getAmount());
//        viewHolder.pricesView.setText(zsrps);

        StringBuffer message=new StringBuffer(curriculumVO.getCate_name());
        message.append(",").append(zsrss);
        message.append(",").append(zsrps);
        viewHolder.studentCountView.setText(message.toString());

        if ((!editEnable)&&(!dropEnable)){
            viewHolder.moreView.setVisibility(View.GONE);
        }

        viewHolder.moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.view_popup_delete_edit,null);
                final BaseActivity baseActivity= (BaseActivity) context;
                final PopupWindow popupWindow = baseActivity.showPopupWindow(v,view,R.color.theme_transparent_style);

                View deleteview = view.findViewById(R.id.delete_view);
                if (!dropEnable){
                    deleteview.setVisibility(View.GONE);
                }
                deleteview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                        baseActivity.showDeleteDialog(new BaseActivity.SelectedListener() {
                            @Override
                            public void callBack(boolean flag) {
                                if (flag) {
                                    new OperateServers().deleteCurriculum(String.valueOf(curriculumVO.getId()), new HttpCallBack<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        curriculumVOs.remove(curriculumVO);

                                        refresh(contentview);
//                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFail(String msg) {
                                    }
                                });
                                }
                            }
                        });
                    }
                });


                View editview =view.findViewById(R.id.edit_view);
                if (!editEnable){
                    editview.setVisibility(View.GONE);
                }
                editview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent intent=new Intent(context,AddCurriculumActivity.class);
                        intent.putExtra("curriculumVO",curriculumVO);
                        intent.putExtra("campusVO",campusVOInfo);

                        context.startActivity(intent);
                    }
                });
            }
        });

        List<String> pics=curriculumVO.getPhoto_url();
        if ((pics!=null)&&(pics.size()>0)) {
            BaseApplication.application.getDisplayImageOptions(pics.get(0),viewHolder.imageView);
        }

        if (position==(curriculumVOs.size()-1)){
            viewHolder.buttomView.setVisibility(View.VISIBLE);
        }else{
            viewHolder.buttomView.setVisibility(View.GONE);
        }

        if ("0".equals(curriculumVO.getStatus())){
            viewHolder.contentView.setAlpha(0.6f);
            viewHolder.disableImageView.setVisibility(View.VISIBLE);
            viewHolder.moreView.setVisibility(View.INVISIBLE);
            viewHolder.disableView.setVisibility(View.VISIBLE);
            viewHolder.reaseView.setText(curriculumVO.getReason());
            viewHolder.toEditView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,AddCurriculumActivity.class);
                    intent.putExtra("curriculumVO",curriculumVO);
                    intent.putExtra("campusVO",campusVOInfo);
                    context.startActivity(intent);
                }
            });
        }else{
            viewHolder.contentView.setAlpha(1.0f);
            viewHolder.disableImageView.setVisibility(View.GONE);
            viewHolder.moreView.setVisibility(View.VISIBLE);
            viewHolder.disableView.setVisibility(View.GONE);
            viewHolder.toEditView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        viewHolder.teacherListView.removeAllViews();
        List<TeacherVO> teacherVOs=curriculumVO.getTeachers();
        if ((teacherVOs!=null)&&(teacherVOs.size()>0)){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (TeacherVO teacherVO:teacherVOs){
                View itemview=layoutInflater.inflate(R.layout.view_teacher_item,null);
                ImageView headerview= (ImageView) itemview.findViewById(R.id.teacher_header_photo_image);
                TextView nameView= (TextView) itemview.findViewById(R.id.teacher_name);
                ImageView stateView= (ImageView) itemview.findViewById(R.id.state_imageView);
                nameView.setText(teacherVO.getName());
                BaseApplication.application.getDisplayImageOptions(teacherVO.getAvatar_url(),headerview);
                if (teacherVO.getState()){
                    stateView.setVisibility(View.VISIBLE);
                }else{
                    stateView.setVisibility(View.GONE);
                }

                viewHolder.teacherListView.addView(itemview,layoutParams);
            }
        }

//        if ((position==(curriculumVOs.size()-1))&&(position>2)){
//            viewHolder.buttomView.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.buttomView.setVisibility(View.GONE);
//        }

        SpannableStringBuilder builder = new SpannableStringBuilder(viewHolder.curriculumView.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.theme_unauto_background_color));
        builder.setSpan(redSpan, 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.curriculumView.setText(builder);

        CampusVO campusVO= curriculumVO.getCampus();
        if (campusVO!=null) {
            viewHolder.campusContentView.setVisibility(View.VISIBLE);
            viewHolder.campusNameValue.setText(curriculumVO.getCampus().getName());
            String url="";
            List<String> photos=campusVO.getPhoto_url();
            if ((photos!=null)&&(photos.size()>0)){
                url=photos.get(0);
            }
            BaseApplication.application.getDisplayImageOptions(url,viewHolder.campusLogoView);

            if ("1".equals(campusVO.getStatus())){
                viewHolder.campusRZStutasView.setImageDrawable(BitmapUtils.getDrawable(context, R.mipmap.ysm));
            }else {
                viewHolder.campusRZStutasView.setImageDrawable(BitmapUtils.getDrawable(context, R.mipmap.wsm));
            }
        }else{
            viewHolder.campusContentView.setVisibility(View.GONE);
        }

        String campusname=curriculumVO.getCampus_name();
        if (campusVOInfo==null) {
            if ((campusname != null) && (campusname.length() > 0)) {
                viewHolder.campusNameView.setVisibility(View.VISIBLE);
                viewHolder.campusNameView.setText(campusname);
            } else {
                viewHolder.campusNameView.setVisibility(View.GONE);
            }
        }else{
            viewHolder.campusNameView.setVisibility(View.GONE);
        }

        viewHolder.desView.setVisibility(View.GONE);
        viewHolder.desView.setText("暂无");
        List<TimeVO> timeVOs=curriculumVO.getTimerules();
        if ((timeVOs!=null)&&(timeVOs.size()>0)){
            TimeVO timeVO=timeVOs.get(0);
            if (timeVO!=null){
                String timeDur=timeVO.getStart_time()+"-"+timeVO.getEnd_time();

                String[] repeatAts = timeVO.getRepeat_at().split(",");
                String title;
                if (CurriculumTimeView.MONTH.equals(timeVO.getRepeat_mode())){
                    title=getDays(repeatAts);
                }else{
                    title=getTimes(repeatAts);
                }

                StringBuffer stringBuffer=new StringBuffer("");
                stringBuffer.append("每").append(title).append(" ").append(timeDur);
                viewHolder.desView.setText(stringBuffer.toString());

            }
        }



        return convertView;
    }


    public String getDays(String[] repeatAts){
        StringBuffer stringBuffer=new StringBuffer("");
        String hz="号";
        for (String name : repeatAts){
            if (stringBuffer.length()>0){
                stringBuffer.append(",");
            }
            stringBuffer.append(name).append(hz);
        }

        return stringBuffer.toString();
    }

    public String getTimes(String[] repeatAts){
        StringBuffer stringBuffer=new StringBuffer("");
        String[] times=new String[]{"每天","周一","周二","周三","周四","周五","周六","周日"};
        for (String index : repeatAts){
            int num=Integer.valueOf(index);

            String name;
            if (num<times.length){
                name=times[num];
            }else{
                name="暂无";
            }
            if (stringBuffer.length()>0){
                stringBuffer.append(",");
            }
            stringBuffer.append(name);
        }
        return stringBuffer.toString();
    }
}
