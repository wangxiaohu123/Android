package com.ykx.organization.storage.vo;

import com.ykx.baselibs.vo.TypeVO;

import java.io.Serializable;
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
 * Created by 2017/3/26.
 * <p>
 * <p>
 * ********************************************************************************
 */



public class TypesVO implements Serializable {

    public TypesVO(){

    }

    private List<TypeVO> typeVOs;

    private String codes;
    private String names;

    public List<TypeVO> getTypeVOs() {
        return typeVOs;
    }

    public void setTypeVOs(List<TypeVO> typeVOs) {
        this.typeVOs = typeVOs;
    }

    public String getCodes() {
        if (typeVOs!=null){
            StringBuffer stringBuffer=new StringBuffer("");
            for (TypeVO typeVO:typeVOs){
                if (stringBuffer.length()<=0){
                    stringBuffer.append(typeVO.getCode());
                }else{
                    stringBuffer.append(",").append(typeVO.getCode());
                }
            }
            return stringBuffer.toString();
        }
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getNames() {
        if (typeVOs!=null){
            StringBuffer stringBuffer=new StringBuffer("");
            for (TypeVO typeVO:typeVOs){
                if (stringBuffer.length()<=0){
                    stringBuffer.append(typeVO.getName());
                }else{
                    stringBuffer.append(",").append(typeVO.getName());
                }
            }
            return stringBuffer.toString();
        }
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
