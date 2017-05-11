package com.ykx.organization.pages.home.operates.empmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.views.SelectedButtomView;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.pages.home.operates.curriculum.CurriculumInfoDesActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.CampusVO;
import com.ykx.organization.storage.vo.EmpVO;
import com.ykx.organization.storage.vo.ItemVO;
import com.ykx.organization.storage.vo.RoleModule;

import java.util.ArrayList;
import java.util.List;

public class AddEmpActivity extends BaseActivity {

    private EditText nameView,phoneView;

    private TextView genderView,zwView,ssxqView,skkmView,jjview,llview,ryview;

    private TypeVO gender;
    private List<TypeVO> zwVOs;


    private int jjFlag=1101;
    private int llFlag=1102;
    private int ryFlag=1103;
    private int campusFlag=1104;
    private int curriculumTypeFalg=1105;
    private int roleTypeFlag=1106;

    private int roleManagerTypeFlag=1107;

    private String jjInfo="";
    private String llInfo="";
    private String ryInfo="";

    private  List<CampusVO> selectedCampusVOs;
    private  List<TypeVO> selectedTypeVOs;
    private String selectedCampusNames;
    private String selectedTypeVOSNames;

    private List<RoleModule> roleModules;

    private EmpVO editEmpVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        editEmpVO= (EmpVO) getIntent().getSerializableExtra("empVO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);

        initUI();
        resetUI();
    }

    private void initUI(){
        nameView= (EditText) findViewById(R.id.name_editview);
        phoneView= (EditText) findViewById(R.id.phone_edittext);
        genderView= (TextView) findViewById(R.id.gender_view);
        zwView= (TextView) findViewById(R.id.zw_view);
        ssxqView= (TextView) findViewById(R.id.ssxq_view);
        skkmView= (TextView) findViewById(R.id.skkm_view);

        jjview= (TextView) findViewById(R.id.jj_view);
        llview= (TextView) findViewById(R.id.ll_view);
        ryview= (TextView) findViewById(R.id.ry_view);

        if (editEmpVO!=null) {
            cshUI();
//            new OperateServers().empDetailInfo(String.valueOf(editEmpVO.getId()), new HttpCallBack<EmpVO>() {
//                @Override
//                public void onSuccess(EmpVO data) {
//                    if (data!=null){
//                        editEmpVO=data;
//                        cshUI();
//                    }
//                }
//                @Override
//                public void onFail(String msg) {}
//            });
        }
    }

    private void cshUI(){
        nameView.setText(editEmpVO.getName());
        phoneView.setText(editEmpVO.getPhone());
        gender = getGender(editEmpVO.getSex());
        genderView.setText(gender.getName());

        zwVOs = changeItemvoToTypevo(editEmpVO.getPositions());
        selectedCampusVOs = editEmpVO.getCampuses();
        selectedTypeVOs = editEmpVO.getCategories();

        ssxqView.setText(getCampusInfo());
        skkmView.setText(getSelectedTypeVOsInfo());
        zwView.setText(getZWInfo());

        if ((editEmpVO.getSummary() != null) && (editEmpVO.getSummary().length() > 0)) {
            jjview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            jjInfo=editEmpVO.getSummary();
        }
        if ((editEmpVO.getResume() != null) && (editEmpVO.getResume().length() > 0)) {
            llview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            llInfo=editEmpVO.getResume();
        }
        if ((editEmpVO.getHonor() != null) && (editEmpVO.getHonor().length() > 0)) {
            ryview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            ryInfo=editEmpVO.getHonor();
        }
    }

    private void resetUI() {
        setUnAbleNull(R.id.name);
        setUnAbleNull(R.id.gender);
        setUnAbleNull(R.id.phone);
        setUnAbleNull(R.id.jobs);
        setUnAbleNull(R.id.add_emp_info_ssxq);
        setUnAbleNull(R.id.role);
    }

    private String getZWInfo(){
        if ((zwVOs!=null)&&(zwVOs.size()>0)){

            StringBuffer stringBuffer=new StringBuffer("");
            for (TypeVO typeVO:zwVOs){
                if (stringBuffer.length()>0){
                    stringBuffer.append(",").append(typeVO.getName());
                }else {
                    stringBuffer.append(typeVO.getName());
                }
            }
            return stringBuffer.toString();
        }
        return getResString(R.string.emp_manager_activity_add_hint1);
    }

    private String getSelectedTypeVOsInfo(){
        StringBuffer stringBuffer=new StringBuffer("");
        if ((selectedTypeVOs!=null)&&(selectedTypeVOs.size()>0)){

            for (TypeVO typeVO:selectedTypeVOs){
                if (stringBuffer.length()>0){
                    stringBuffer.append(",").append(typeVO.getName());
                }else {
                    stringBuffer.append(typeVO.getName());
                }
            }
        }else{
            return getResString(R.string.emp_manager_activity_add_hint1);
        }
        selectedTypeVOSNames=stringBuffer.toString();
        return selectedTypeVOSNames;
    }

