package com.ykx.organization.pages.home.operates.curriculum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.ykx.baselibs.utils.DateUtil;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.baselibs.views.MyViewPager;
import com.ykx.organization.R;
import com.ykx.organization.storage.vo.TimeVO;
import com.ykx.organization.views.DateAndTimeVIew;
import com.ykx.organization.views.DateTypeView;

import java.lang.reflect.Field;
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
 * Created by 2017/3/20.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class CurriculumTimeView extends LinearLayout {

    public static final String MONTH="month";

    private Context context;

    private View buttomView;
    private View bgButtomView;

    private DateAndTimeVIew startDate;
    private DateAndTimeVIew endDate;
    private DateAndTimeVIew startTime;
    private DateAndTimeVIew endTime;
    private DateTypeView dateTypeView;


    private TextView titleView;
    private MyViewPager viewPager;

    private String[] titles;

    private int index=0;

    private ViewGroup contentViewGroup;

    private TextView lastView,nextView;


    public interface CallBackListener{
        void callBackRemove(View view, boolean isCannel, TimeVO timeVO);
    }

    private CallBackListener callBackListener;


    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public CurriculumTimeView(Context context) {
        super(context);
        initUI(context);
    }

    public CurriculumTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public void addViewInContentview(final ViewGroup viewGroup){
        contentViewGroup=viewGroup;
        viewGroup.setVisibility(View.INVISIBLE);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        viewGroup.addView(this,layoutParams);
    }

    public void show(){
        addViewAnimation(buttomView,bgButtomView);
        contentViewGroup.setVisibility(View.VISIBLE);
    }

    class FixedSpeedScroller extends Scroller {

        private int mDuration =200;

        public void setTime(int scrollerTime){
            mDuration=scrollerTime;
        }
        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @SuppressLint("NewApi") public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    /**
     * 设置滑动时间
     */
    public void setScrollerTime(int scrollerTime,ViewPager mViewPager){
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller= new FixedSpeedScroller(context,new AccelerateInterpolator());
            scroller.setTime(scrollerTime);
            mScroller.set(mViewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<View> loadWheelView(){

        ArrayList<View> viewList = new ArrayList<>();// 将要分页显示的View装入数组中
        startDate=new DateAndTimeVIew(context) {
            @Override
            public TimePickerView.Type getType() {
                return TimePickerView.Type.YEAR_MONTH_DAY;
            }
        };

        endDate=new DateAndTimeVIew(context) {
            @Override
            public TimePickerView.Type getType() {
                return TimePickerView.Type.YEAR_MONTH_DAY;
            }
        };


        startTime=new DateAndTimeVIew(context) {
            @Override
            public TimePickerView.Type getType() {
                return TimePickerView.Type.HOURS_MINS;
            }
        };
        endTime=new DateAndTimeVIew(context) {
            @Override
            public TimePickerView.Type getType() {
                return TimePickerView.Type.HOURS_MINS;
            }
        };
        dateTypeView=new DateTypeView(context);

        viewList.add(startDate);
        viewList.add(endDate);
        viewList.add(dateTypeView);
        viewList.add(startTime);
        viewList.add(endTime);

        return viewList;
    }

    class MyPageAdapter extends PagerAdapter{

        private List<View> viewList;

        public MyPageAdapter(List<View> viewList) {
            if (viewList==null){
                viewList=new ArrayList<>();
            }
            this.viewList=viewList;
        }

        private void refresh(){
            this.viewList=loadWheelView();
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(viewList.get(position));


            return viewList.get(position);
        }
    }

    private void initUI(final Context context){
        this.context=context;
        titles=new String[]{getResources().getString(R.string.curriculum_activity_add_campus_time_title_start_date),getResources().getString(R.string.curriculum_activity_add_campus_time_title_end_date),getResources().getString(R.string.curriculum_activity_add_campus_time_title),getResources().getString(R.string.curriculum_activity_add_campus_time_title_start_time),getResources().getString(R.string.curriculum_activity_add_campus_time_title_end_time)};
        View view= LayoutInflater.from(context).inflate(R.layout.view_curriculum_time,null);
        titleView= (TextView) view.findViewById(R.id.pop_selected_title_view);
        buttomView=view.findViewById(R.id.campus_time_contentview);
        viewPager= (MyViewPager) view.findViewById(R.id.viewpager);
        viewPager.setNoScrollble(true);
        lastView= (TextView) view.findViewById(R.id.last_page_view);
        nextView= (TextView) view.findViewById(R.id.next_page_view);

        resetPopView(false);
//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        addView(view,layoutParams);
        bgButtomView=view.findViewById(R.id.close_campus_view);
        bgButtomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cannelPopAction(null);
            }
        });
        buttomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        nextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (index>=4) {
                    if (!isNext(startTime.getTime(),endTime.getTime())){
                        Toast.makeText(context,"开始时间必须小于结束时间!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    submitPopAction();
                    return;
                }

                if (index==1){
                    if (!isNext(startDate.getTime(),endDate.getTime())){
                        Toast.makeText(context,"开始日期必须小于结束日期!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else if(index==2){
                    String data=dateTypeView.getDataAdapter().getSelectedData();
                    if (data.length()<=0){
                        Toast.makeText(context,"必须选择日期!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (index==0){
                    endDate.setTime(startDate.getTime());
                    lastView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_last));
                }else {
                    if(index==3){
                        endTime.setTime(startTime.getTime());
                        nextView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_sure));
                    }else{
                        nextView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_next));
                    }
                }
                viewPager.setCurrentItem(++index);
                setTitle(index);
            }
        });


        lastView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index==0) {
                    cannelPopAction(null);
                    return;
                }
                if (index==1){
                    lastView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_cannel));
                }else {
                    if(index==5){
                        nextView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_sure));
                    }else{
                        nextView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_next));
                    }
                }
                viewPager.setCurrentItem(--index);
                setTitle(index);

            }
        });

    }

    public boolean isNext(String startTime,String endTime){
        long start = DateUtil.stringToDate(startTime,DateUtil.DATE_FORMAT).getTime();
        long end = DateUtil.stringToDate(endTime,DateUtil.DATE_FORMAT).getTime();
        if (end-start>0){
            return true;
        }
        return false;
    }

    /**
     * 提交时间设置信息
     */
    private void submitPopAction() {
        // TODO: 2017/3/21 提交保存
        TimeVO timeVO=new TimeVO();
        timeVO.setId(0);
        timeVO.setRepeat_mode((dateTypeView.getSelectedTypeFalg()==0)?"week":"month");
        timeVO.setRepeat_at(dateTypeView.getDataAdapter().getSelectedData());
        timeVO.setStart_date(subString(startDate.getTime(),0,10));
        timeVO.setEnd_date(subString(endDate.getTime(),0,10));
        timeVO.setStart_time(subString(startTime.getTime(),11,16));
        timeVO.setEnd_time(subString(endTime.getTime(),11,16));

        cannelPopAction(timeVO);

        resetPopView(true);
    }

    private void resetPopView(boolean isdelay){
        int delay=0;
        if (isdelay) {
            delay=300;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                index = 0;
                setScrollerTime(200, viewPager);
                viewPager.setAdapter(new MyPageAdapter(loadWheelView()));
                nextView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_next));
                lastView.setText(getResources().getString(R.string.curriculum_activity_add_campus_time_view_cannel));
            }
        },delay);
    }


    private String subString(String string,int start,int end){
        if ((string!=null)&&(string.length()>end)){
            return string.substring(start,end);
        }
        return "";
    }

    /**
     * 取消时间设置
     */
    private void cannelPopAction(final TimeVO timeVO){
        removeViewAnimation(buttomView, bgButtomView, new FirstOverListener() {
            @Override
            public void animationEnd() {
                if (callBackListener!=null){
                    callBackListener.callBackRemove(CurriculumTimeView.this,false,timeVO);
                }
            }
        });
    }

    private void setTitle(int index){
        titleView.setText(titles[index]);
    }


    private void removeViewAnimation(View contextView,View bgView,final FirstOverListener firstOverListener){
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, 0, DensityUtil.dip2px(context,400));//横向位移
        /**
         accelerate_decelerate_interpolator   加速-减速 动画插入器
         accelerate_interpolator               加速-动画插入器
         decelerate_interpolator               减速- 动画插入器
         */
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator() );
        tAnim.setDuration(300);
        contextView.startAnimation(tAnim);

        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                if (firstOverListener!=null){
                    firstOverListener.animationEnd();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });


        AlphaAnimation aAnim=new AlphaAnimation(1,0);
        aAnim.setDuration(300);
        aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        bgView.startAnimation(aAnim);
    }

    protected void addViewAnimation(View contextView,View bgView) {
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, DensityUtil.dip2px(context,400), 0);//横向位移
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        tAnim.setDuration(300);
        contextView.startAnimation(tAnim);

        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        AlphaAnimation aAnim=new AlphaAnimation(0,1);
        aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        aAnim.setDuration(300);
        bgView.startAnimation(aAnim);
    }


    public interface FirstOverListener{
        void animationEnd();
    }


}
