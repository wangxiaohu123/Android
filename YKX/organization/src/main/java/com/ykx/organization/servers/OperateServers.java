package com.ykx.organization.servers;


import com.ykx.baselibs.https.BaseHttp;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.RequestMethod;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.storage.vo.AgenciesVO;
import com.ykx.organization.storage.vo.ApplicantVO;
import com.ykx.organization.storage.vo.BrandInfoVO;
import com.ykx.organization.storage.vo.CampusCoursVO;
import com.ykx.organization.storage.vo.CampusInfo;
import com.ykx.organization.storage.vo.CampusVO;
import com.ykx.organization.storage.vo.CurriculumVO;
import com.ykx.organization.storage.vo.EmpVO;
import com.ykx.organization.storage.vo.OraganizationVO;
import com.ykx.organization.storage.vo.PositionVO;
import com.ykx.organization.storage.vo.RoleModule;
import com.ykx.organization.storage.vo.TeacherVO;
import com.ykx.organization.storage.vo.TimeVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

public class OperateServers extends BaseHttp {


    /**
     * 获取机构类型列表
     * @param code
     * @param callBack
     */
    public void getOrganizationType(String code,HttpCallBack<List<TypeVO>> callBack){

        String serverrul="v1/com/sub-category";

        Map<String, String> params=new HashMap<>();
        params.put("code",code);

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端-注册机构
     * @param organzationName
     * @param typeCodes
     * @param uploadfiles
     * @param callBack
     */
    public void regiestOrganization(String organzationName, String typeCodes, LinkedHashMap<String, String> uploadfiles, HttpCallBack<OraganizationVO> callBack){

        String serverrul="v1/agency";

        Map<String, String> params=new HashMap<>();
        params.put("name",organzationName);
        params.put("type",typeCodes);
        params.putAll(uploadfiles);

        doTast(RequestMethod.POST,serverrul,params,callBack);
    }

    /**
     * 新建校区
     * @param name
     * @param adcode
     * @param lat
     * @param lng
     * @param address
     * @param contact
     * @param callBack
     */
    public void addCampus(String photos,String name,String adcode,String lat,String lng,String address,String contact,HttpCallBack<CampusVO> callBack){

        String serverrul="v1/agency/operation/campus/add/campus";

        Map<String, String> params=new HashMap<>();
        params.put("name",name);
        params.put("adcode",String.valueOf(Double.valueOf(adcode).intValue()));
        params.put("lat",lat);
        params.put("lng",lng);
        params.put("photo",photos);
        params.put("address",address);
        params.put("contact",contact);

        doTast(RequestMethod.POST,serverrul,params,callBack);
    }

    /**
     * B端-校区-编辑
     * @param name
     * @param adcode
     * @param lat
     * @param lng
     * @param address
     * @param contact
     * @param callBack
     */
    public void editCampus(String photos,String campusId,String name,String adcode,String lat,String lng,String address,String contact,HttpCallBack<CampusVO> callBack){
        String serverrul="v1/agency/operation/campus/edit/campus/"+campusId;

        Map<String, String> params=new HashMap<>();
        params.put("name",name);
        params.put("adcode",String.valueOf(Double.valueOf(adcode).intValue()));
        params.put("lat",lat);
        params.put("lng",lng);
        params.put("photo",photos);
        params.put("address",address);
        params.put("contact",contact);

        doTast(RequestMethod.PUT,serverrul,params,callBack);
    }


    /**
     * B端-校区-删除
     * @param callBack
     */
    public void deleteCampusDatas(String campusId,HttpCallBack<String> callBack){

        String serverrul="v1/agency/operation/campus/drop/campus/"+campusId;

        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.DELETE,serverrul,params,callBack);
    }