    private String getCampusInfo(){
        StringBuffer stringBuffer=new StringBuffer("");
        if ((selectedCampusVOs!=null)&&(selectedCampusVOs.size()>0)){

            for (CampusVO campusVO1:selectedCampusVOs){
                if (stringBuffer.length()>0){
                    stringBuffer.append(",").append(campusVO1.getName());
                }else {
                    stringBuffer.append(campusVO1.getName());
                }
            }
        }else{
            return getResString(R.string.emp_manager_activity_add_hint1);
        }
        selectedCampusNames=stringBuffer.toString();
        return selectedCampusNames;
    }


    @Override
    protected String titleMessage() {
        return getResString(R.string.emp_manager_activity_add_title);
    }

    private List<TypeVO> changeItemvoToTypevo(List<ItemVO> zwitems){
        List<TypeVO> typeVOList=new ArrayList<>();
        if (zwitems!=null) {
            for (ItemVO itemVO : zwitems) {
                typeVOList.add(new TypeVO(itemVO.getName(), itemVO.getId()));
            }
        }
        return typeVOList;
    }

    private TypeVO getGender(String gendervalue){

        ArrayList<TypeVO> datas=getGenders();
        for (TypeVO typeVO:datas){
            if (String.valueOf(typeVO.getCode()).equals(gendervalue)){
                return typeVO;
            }
        }
        return datas.get(0);
    }

    private ArrayList<TypeVO> getGenders(){

        ArrayList<TypeVO> datas=new ArrayList<>();

        datas.add(new TypeVO("男",1));
        datas.add(new TypeVO("女",0));

        return datas;
    }

    public void selectedGenderAction(View view){

        ArrayList<TypeVO> datas=getGenders();

        String gendername="";
        if (gender!=null){
            gendername=gender.getName();
        }

        showOptionWithName(gendername,datas,new  SelectedButtomView.SelectedButtomViewListener(){
            @Override
            public void callBack(boolean isTrue, TypeVO typevo) {
                if (isTrue){
                    gender = typevo;
                    if (gender!=null) {
                        genderView.setText(gender.getName());
                    }
                }
            }
        });
    }

    public void selectedZWAction(View view){
//        ArrayList<TypeVO> datas=new ArrayList<>();
//
//        datas.add(new TypeVO("管理员",1));
//        datas.add(new TypeVO("校长",2));
//        datas.add(new TypeVO("老师",3));
//        datas.add(new TypeVO("咨询师",4));
//        datas.add(new TypeVO("其他",5));
//
//        String zwname="";
//        if (zwVO!=null){
//            zwname=zwVO.getName();
//        }
//
//        showOptionWithName(zwname,datas,new  SelectedButtomView.SelectedButtomViewListener(){
//            @Override
//            public void callBack(boolean isTrue, TypeVO typevo) {
//                if (isTrue){
//                    zwVO = typevo;
//                    if (zwVO!=null) {
//                        zwView.setText(zwVO.getName());
//                    }
//                }
//            }
//        });

        Intent intent=new Intent(this,RoleMultisSelectedActivity.class);
        if (zwVOs!=null){
            TypeVO.TypeVOs typeVOs=TypeVO.getTypevos();
            typeVOs.setDatas(zwVOs);
            intent.putExtra("typeVO",typeVOs);
        }

        startActivityForResult(intent,roleTypeFlag);

    }

    public void selectedSKKMction(View view){

        Intent intent=new Intent(this,FirstMultisSelectedActivity.class);

        startActivityForResult(intent,curriculumTypeFalg);

    }

    public void selectedSSXQAction(View view){

        Intent intent=new Intent(this,MultiCampusSelectedListActivity.class);
        if (selectedCampusVOs!=null){
            CampusVO.CampusList campusList = CampusVO.newCampusList();
            campusList.setDatas(selectedCampusVOs);
            intent.putExtra("CampusList",campusList);

        }
        startActivityForResult(intent,campusFlag);

    }

    public void selectedJJAction(View view){
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        intent.putExtra("message",jjInfo);
        startActivityForResult(intent,jjFlag);
    }

