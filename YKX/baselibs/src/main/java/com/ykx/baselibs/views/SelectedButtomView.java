package com.ykx.baselibs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.ykx.baselibs.R;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.baselibs.vo.TypeVO;

import java.util.ArrayList;

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
 * Created by 2017/4/7.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class SelectedButtomView extends LinearLayout {

    private ViewGroup contentView;
    private View bgView,buttomView;
    private Button trueView,cannelView;
    private TextView titleView;

//    private ListView itemView;
    private WheelView wheelView;

//    private ItemAdapter itemAdapter;
    private Context context;

    private TypeVO typeVO;

    private SelectedButtomViewListener selectedButtomViewListener;

    public interface SelectedButtomViewListener{
        void callBack(boolean isTrue,TypeVO typevo);
    }

    public SelectedButtomView(Context context) {
        super(context);
        initUI(context);
    }

    public SelectedButtomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.view_selected_buttom,null);
        bgView=view.findViewById(R.id.bg_view);
        buttomView=view.findViewById(R.id.buttom_view);
        trueView= (Button) view.findViewById(R.id.btnSubmit);
        cannelView= (Button) view.findViewById(R.id.btnCancel);
        titleView= (TextView) view.findViewById(R.id.tvTitle);
//        itemView= (ListView) view.findViewById(R.id.item_view);
        wheelView= (WheelView) view.findViewById(R.id.my_item_view);
        titleView.setText("");

        trueView.setOnClickListener(clickListener);
        cannelView.setOnClickListener(clickListener);

//        itemAdapter=new ItemAdapter(context,new ArrayList<TypeVO>());
//        itemView.setAdapter(itemAdapter);

//        bgView.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
        bgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeViewAnimation();
            }
        });

        addView(view,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

    }

    public static void showView(int selectedIndex,Context context, FrameLayout frameLayout,ArrayList<TypeVO> list, SelectedButtomView.SelectedButtomViewListener selectedButtomViewListener){
        SelectedButtomView selectedButtomView=new SelectedButtomView(context);
        selectedButtomView.resetView(selectedIndex,list,selectedButtomViewListener,frameLayout);
        frameLayout.addView(selectedButtomView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    public static void showViewWithName(String selectedName,Context context, FrameLayout frameLayout,ArrayList<TypeVO> list, SelectedButtomView.SelectedButtomViewListener selectedButtomViewListener){
        SelectedButtomView selectedButtomView=new SelectedButtomView(context);
        selectedButtomView.resetView(selectedName,list,selectedButtomViewListener,frameLayout);
        frameLayout.addView(selectedButtomView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private void resetView(String selectedName,final ArrayList<TypeVO> typeVOs, SelectedButtomViewListener selectedButtomViewListener,ViewGroup contentView){
        this.contentView=contentView;
        if ((typeVOs!=null)&&(typeVOs.size()>0)) {

            ArrayList<String> datas=new ArrayList<>();
            int index=0;
            for (int i=0;i<typeVOs.size();i++){
                TypeVO typeVO=typeVOs.get(i);
                datas.add(typeVO.getName());
                if (typeVO.getName().equals(selectedName)){
                    index=i;
                }
            }
            if ((selectedName!=null)&&(selectedName.length()>0)){}else{
                index=0;
            }
            typeVO=typeVOs.get(0);
            wheelView.setCellHeight(DensityUtil.dip2px(context,20));
            wheelView.setAdapter(new ArrayWheelAdapter(datas,datas.size()));
            wheelView.setCurrentItem(index);
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    typeVO=typeVOs.get(index);
                }
            });
//
//            int size=typeVOs.size();
//            if (size>5){
//                size=5;
//            }
//            itemView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context,40*size)));
//            itemAdapter.refreshf(typeVOs);
            this.selectedButtomViewListener = selectedButtomViewListener;
            addViewAnimation();
        }
    }

    private void resetView(int selectedIndex,final ArrayList<TypeVO> typeVOs, SelectedButtomViewListener selectedButtomViewListener,ViewGroup contentView){
        this.contentView=contentView;
        if ((typeVOs!=null)&&(typeVOs.size()>0)) {

            ArrayList<String> datas=new ArrayList<>();
            for (TypeVO typeVO:typeVOs){
                datas.add(typeVO.getName());
            }
            typeVO=typeVOs.get(0);
            wheelView.setCellHeight(DensityUtil.dip2px(context,20));
            wheelView.setAdapter(new ArrayWheelAdapter(datas,datas.size()));
            wheelView.setCurrentItem(selectedIndex);
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    typeVO=typeVOs.get(index);
                }
            });