    /**
     * B端-校区-详情
     * @param campusId
     * @param callBack
     */
    public void detailCampusDatas(String campusId,HttpCallBack<CampusVO> callBack){

        String serverrul="v1/agency/operation/campus/view/campus/"+campusId;

        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端-校区-列表
     * @param callBack
     */
    public void getCampusDatas(int pageIndex,HttpCallBack<CampusInfo> callBack){

        String serverrul="v1/agency/operation/campus/view/campuses";

        Map<String, String> params=new HashMap<>();
        params.put("page",String.valueOf(pageIndex));
        params.put("per_page","100");

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端-班课-校区选择列表
     * @param callBack
     */
    public void getAllCampusDatas(HttpCallBack<List<CampusVO>> callBack){

        String serverrul="v1/agency/teaching/course/view/campus-options";

        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }


    /**
     * 校区班课数目验证
     * @param callBack
     */
    public void getCampusCoursCount(HttpCallBack<CampusCoursVO> callBack){
        String serverrul="v1/agency/operation/campus/view/count";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }



    /** GET /v1/agency/teaching/course/view/count
     * 班课数目验证
     * @param callBack
     */
    public void getCourseCount(HttpCallBack<CampusCoursVO> callBack){
        String serverrul="v1/agency/teaching/course/view/count";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端-班课-新建
     * @param campusid
     * @param name
     * @param cate
     * @param summary
     * @param photo
     * @param person
     * @param amount
     * @param start
     * @param end
     * @param price
     * @param callBack
     */
    public void addCurriculumInfo(String sftime,String style,String campusid,String name,String cate,String summary,String photo,String person,String amount,String start,String end,String price,HttpCallBack<CurriculumVO> callBack){
        String serverrul="v1/agency/teaching/course/add/course";

        Map<String, String> params=new HashMap<>();
        params.put("campus_id",campusid);
        params.put("name",name);
        params.put("cate",cate);
        params.put("summary",summary);
        params.put("photo",photo);
        params.put("person",person);
        params.put("amount",amount);
        params.put("start_date",start);
        params.put("end_date",end);
        params.put("price",price);
        params.put("style",style);
        params.put("times",sftime);

        doTast(RequestMethod.POST,serverrul,params,callBack);
    }

    /**
     * B端-班课-编辑
     * @param curriculumId
     * @param sftime
     * @param style
     * @param campusid
     * @param name
     * @param cate
     * @param summary
     * @param photo
     * @param person
     * @param amount
     * @param start
     * @param end
     * @param price
     * @param callBack
     */
    public void editCurriculumInfo(String curriculumId,String sftime,String style,String campusid,String name,String cate,String summary,String photo,String person,String amount,String start,String end,String price,HttpCallBack<CurriculumVO> callBack){
        String serverrul="v1/agency/teaching/course/edit/course/"+curriculumId;
        Map<String, String> params=new HashMap<>();
        params.put("campus_id",campusid);
        params.put("name",name);
        params.put("cate",cate);
        params.put("summary",summary);
        params.put("photo",photo);
        params.put("person",person);
        params.put("amount",amount);
        params.put("start_date",start);
        params.put("end_date",end);
        params.put("price",price);
        params.put("style",style);
        params.put("times",sftime);

        doTast(RequestMethod.PUT,serverrul,params,callBack);
    }


    /**
     * B端-校区-班课列表
     * @param curriculumId
     * @param callBack
     */
    public void getCurriculumWithCampusId(String curriculumId,HttpCallBack<List<CurriculumVO>> callBack){

//        String serverrul="v1/agency/teaching/course/view/campus/"+curriculumId+"/courses";
        String serverrul="v1/agency/operation/campus/view/campus/"+curriculumId+"/courses";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);

    }

    /**
     * B端-保存上课老师
     * @param courseId
     * @param callBack
     */
    public void saveCurriculumTeacherWithCourseId(String courseId, List<String> teacherIds, HttpCallBack<Object> callBack){

        String serverrul="v1/agency/teaching/course/edit/course/"+courseId+"/teachers";
        Map<String, Object> params=new HashMap<>();
        params.put("staffId",teacherIds);

        doJSONTast(RequestMethod.POST,serverrul,params,callBack);

    }

    /**
     * B端-教学-班课列表
     * @param callBack
     */
    public void getAllCurriculum(HttpCallBack<List<CurriculumVO>> callBack){

        String serverrul="v1/agency/teaching/course/view/courses";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);

    }

    /**
     * B端-教学-班课详情
     * @param courseId
     * @param callBack
     */
    public void getCurriculum(String courseId,HttpCallBack<CurriculumVO> callBack){
        String serverrul="v1/agency/teaching/course/view/course/"+courseId;
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);

    }

