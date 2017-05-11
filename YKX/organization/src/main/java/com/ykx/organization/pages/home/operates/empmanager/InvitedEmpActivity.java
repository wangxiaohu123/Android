package com.ykx.organization.pages.home.operates.empmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.commons.Constant;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;
import com.ykx.organization.app.OrganizationApp;
import com.ykx.organization.libs.ZxingUtils;
import com.ykx.organization.pages.bases.CustomScanActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.ApplicantVO;
import com.ykx.organization.storage.vo.EmpVO;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class InvitedEmpActivity extends BaseActivity {

    private ListView empListView;
    private EmpAdapter empAdapter;
    private EditText shareView;
    private ImageView zxingView;

    private Bitmap ewmBitmap;
    private String ewmurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_emp);

        initUI();
        loadData();
    }

    private void initUI(){
        shareView= (EditText) findViewById(R.id.share_url_view);
        empListView= (ListView) findViewById(R.id.emp_list_view);
        zxingView= (ImageView) findViewById(R.id.zxing_image_view);
        empAdapter=new EmpAdapter(this,null);
        empListView.setAdapter(empAdapter);

    }

    private void loadData(){

        new OperateServers().applyEmpList(new HttpCallBack<ApplicantVO>() {
            @Override
            public void onSuccess(ApplicantVO data) {
                if (data!=null){
                    empAdapter.refres(data.getApplicants());
                    shareView.setText(data.getUrl());

                    try {
                        ewmBitmap=ZxingUtils.createQRCode(data.getUrl(),DensityUtil.dip2px(InvitedEmpActivity.this,150));
                        zxingView.setImageBitmap(ewmBitmap);
                        ewmurl=BitmapUtils.writeBitmapToFile(ewmBitmap, Constant.OTHERS_PATH,"ewm");
                    } catch (WriterException e) {
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.emp_manager_activity_invited_emp_title);
    }


    public void shareInfoAction(View view){

        String shareurl=shareView.getText().toString();
        if ((shareurl!=null)&&(shareurl.length()>0)) {
            String brandname=PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,"暂无");
            if ((brandname!=null)&&(brandname.length()>0)){
            }else{
                brandname="暂无";
            }
            String localfile=null;
            if (view.getId()!=R.id.share_linek_view1){
                localfile=ewmurl;
            }
//            String ewmurl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492592619695&di=d9d17bce0b09d99ea3c9f7f4ad1322d0&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F1603%2F18%2Fc63%2F19365090_1458313871651_mthumb.jpg";
            OrganizationApp.showShare("邀请你加入"+brandname, "邀请你加入"+brandname+"，确认加入请点击>>", shareurl, localfile);
//          customScan();
        }

    }

    private void customScan(){
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                Toast.makeText(this,"扫描成功 ScanResult="+ScanResult,Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ewmBitmap!=null){
            if (!ewmBitmap.isRecycled()){
                ewmBitmap.recycle();
            }
        }
    }

    class EmpAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<ApplicantVO.ApplicantInfo> empVOs;
        private Context context;

        public EmpAdapter(Context context,List<ApplicantVO.ApplicantInfo> empVOs){
            this.context=context;
            this.layoutInflater=LayoutInflater.from(context);
            if (empVOs==null){
                empVOs=new ArrayList<>();
            }
            this.empVOs=empVOs;

        }

        public void refres(List<ApplicantVO.ApplicantInfo> empVOs){
            if (empVOs==null){
                empVOs=new ArrayList<>();
            }
            this.empVOs=empVOs;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) empListView.getLayoutParams();
            layoutParams.height= DensityUtil.dip2px(context,165)*this.empVOs.size();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return empVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return empVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            ImageView headerView;
            TextView nameView;
            TextView phoneView;
            TextView desView;
            TextView rejectView;
            TextView passView;
            TextView stateView;

            View doStateContentView,stateContentView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.view_emp_state_item,null);
                viewHolder.headerView= (ImageView) convertView.findViewById(R.id.photo_view);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.name_textview);
                viewHolder.phoneView= (TextView) convertView.findViewById(R.id.phone_textview);
                viewHolder.desView= (TextView) convertView.findViewById(R.id.des_textview);
                viewHolder.rejectView= (TextView) convertView.findViewById(R.id.emp_unpass);
                viewHolder.passView= (TextView) convertView.findViewById(R.id.emp_pass);
                viewHolder.stateView= (TextView) convertView.findViewById(R.id.emp_pass_status);
                viewHolder.doStateContentView=convertView.findViewById(R.id.emp_do_pass_context_status);
                viewHolder.stateContentView=convertView.findViewById(R.id.emp_pass_context_status);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }


            final ApplicantVO.ApplicantInfo empVO=empVOs.get(position);
            viewHolder.nameView.setText(empVO.getName());
            BaseApplication.application.getDisplayImageOptions(empVO.getPhotoUrl(),viewHolder.headerView);
            viewHolder.phoneView.setText(empVO.getPhone());
            viewHolder.desView.setText(empVO.getRemark());
            if (empVO.getStatus()==0){
                viewHolder.doStateContentView.setVisibility(View.VISIBLE);
                viewHolder.stateContentView.setVisibility(View.GONE);
            }else{
                viewHolder.doStateContentView.setVisibility(View.GONE);
                viewHolder.stateContentView.setVisibility(View.VISIBLE);
                if (empVO.getStatus()==1){
                    viewHolder.stateView.setText(getResString(R.string.emp_manager_activity_invited_emp_share_itme3_cell_ytg));
                    viewHolder.stateView.setTextColor(context.getResources().getColor(R.color.theme_main_background_color));
                }else if (empVO.getStatus()==2){
                    viewHolder.stateView.setText(getResString(R.string.emp_manager_activity_invited_emp_share_itme3_cell_yjj));
                    viewHolder.stateView.setTextColor(context.getResources().getColor(R.color.theme_small_background_color));
                }
            }

            final TextView stateview=viewHolder.stateView;
            final View doStateContentView=viewHolder.doStateContentView;
            final View stateContentView=viewHolder.stateContentView;

            viewHolder.rejectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View view=LayoutInflater.from(InvitedEmpActivity.this).inflate(R.layout.view_operate_emp_manager_refuse,null);

                    final MaterialDialog mMaterialDialog = new MaterialDialog(InvitedEmpActivity.this);
                    mMaterialDialog.setContentView(view);
                    final EditText editText= (EditText) view.findViewById(R.id.reject_message_view);

                    view.findViewById(R.id.cannel_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });
                    view.findViewById(R.id.true_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                            showLoadingView();
                            String message=editText.getText().toString();
                            new OperateServers().applyRefuseEmp(String.valueOf(empVO.getId()),message, new HttpCallBack<Object>() {

                                @Override
                                public void onSuccess(Object data) {
                                    hindLoadingView();
                                    doStateContentView.setVisibility(View.GONE);
                                    stateContentView.setVisibility(View.VISIBLE);
                                    stateview.setText(getResString(R.string.emp_manager_activity_invited_emp_share_itme3_cell_yjj));
                                    stateview.setTextColor(context.getResources().getColor(R.color.theme_small_background_color));
                                }

                                @Override
                                public void onFail(String msg) {
                                    hindLoadingView();
                                }
                            });

                        }
                    });

                    mMaterialDialog.show();
                }
            });

            viewHolder.passView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showLoadingView();
                    new OperateServers().applyPassEmp(String.valueOf(empVO.getId()), new HttpCallBack<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            hindLoadingView();

                            Intent intent=new Intent(InvitedEmpActivity.this,AddEmpActivity.class);
                            EmpVO empVO1=new EmpVO();
                            empVO1.setName(empVO.getName());
                            empVO1.setPhone(empVO.getPhone());
                            empVO1.setId(empVO.getId());
                            intent.putExtra("empVO",empVO1);

                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFail(String msg) {
                            hindLoadingView();
                        }
                    });

                }
            });


            return convertView;
        }
    }
}