//
//            int size=typeVOs.size();
//            if (size>5){
//                size=5;
//            }
//            itemView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context,40*size)));
//            itemAdapter.refreshf(typeVOs);
            this.selectedButtomViewListener = selectedButtomViewListener;
            addViewAnimation();
        }
    }


    private OnClickListener clickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.btnSubmit){
                if (typeVO!=null) {
                    if (selectedButtomViewListener != null) {
                        selectedButtomViewListener.callBack(true, typeVO);
                    }
                }
                removeViewAnimation();
            }else if(v.getId()==R.id.btnCancel){
                if (selectedButtomViewListener != null) {
                    selectedButtomViewListener.callBack(false, null);
                }
                removeViewAnimation();
            }
        }
    };

    private void removeViewAnimation(){
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, 0,DensityUtil.dip2px(context,280));//横向位移
        /**
         accelerate_decelerate_interpolator   加速-减速 动画插入器
         accelerate_interpolator               加速-动画插入器
         decelerate_interpolator               减速- 动画插入器
         */
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator() );
        tAnim.setDuration(300);
        buttomView.startAnimation(tAnim);

        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                contentView.removeView(SelectedButtomView.this);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });


        AlphaAnimation aAnim=new AlphaAnimation(1,0);
        aAnim.setDuration(300);
        aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        bgView.startAnimation(aAnim);
    }

    protected void addViewAnimation() {
            TranslateAnimation tAnim = new TranslateAnimation(0, 0, DensityUtil.dip2px(context,280), 0);//横向位移
            tAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            tAnim.setDuration(300);
            buttomView.startAnimation(tAnim);

            tAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            AlphaAnimation aAnim=new AlphaAnimation(0,1);
            aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            aAnim.setDuration(300);
            bgView.startAnimation(aAnim);
    }

//    class ItemAdapter extends BaseAdapter{
//
//        private LayoutInflater layoutInflater;
//        private ArrayList<TypeVO> typeVOs;
//
//        private ItemAdapter(Context context, ArrayList<TypeVO> typeVOs){
//            this.layoutInflater=LayoutInflater.from(context);
//            if (typeVOs==null){
//                typeVOs=new ArrayList<>();
//            }
//            this.typeVOs=typeVOs;
//        }
//
//        public void refreshf(ArrayList<TypeVO> typeVOs){
//            if (typeVOs==null){
//                typeVOs=new ArrayList<>();
//            }
//            this.typeVOs=typeVOs;
//            this.notifyDataSetChanged();
//        }
//
//
//        @Override
//        public int getCount() {
//            return typeVOs.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return typeVOs.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        class ViewHolder{
//            TextView itemView;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder;
//            if (convertView==null){
//                viewHolder=new ViewHolder();
//                convertView=layoutInflater.inflate(R.layout.selected_buttom_item,null);
//                viewHolder.itemView= (TextView) convertView.findViewById(R.id.listview_item);
//
//                convertView.setTag(viewHolder);
//            }else{
//                viewHolder= (ViewHolder) convertView.getTag();
//            }
//
//            TypeVO typeVO = typeVOs.get(position);
//            viewHolder.itemView.setText(typeVO.getName());
//
//
//            return convertView;
//        }
//    }

}