    /**
     * B端-删除班课
     * @param callBack
     */
    public void deleteCurriculum(String curriculumId,HttpCallBack<String> callBack){

        String serverrul="v1/agency/teaching/course/drop/course/"+curriculumId;
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.DELETE,serverrul,params,callBack);

    }

    /**
     * B端-班课-上课时间规则-列表 显示/隐藏 显示操作 展开操作
     * @param curriculumId
     * @param callBack
     */
    public void getCurriculumSetTimeList(String curriculumId,HttpCallBack<List<TimeVO>> callBack){

        String serverrul="v1/agency/teaching/course/view/course/"+curriculumId+"/timerules";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端-班课-上课时间规则-新建
     * @param curriculumId
     * @param timeVOs
     * @param callBack
     */
    public void curriculumTimeSet(String curriculumId, List<TimeVO> timeVOs, HttpCallBack<List<TimeVO>> callBack){

        String serverrul="v1/agency/teaching/course/edit/course/"+curriculumId+"/timerules";
        Map<String, Object> params=new HashMap<>();

        List<Map<String,String>> datas=new ArrayList<>();
        for (TimeVO timeVO : timeVOs){
            Map<String,String> map=new HashMap<>();
            map.put("id",String.valueOf(timeVO.getId()));
            map.put("repeat_mode",timeVO.getRepeat_mode());
            map.put("repeat_at",timeVO.getRepeat_at());
            map.put("start_time",timeVO.getStart_time()+":00");
            map.put("end_time",timeVO.getEnd_time()+":00");
            map.put("start_date",timeVO.getStart_date());
            map.put("end_date",timeVO.getEnd_date());

            datas.add(map);
        }

        params.put("timerules",datas);

        params.put("remark",String.valueOf(System.currentTimeMillis()));

        doJSONTast(RequestMethod.POST,serverrul,params,callBack);
    }

    /**
     * B端-班课-上课时间规则-删除
     * @param curriculumId
     * @param timerruleId
     * @param callBack
     */
    public void deleteCurriculumTime(String curriculumId,String timerruleId, HttpCallBack<String> callBack){

        String serverrul="v1/agency/teaching/course/edit/course/"+curriculumId+"/timerule/"+timerruleId;

        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.DELETE,serverrul,params,callBack);
    }

    /**
     * B端-班课-是否接受线上报名
     * @param curriculumId
     * @param callBack
     */
    public void curriculumSuportOnlinePay(String curriculumId,HttpCallBack<String> callBack){
        String serverrul="v1/agency/teaching/course/edit/course/"+curriculumId+"/online";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.PATCH,serverrul,params,callBack);
    }


    /**
     * B端-品牌管理-品牌基础信息
     * @param callBack
     */
    public void brandInfo(HttpCallBack<BrandInfoVO> callBack){
        String serverrul="v1/agency/operation/brand/view";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /** POST /v1/agency/operation/brand/edit
     * B端-品牌管理-品牌基础信息 save or update
     * @param name
     * @param logo
     * @param teaching
     * @param team
     * @param honour
     * @param environment
     * @param operation
     * @param callBack
     */
    public void saveOrUpdateBrandInfo(String name,String logo,String teaching,String team,String honour,String environment,String operation,HttpCallBack<BrandInfoVO.Brand> callBack){
        String serverrul="v1/agency/operation/brand/edit";
        Map<String, String> params=new HashMap<>();
        params.put("name",name);
        params.put("logo",logo);
        params.put("teaching",teaching);
        params.put("team",team);
        params.put("honour",honour);
        params.put("environment",environment);
        params.put("operation",operation);


        doTast(RequestMethod.POST,serverrul,params,callBack);
    }
    /**
     * B端-品牌管理-品牌认证信息
     * @param callBack
     */
    public void brandAutoInfo(HttpCallBack<String> callBack){
        String serverrul="v1/agency/branding";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /** POST /v1/agency/operation/brand/add
     * B端-品牌管理-品牌认证信息 save or update
     * @param name
     * @param logo
     * @param trademark
     * @param number
     * @param certificate
     * @param attorney
     * @param operation
     * @param phone
     * @param tel
     * @param callBack
     */
    public void saveOrUpdateBrandAutoInfo(String name,String logo,String trademark,String number,String certificate,String attorney,String operation,String phone,String tel,HttpCallBack<BrandInfoVO.Branding> callBack){
        String serverrul="v1/agency/operation/brand/add";
        Map<String, String> params=new HashMap<>();
        params.put("name",name);
        params.put("logo",logo);
        params.put("trademark",trademark);
        params.put("number",number);
        params.put("certificate",certificate);
        params.put("attorney",attorney);
        params.put("linkman",operation);
        params.put("phone",phone);
        params.put("tel",tel);


        doTast(RequestMethod.POST,serverrul,params,callBack);
    }

    /** GET /v1/agency/operation/brand/add/attorney
     * B端-品牌管理-授权书模板邮件
     * @param email
     * @param callBack
     */

    public void brandSendEmail(String email,HttpCallBack<String> callBack){
        String serverrul="v1/agency/operation/brand/add/attorney";
        Map<String, String> params=new HashMap<>();
        params.put("email",email);

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }


    /**
     * 获取教师列表  GET /v1/agency/teaching/course/view/teachers
     * @param callBack
     */
    public void getTeachers(HttpCallBack<List<TeacherVO>> callBack){
        String serverrul="v1/agency/teaching/course/view/teachers";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }


    /** GET /v1/agency/operation/staff/view/staff
     * 查询该品牌下所有员工
     * @param callBack
     */
    public void getEmpsList(HttpCallBack<List<EmpVO>> callBack){
        String serverrul="v1/agency/operation/staff/view/staff";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }


    /**  GET /v1/agency/operation/staff/view/position-options
     * 获取职位列表
     * @param callBack
     */
    public void getPerfList(HttpCallBack<List<PositionVO>> callBack){
        String serverrul="v1/agency/operation/staff/view/position-options";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }
    /** POST /v1/agency/operation/staff/add/staff
     * 添加员工
     * @param name 姓名
     * @param sex 性别0女1男
     * @param phone 手机
     * @param positionId 职位
     * @param categoryId  授课科目
     * @param campusId 所属校区
     * @param summary 简介
     * @param resume  履历
     * @param honor 荣誉
     * @param role
     * @param callBack
     */
    public void addEmp(String name,String sex,String phone,String[] positionId,String[] categoryId,String[] campusId,String summary,String resume,String honor,List<RoleModule> role,HttpCallBack<Object> callBack){
        String serverrul="v1/agency/operation/staff/add/staff";
        Map<String, Object> params=new HashMap<>();
        params.put("name",name);
        params.put("sex",sex);
        params.put("phone",phone);
        params.put("positionId",positionId);
        params.put("categoryCode",categoryId);
        params.put("campusId",campusId);
        params.put("summary",summary);
        params.put("resume",resume);
        params.put("honor",honor);

        params.put("power",RoleModule.getMaps(role));

        doJSONTast(RequestMethod.POST,serverrul,params,callBack);
    }
    /** DELETE /v1/agency/operation/staff/drop/staff/{id}
     * 删除员工
     * @param callBack
     */
    public void deleteEmpWithEmpId(String empId,HttpCallBack<String> callBack){
        String serverrul="v1/agency/operation/staff/drop/staff/"+empId;
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.DELETE,serverrul,params,callBack);
    }
    /** GET /v1/agency/operation/staff/view/staff/{id}
     * 员工详情
     * @param callBack
     */
    public void empDetailInfo(String empId,HttpCallBack<EmpVO> callBack){
        String serverrul="v1/agency/operation/staff/view/staff/"+empId;
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /** PUT /v1/agency/operation/staff/edit/staff/{id}
     * 编辑员工
     * @param name 姓名
     * @param sex 性别0女1男
     * @param phone 手机
     * @param positionId 职位
     * @param categoryId  授课科目
     * @param campusId 所属校区
     * @param summary 简介
     * @param resume  履历
     * @param honor 荣誉
     * @param role
     * @param callBack
     */
    public void editEmp(String id,String name,String sex,String phone,String[] positionId,String[] categoryId,String[] campusId,String summary,String resume,String honor,List<RoleModule> role,HttpCallBack<Object> callBack){
        String serverrul="v1/agency/operation/staff/edit/staff/"+id;
        Map<String, Object> params=new HashMap<>();
//        params.put("id",id);
        params.put("name",name);
        params.put("sex",sex);
        params.put("phone",phone);
        params.put("positionId",positionId);
        params.put("categoryCode",categoryId);
        params.put("campusId",campusId);
        params.put("summary",summary);
        params.put("resume",resume);
        params.put("honor",honor);

        params.put("power",RoleModule.getMaps(role));

        doJSONTast(RequestMethod.PUT,serverrul,params,callBack);
    }

    /** GET /v1/agency/operation/staff/view/applicants
     * 申请人列表
     * @param callBack
     */
    public void applyEmpList(HttpCallBack<ApplicantVO> callBack){
        String serverrul="v1/agency/operation/staff/view/applicants";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /** PUT /v1/agency/operation/staff/edit/applicant/{id}/pass
     * 审核申请人 通过
     * @param empId
     * @param callBack
     */
    public void applyPassEmp(String empId,HttpCallBack<Object> callBack){
        String serverrul="v1/agency/operation/staff/edit/applicant/"+empId+"/pass";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.PUT,serverrul,params,callBack);
    }


    /** PUT /v1/agency/operation/staff/edit/applicant/{id}/refuse
     * 拒绝申请人
     * @param empId
     * @param callBack
     */
    public void applyRefuseEmp(String empId,String message,HttpCallBack<Object> callBack){
        String serverrul="v1/agency/operation/staff/edit/applicant/"+empId+"/refuse";
        Map<String, String> params=new HashMap<>();
        params.put("reason",message);

        doTast(RequestMethod.PUT,serverrul,params,callBack);
    }
    /** GET /v1/agency/operation/staff/view/powers
     * 获取权限列表
     * @param callBack
     */
    public void getStaffRoles(HttpCallBack<List<RoleModule>> callBack){
        String serverrul="v1/agency/operation/staff/view/powers";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * 获取我的品牌列表
     * @param callBack
     */
    public void getMybrands(HttpCallBack<List<AgenciesVO>> callBack){
        String serverrul="v1/agency/my/brands";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

}