    public void selectedLLAction(View view){
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        intent.putExtra("message",llInfo);
        startActivityForResult(intent,llFlag);
    }
    public void selectedRYAction(View view){
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        intent.putExtra("message",ryInfo);
        startActivityForResult(intent,ryFlag);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            String message=data.getStringExtra("message");
            if (requestCode==jjFlag){
                jjInfo=message;
                jjview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            }else if (requestCode==llFlag){
                llInfo=message;
                llview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            }else if (requestCode==ryFlag){
                ryInfo=message;
                ryview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            }else if(requestCode==campusFlag){
                CampusVO.CampusList  campusVO = (CampusVO.CampusList) data.getSerializableExtra("campusVOs");
                if (campusVO!=null){
                    selectedCampusVOs = campusVO.getDatas();
                    ssxqView.setText(getCampusInfo());
                }

            }else if(requestCode==curriculumTypeFalg){
                TypeVO.TypeVOs typeVOs = (TypeVO.TypeVOs) data.getSerializableExtra("typeVOS");
                if (typeVOs!=null){
                    selectedTypeVOs=typeVOs.getDatas();
                    skkmView.setText(getSelectedTypeVOsInfo());
                }
            }else if(requestCode==roleTypeFlag){
                TypeVO.TypeVOs typeVOs = (TypeVO.TypeVOs) data.getSerializableExtra("typeVO");
                if (typeVOs!=null){
                    zwVOs=typeVOs.getDatas();
                    zwView.setText(getZWInfo());
                }
            }else if(requestCode==roleManagerTypeFlag){
                RoleModule.RoleModuleList roleModuleList = (RoleModule.RoleModuleList) data.getSerializableExtra("roleModules");
                if (roleModuleList!=null){
                    roleModules=roleModuleList.getDatas();
                }
            }
        }

    }

    public void selectedRoleAction(View view){

        Intent intent=new Intent(this,RoleManagerActivity.class);
        String roles=getZWInfo();
        if (getResString(R.string.emp_manager_activity_add_hint1).equals(roles)){
            roles="";
        }
        intent.putExtra("roles",roles);
        intent.putExtra("name",nameView.getText().toString());
        if (roleModules!=null){
            EmpVO newemp=new EmpVO();
            newemp.setPower(roleModules);
            intent.putExtra("empvo",newemp);
        }else{
            intent.putExtra("empvo",editEmpVO);
        }

        startActivityForResult(intent,roleManagerTypeFlag);

    }

    public void addEmpAction(View view){

        String name=nameView.getText().toString();
        String phone=phoneView.getText().toString();

        if (name.length()<=0){
            toastMessage(getResString(R.string.emp_manager_activity_name_toast));
            return;
        }

        if (name.length()>6){
            toastMessage(getResString(R.string.emp_manager_activity_name_max_toast));
            return;
        }

        if (phone.length()<=0){
            toastMessage(getResString(R.string.emp_manager_activity_phone_toast));
            return;
        }

        if (gender==null){
            toastMessage(getResString(R.string.emp_manager_activity_gender_toast));
            return;
        }

        if ((zwVOs!=null)&&(zwVOs.size()>0)){
        }else{
            toastMessage(getResString(R.string.emp_manager_activity_zw_toast));
            return;
        }

        if ((selectedCampusVOs!=null)&&(selectedCampusVOs.size()>0)){
        }else{
            toastMessage(getResString(R.string.emp_manager_activity_campus_selected));
            return;
        }

        String[] positionid=new String[zwVOs.size()];
        for (int i=0;i<zwVOs.size();i++){
            TypeVO typeVO=zwVOs.get(i);
            positionid[i]=String.valueOf(typeVO.getCode());
        }

        String[] campusid=new String[selectedCampusVOs.size()];
        for (int i=0;i<selectedCampusVOs.size();i++){
            CampusVO campusVO=selectedCampusVOs.get(i);
            campusid[i]=String.valueOf(campusVO.getId());
        }

        String[] skkms=new String[0];
        if ((selectedTypeVOs!=null)&&(selectedTypeVOs.size()>0)){
            skkms=new String[selectedTypeVOs.size()];

            for (int i=0;i<selectedTypeVOs.size();i++){
                TypeVO typeVO=selectedTypeVOs.get(i);
                skkms[i]=String.valueOf(typeVO.getCode());
            }
        }

        if ((roleModules!=null)&&(roleModules.size()>0)){
        }else{
            toastMessage(getResString(R.string.emp_manager_activity_roles_selected_toast));
            return;
        }


        showLoadingView();

        if (editEmpVO!=null){

            new OperateServers().editEmp(String.valueOf(editEmpVO.getId()),name, String.valueOf(gender.getCode()), phone, positionid, skkms, campusid, jjInfo, llInfo, ryInfo, roleModules, new HttpCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {

                    hindLoadingView();
                    finish();
                }

                @Override
                public void onFail(String msg) {
                    hindLoadingView();
                }
            });
        }else{
            new OperateServers().addEmp(name, String.valueOf(gender.getCode()), phone, positionid, skkms, campusid, jjInfo, llInfo, ryInfo, roleModules, new HttpCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {

                    hindLoadingView();
                    finish();
                }

                @Override
                public void onFail(String msg) {
                    hindLoadingView();
                }
            });
        }



    }

}
