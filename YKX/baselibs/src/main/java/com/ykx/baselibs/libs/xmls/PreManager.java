package com.ykx.baselibs.libs.xmls;

import android.preference.PreferenceManager;

import com.ykx.baselibs.app.BaseApplication;


/**
 * 封装SharedPreference，文件保存等操作
 * 
 * @author Justin Z
 * 
 */
public class PreManager {

//	public static final String USER_INFO_KEY="USER_INFO_KEY";

	public static final String USER_NAME="USER_NAME";
	public static final String PASSWORD="PASSWORD";
	public static final String DEFAULT_BRANDID="DEFAULT_BRANDID";
	public static final String DEFAULT_BRANDNAME="DEFAULT_BRANDNAME";


	private static final PreManager instance = new PreManager();
	private BaseApplication cusApp;

	private PreManager() {
		this.cusApp = BaseApplication.application;
	}

	/**
	 * 单例模式得到本类实例
	 * 
	 * @return
	 */
	public static PreManager getInstance() {
		return instance;
	}


	public void setLoginInfo(String username,String password,String brandid,String brandName){
		saveData(USER_NAME,username);
		saveData(PASSWORD,password);
		saveData(DEFAULT_BRANDID,brandid);
		saveData(DEFAULT_BRANDNAME,brandName);
	}


	public void saveData(String key,String value) {
		PreferenceManager.getDefaultSharedPreferences(cusApp).edit().putString(key, value).commit();
	}


	public String getData(String key,String defalutValue) {
		return PreferenceManager.getDefaultSharedPreferences(cusApp).getString(key, defalutValue);
	}

	


}
