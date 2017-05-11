package com.ykx.organization.pages.home.operates.campus;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.pages.ExceptionVIew;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.baselibs.views.refreshview.pulltorefreshswipemenulistviewsample.PullToRefreshSwipeMenuListView;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.home.operates.curriculum.CurriculumListActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.CampusCoursVO;
import com.ykx.organization.storage.vo.CampusInfo;
import com.ykx.organization.storage.vo.CampusVO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CampusListActivity extends BaseActivity implements PullToRefreshSwipeMenuListView.IXListViewListener {

    private CampusAdapter campusAdapter;

//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullToRefreshSwipeMenuListView listView;
    private LinearLayout contentview;
    private ExceptionVIew exceptionVIew;

    private int indexPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_list);

        initUI();
        loadData(1,true);
    }

    private void initUI(){
        contentview= (LinearLayout) findViewById(R.id.content_view);

        listView = (PullToRefreshSwipeMenuListView) findViewById(R.id.curriculum_listview);

        campusAdapter=new CampusAdapter(this,new ArrayList<CampusVO>());
        listView.setAdapter(campusAdapter);

        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);

        boolean isadd = RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_campus,RoleConstants.role_add);
        View view=findViewById(R.id.buttom_view);
        if (!isadd){
            view.setVisibility(View.GONE);
        }

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
////                CurriculumVO curriculumVO = (CurriculumVO) curriculumAdapter.getItem(position);
//
//
//            }
//        });

