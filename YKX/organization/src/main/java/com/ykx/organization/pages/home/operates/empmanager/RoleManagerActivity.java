package com.ykx.organization.pages.home.operates.empmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DeviceUtils;
import com.ykx.baselibs.utils.ObjectUtils;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.EmpVO;
import com.ykx.organization.storage.vo.RoleModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoleManagerActivity extends BaseActivity {

    private TextView noticeView;
    private HorizontalScrollView scrollView;
    private LinearLayout menuView;
    private ViewPager contentViewPage;
    private RolePagerAdapter rolePagerAdapter;

    private List<RoleModule> roleModules;

    private List<TitleMenuView> titleMenuViews=new ArrayList<>();
    private List<View> roleContentViews=new ArrayList<>();

    private EmpVO empVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        empVO= (EmpVO) getIntent().getSerializableExtra("empvo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_manager);

        initUI();
        loadDatas();
    }

    private String getRoleAndName(){
        StringBuffer stringBuffer=new StringBuffer("");

        String role=getMessage(getIntent().getStringExtra("roles"));
        String name=getMessage(getIntent().getStringExtra("name"));

        stringBuffer.append(role).append(",").append(name);

        return stringBuffer.toString();
    }

    @Override
    protected void setRightView(LinearLayout rightContentView) {

        TextView rightview=new TextView(this);
        rightview.setGravity(Gravity.CENTER);
        rightview.setText(getResources().getString(R.string.curriculum_activity_add_title_save));
        rightview.setTextSize(15);
        rightview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
        rightContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (roleModules!=null) {
                    RoleModule.RoleModuleList roleModuleList = RoleModule.getRoleModuleList();
                    roleModuleList.setDatas(roleModules);
                    Intent intent=new Intent();
                    intent.putExtra("roleModules",roleModuleList);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }


    private void initUI(){
        noticeView= (TextView) findViewById(R.id.role_manager_notices);
        menuView= (LinearLayout) findViewById(R.id.role_menu_title_view);
        contentViewPage= (ViewPager) findViewById(R.id.role_menu_c_view);
        scrollView= (HorizontalScrollView) findViewById(R.id.role_menu_scroll_title_view);
        scrollView.smoothScrollTo(0,0);

        String zsrps=String.format(getResources().getString(R.string.emp_manager_activity_role_manager_notices),getRoleAndName());
        noticeView.setText(zsrps);

        rolePagerAdapter=new RolePagerAdapter(new ArrayList<View>());
        contentViewPage.setAdapter(rolePagerAdapter);
        contentViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (titleMenuViews.size()>position) {
                    titleMenuViews.get(position).pageSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    private void loadDatas() {

        if ((empVO!=null)&&(empVO.getPower()!=null)&&(empVO.getPower().size()>0)){
            roleModules = empVO.getPower();
            loadTitleView();
            rolePagerAdapter.refresh(roleContentViews);
        }else {
            new OperateServers().getStaffRoles(new HttpCallBack<List<RoleModule>>() {
                public void onSuccess(List<RoleModule> datas) {
                    roleModules = datas;
                    if (datas == null) {
                        roleModules = new ArrayList<>();
                    }
                    loadTitleView();
                    rolePagerAdapter.refresh(roleContentViews);
                }

                public void onFail(String msg) {

                }
            });
        }
//        empRoleVO=new EmpRoleVO();
//        empRoleVO.creatdata();
//        Log.e("xx",new Gson().toJson(empRoleVO));
//
//        loadTitleView();
//        rolePagerAdapter.refresh(roleContentViews);
    }

    private void loadTitleView(){

        if (roleModules!=null){
//           Map<EmpRoleVO.RoleTitle,List<EmpRoleVO.RoleContentVO>> values= empRoleVO.getRoles();
            int width = DeviceUtils.getDeviceWith(RoleManagerActivity.this)/4;
            LayoutInflater layoutInflater=LayoutInflater.from(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            int i=0;
            for (RoleModule entry:roleModules){
                View view=layoutInflater.inflate(R.layout.view_role_manager_title_item,null);
                TextView titleview = (TextView) view.findViewById(R.id.title_view);
//                View rightview=view.findViewById(R.id.right_line_view);
                final View buttomtview=view.findViewById(R.id.buttom_line_view);
                titleview.setText(entry.getName());
                TitleMenuView titleMenuView=new TitleMenuView();
                titleMenuView.contentView=view;
                titleMenuView.titleview=titleview;
//                titleMenuView.rightview=rightview;
                titleMenuView.buttomtview=buttomtview;
                titleMenuView.create();
                titleMenuViews.add(titleMenuView);
                if (i==0) {
                    titleMenuView.selected(i);
                }else{
                    titleMenuView.unSelected(i);
                }
//                if (i==(values.entrySet().size()-1)){
//                    rightview.setVisibility(View.GONE);
//                }
                menuView.addView(view,layoutParams);

                RoleContentView roleContentView = new RoleContentView(entry.getModules());
                roleContentViews.add(roleContentView.getModelViews());

                i++;
            }
        }
    }

    class RoleContentView{
        ListView modelViews;

        private RoleModelAdapter roleModelAdapter;

        public RoleContentView(List<RoleModule> roleContentVOs){
            iniUI(roleContentVOs);
        }

        public ListView getModelViews() {
            return modelViews;
        }

        private void iniUI(List<RoleModule> roleContentVOs){
            modelViews=new ListView(BaseApplication.application);

            roleModelAdapter=new RoleModelAdapter(roleContentVOs);
            modelViews.setAdapter(roleModelAdapter);

        }

        public void resetListView(){

            roleModelAdapter.notifyDataSetChanged();

        }

        class RoleModelAdapter extends BaseAdapter{

            private List<RoleModule> roleContentVOs;

            public RoleModelAdapter(List<RoleModule> roleContentVOs){
                if (roleContentVOs==null){
                    roleContentVOs=new ArrayList<>();
                }
                this.roleContentVOs=roleContentVOs;
            }

            @Override
            public int getCount() {
                return roleContentVOs.size();
            }

            @Override
            public Object getItem(int position) {
                return roleContentVOs.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            class ViewHolder{
                TextView nameView;
                ImageView openView;

                ScrollView contentpview;
                LinearLayout contentview;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder viewHolder;
                if (convertView==null){
                    viewHolder=new ViewHolder();
                    convertView=LayoutInflater.from(BaseApplication.application).inflate(R.layout.view_role_manager_content_item,null);

                    viewHolder.nameView= (TextView) convertView.findViewById(R.id.role_model_view);
                    viewHolder.openView= (ImageView) convertView.findViewById(R.id.open_switch_view);
                    viewHolder.contentpview= (ScrollView) convertView.findViewById(R.id.role_item_content_view);
                    viewHolder.contentview= (LinearLayout) convertView.findViewById(R.id.role_item_view);

                    convertView.setTag(viewHolder);
                }else{
                    viewHolder= (ViewHolder) convertView.getTag();
                }

                final RoleModule roleContentVO = roleContentVOs.get(position);

                final ScrollView pcontentview=viewHolder.contentpview;
                final ImageView openView=viewHolder.openView;



                WindowManager wm = getWindowManager();
                int width = wm.getDefaultDisplay().getWidth();

                viewHolder.contentview.removeAllViews();
                List<RoleModule.ActionVO>  keyValues =roleContentVO.getAction();
                if ((keyValues!=null)&&(keyValues.size()>0)) {
                    pcontentview.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 4, LinearLayout.LayoutParams.WRAP_CONTENT);
                    for (final RoleModule.ActionVO keyValue : keyValues) {
                        View view = LayoutInflater.from(BaseApplication.application).inflate(R.layout.view_checkbox, null);
                        CheckBox checkView = (CheckBox) view.findViewById(R.id.check_box_view);
                        checkView.setChecked(keyValue.isChecked());
                        TextView titleview = (TextView) view.findViewById(R.id.check_box_title_view);
                        titleview.setText(keyValue.getName());

                        checkView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                keyValue.setChecked(isChecked);
                            }
                        });

                        viewHolder.contentview.addView(view, layoutParams);

                    }
                }else{
                    pcontentview.setVisibility(View.GONE);
                }

                viewHolder.nameView.setText(roleContentVO.getName());
                viewHolder.nameView.setTextColor(getResources().getColor(R.color.theme_first_text_color));
                if (roleContentVO.isShow()){
                    openView.setTag(Boolean.valueOf(true));
                    openView.setImageDrawable(BitmapUtils.getDrawable(RoleManagerActivity.this,R.drawable.svg_switch_on));
                    List<RoleModule.ActionVO>  keyValuesn =roleContentVO.getAction();
                    if ((keyValuesn!=null)&&(keyValuesn.size()>0)) {
                        pcontentview.setVisibility(View.VISIBLE);
                    }else{
                        pcontentview.setVisibility(View.GONE);
                    }
                }else{
                    openView.setTag(Boolean.valueOf(false));
                    openView.setImageDrawable(BitmapUtils.getDrawable(RoleManagerActivity.this,R.drawable.svg_switch_off));
                    pcontentview.setVisibility(View.GONE);
                }


                viewHolder.openView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object object=v.getTag();
                        if (object!=null) {
                            if (object instanceof Boolean) {
                                boolean isshow = (Boolean) object;
                                if (!isshow) {
                                    openView.setImageDrawable(BitmapUtils.getDrawable(RoleManagerActivity.this,R.drawable.svg_switch_on));
                                    List<RoleModule.ActionVO>  keyValuesn =roleContentVO.getAction();
                                    if ((keyValuesn!=null)&&(keyValuesn.size()>0)) {
                                        pcontentview.setVisibility(View.VISIBLE);
                                    }else{
                                        pcontentview.setVisibility(View.GONE);
                                    }
                                }else{
                                    openView.setImageDrawable(BitmapUtils.getDrawable(RoleManagerActivity.this,R.drawable.svg_switch_off));
                                    pcontentview.setVisibility(View.GONE);
                                }
                                openView.setTag(Boolean.valueOf(!isshow));
                                roleContentVO.setShow(!isshow);
                            }
                        }

                    }
                });

//                viewHolder.openView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView,
//                                                 boolean isChecked) {
//                        roleContentVO.setShow(isChecked);
//                        if (isChecked){
//                            openView.setChecked(true);
//                            pcontentview.setVisibility(View.VISIBLE);
//                        }else{
//                            openView.setChecked(false);
//                            pcontentview.setVisibility(View.GONE);
//                        }
//                    }
//                });

                return convertView;
            }
        }

    }

    class TitleMenuView{

        View contentView;
        TextView titleview;
        //        View rightview;
        View buttomtview;
        int index;
        boolean isSelected;

        public void create(){

            if (contentView!=null) {
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pageSelected(false);
                    }
                });
            }
        }

        /**
         * 是否viewpage 滑动选中
         * @param pageSelected
         */
        public void pageSelected(boolean pageSelected){

            if (isSelected){
                return;
            }

            for (TitleMenuView titleMenuView : titleMenuViews) {
                if (titleMenuView.isSelected) {
                    titleMenuView.unSelected(titleMenuView.index);
                }
            }
            buttomtview.setBackgroundColor(getResources().getColor(R.color.theme_main_background_color));
            titleview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
            if (!pageSelected) {
                contentViewPage.setCurrentItem(index);
            }

            isSelected=true;
        }


        public void selected(final int index){
            isSelected=true;
            this.index=index;
            buttomtview.setBackgroundColor(getResources().getColor(R.color.theme_main_background_color));
            titleview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
        }


        public void unSelected(int index){
            isSelected=false;
            this.index=index;
            buttomtview.setBackgroundColor(getResources().getColor(R.color.default_line_color));
            titleview.setTextColor(getResources().getColor(R.color.theme_first_text_color));
        }
    }


    class RolePagerAdapter extends PagerAdapter {

        private List<View> pageViews;

        public RolePagerAdapter(List<View> pageViews){
            if (pageViews==null){
                pageViews=new ArrayList<>();
            }
            this.pageViews=pageViews;
        }

        private void refresh(List<View> pageViews){
            if (pageViews!=null){
                this.pageViews=pageViews;
                notifyDataSetChanged();
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(pageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View pageview=pageViews.get(position);

            container.addView(pageview);


            return pageview;
        }
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.emp_manager_activity_role_manager_title);
    }
}