//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
//        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_green_light);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData();
//            }
//        });
//        mSwipeRefreshLayout.setEnabled(true);
//
//
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (isListViewReachTopEdge(absListView)) {
//                    mSwipeRefreshLayout.setEnabled(true);
//                }else {
//                    mSwipeRefreshLayout.setEnabled(false);
//                }
//            }
//
//            public boolean isListViewReachTopEdge(AbsListView listView) {
//                boolean result = false;
//                if (listView.getFirstVisiblePosition() == 0) {
//                    final View topChildView = listView.getChildAt(0);
//                    if (topChildView!=null) {
//                        result = topChildView.getTop() == 0;
//                    }
//                }
//                return result;
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        CampusVO campusVO= (CampusVO) intent.getSerializableExtra("CampusVO");

        if (campusVO!=null) {
            campusAdapter.getCampusVOs().add(campusVO);
            campusAdapter.refresh();
//            campusAdapter.notifyDataSetChanged();
        }

    }

    private void loadData(int pageIndex,final boolean refreshFlag){
        if (refreshFlag){
            pageIndex=1;
        }
        new OperateServers().getCampusDatas(pageIndex,new HttpCallBack<CampusInfo >() {
            @Override
            public void onSuccess(CampusInfo  data) {
                if (refreshFlag){
                    campusAdapter.setCampusVOs(data.getData());
                }else{
                    List<CampusVO> allDatas=campusAdapter.getCampusVOs();
                    allDatas.addAll(data.getData());
                    campusAdapter.setCampusVOs(allDatas);
                }
                indexPage=data.getCurrent_page()+1;
//                mSwipeRefreshLayout.setRefreshing(false);
                if (refreshFlag) {
                    listView.stopRefresh();
                }else{
                    listView.stopLoadMore();
                }
            }

            @Override
            public void onFail(String msg) {
//                mSwipeRefreshLayout.setRefreshing(false);
                if (refreshFlag) {
                    listView.stopRefresh();
                }else{
                    listView.stopLoadMore();
                }
            }
        });
    }

    public void addCurriculumAction(View view){
        new OperateServers().getCampusCoursCount(new HttpCallBack<CampusCoursVO>() {
            @Override
            public void onSuccess(CampusCoursVO data) {

                if (data!=null){
                    if (data.getCampus_count()>=data.getCampus_max()){
                        toastMessage(getResString(R.string.campus_activity_add_max_campus_toast));
                        return;
                    }
                    Intent intent=new Intent(CampusListActivity.this,AddCampusActivity.class);
                    boolean isfirst=true;
                    if ((campusAdapter!=null)&&(campusAdapter.getCampusVOs().size()>0)){
                        isfirst=false;
                    }
                    intent.putExtra("firstAddCampus",isfirst);
                    startActivity(intent);
                }else{
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });

    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.curriculum_activity_list_title);
    }

    @Override
    public void onRefresh() {
        loadData(1,true);
    }

    @Override
    public void onLoadMore() {
        loadData(indexPage,false);
    }


    class CampusAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<CampusVO> campusVOs;

        private Context context;

        private boolean editEnable=true;
        private boolean dropEnable=true;

        public CampusAdapter(Context context,List<CampusVO> campusVOs){
            this.layoutInflater=LayoutInflater.from(context);
            if (campusVOs==null){
                campusVOs=new ArrayList<>();
            }
            this.campusVOs=campusVOs;
            this.context=context;
            getrole();
        }

        private void getrole(){
            editEnable= RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_campus,RoleConstants.role_edit);
            dropEnable=RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_campus,RoleConstants.role_drop);
        }

        public List<CampusVO> getCampusVOs() {
            if (campusVOs==null){
                campusVOs=new ArrayList<>();
            }
            return campusVOs;
        }

        public void setCampusVOs(List<CampusVO> campusVOs) {
            this.campusVOs = campusVOs;
            refresh();
        }

        public void refresh(){
            if ((campusVOs!=null)&&(campusVOs.size()>0)){
                this.notifyDataSetChanged();
                if (exceptionVIew!=null) {
                    contentview.removeView(exceptionVIew);
                }
            }else{
                contentview.removeAllViews();
                new OperateServers().getCampusCoursCount(new HttpCallBack<CampusCoursVO>() {
                    @Override
                    public void onSuccess(CampusCoursVO data) {
                        if (data!=null){
                            setDefaultNull(data.getCampus_count(),data.getCampus_max());
                        }else{
                            setDefaultNull(0,0);
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        setDefaultNull(0,0);
                    }
                });

            }
        }

        private void setDefaultNull(int campus_count,int campus_limit){

            String zsrss=String.format(context.getResources().getString(R.string.campus_activity_list_null_title),Integer.valueOf(campus_count),Integer.valueOf(campus_limit-campus_count));

            exceptionVIew=ExceptionVIew.loadView(CampusListActivity.this,contentview,R.mipmap.xqnull,zsrss,null);
        }

        @Override
        public int getCount() {
            return campusVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return campusVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{

            TextView nameView;
            TextView addressView;

            GridView campusTypeView;

            TextView managerView;

            View moreView;

//            View buttomView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.activity_campus_list_item,null);

                viewHolder.nameView= (TextView) convertView.findViewById(R.id.xq_name_textview);
                viewHolder.addressView= (TextView) convertView.findViewById(R.id.xq_address_textview);

                viewHolder.campusTypeView= (GridView) convertView.findViewById(R.id.curriculum_type_campus_view);
                viewHolder.managerView= (TextView) convertView.findViewById(R.id.xq_curriculum_manager_textview);
//                viewHolder.buttomView=convertView.findViewById(R.id.buttom_line_view);
                viewHolder.moreView=convertView.findViewById(R.id.more_view);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            final CampusVO campusVO=campusVOs.get(position);
            viewHolder.nameView.setText(campusVO.getName());
            viewHolder.addressView.setText(campusVO.getAddress());

            if ((!editEnable)&&(!dropEnable)){
                viewHolder.moreView.setVisibility(View.GONE);
            }

            viewHolder.moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.view_popup_delete_edit,null);
                    final BaseActivity baseActivity= (BaseActivity) context;
                    final PopupWindow popupWindow = baseActivity.showPopupWindow(v,view,R.color.theme_transparent_style);

                    View deletview = view.findViewById(R.id.delete_view);
                    if (!dropEnable){
                        deletview.setVisibility(View.GONE);
                    }
                    deletview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            popupWindow.dismiss();
                            showDeleteDialog(new SelectedListener() {
                                @Override
                                public void callBack(boolean flag) {
                                    if (flag){
                                        new OperateServers().deleteCampusDatas(String.valueOf(campusVO.getId()), new HttpCallBack<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                campusVOs.remove(campusVO);
                                                refresh();
//                                                notifyDataSetChanged();
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

                    View editview=view.findViewById(R.id.edit_view);
                    if (!editEnable){
                        editview.setVisibility(View.GONE);
                    }
                    editview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            Intent intent=new Intent(context,AddCampusActivity.class);
                            intent.putExtra("campusVO",campusVO);

                            context.startActivity(intent);
                        }
                    });
                }
            });

            List<String> campuss=campusVO.getCurriculumInfos();

            viewHolder.campusTypeView.setAdapter(new CampusTypeAdapter(context,campuss));

            ViewGroup.LayoutParams params = viewHolder.campusTypeView.getLayoutParams();
            int col=(campuss.size()/2)+((campuss.size()%2==0)?0:1);
            params.height = col*DensityUtil.dip2px(context,35);
            viewHolder.campusTypeView.setLayoutParams(params);

            viewHolder.managerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, CurriculumListActivity.class);

                    intent.putExtra("campusVO",campusVO);

                    startActivity(intent);

                }
            });

//            if ((position==(campusVOs.size()-1))&&(position>1)){
//                viewHolder.buttomView.setVisibility(View.VISIBLE);
//            }else{
//                viewHolder.buttomView.setVisibility(View.GONE);
//            }

            return convertView;
        }
    }

    class CampusTypeAdapter extends BaseAdapter{

        private List<String> datas;
        private LayoutInflater layoutInflater;

        public CampusTypeAdapter(Context context,List<String> datas){
            if (datas==null){
                datas=new ArrayList<>();
            }
            this.datas=datas;
            layoutInflater=LayoutInflater.from(context);
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
            TextView textView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.activity_campus_list_item_class_item,null);
                viewHolder.textView= (TextView) convertView.findViewById(R.id.xq_type_textview);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }


            String data=datas.get(position);
            viewHolder.textView.setText(data);

            return convertView;
        }
    }

}
