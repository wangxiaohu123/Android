package com.ykx.baselibs.libs.sortlistview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ykx.baselibs.R;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.vo.AreaCode;
import com.ykx.baselibs.vo.SortVO;


public abstract class BaseSortListActivity extends BaseActivity {

	private final String default_location_message="正在定位中...";

	private SortModel myLocationSortModel;

	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	private PinyinComparator pinyinComparator;

	private SortVO sortVO;

	private TextView mylocationview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		sortVO= (SortVO) getIntent().getSerializableExtra("datas");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort_list);
		initViews();
	}

	@Override
	protected String titleMessage() {

		if (sortVO!=null){

			return sortVO.getTitleName();
		}

		return super.titleMessage();
	}

	private void initViews() {
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position==0){
					SortModel sortModel =myLocationSortModel;
					if ((sortModel!=null)&&(sortModel.getCode()!=-1)) {
						Intent intent = new Intent();
						String action = getIntent().getStringExtra("actionname");
						intent.setAction(action);
						intent.putExtra("sortModel", sortModel);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}else{
						toastMessage(default_location_message);
					}
				}else {
					SortModel sortModel = (SortModel) adapter.getItem(position - 1);

					List<AreaCode> areaCodes = sortModel.getAreaCodes();
					if ((areaCodes != null) && (areaCodes.size() > 0)) {
						Context context=getSortListActivity();
						Intent intent = new Intent(context, context.getClass());
						SortVO sortVO = new SortVO();
						sortVO.setAreaCodes(areaCodes);
						sortVO.setTitleName(sortModel.getName());
						intent.putExtra("datas", sortVO);
						String action = getIntent().getStringExtra("actionname");
						intent.putExtra("actionname", action);
						startActivity(intent);
					} else {

						Intent intent = new Intent();
						String action = getIntent().getStringExtra("actionname");
						intent.setAction(action);
						intent.putExtra("sortModel", sortModel);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

						startActivity(intent);
					}
				}
			}
		});
		sortListView.addHeaderView(createHeaderView());
		
//		SourceDateList = filledData(getResources().getStringArray(R.array.date));
		SourceDateList=filledData(getListData());

		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		
		
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	protected void endClickItem(){

	}

	protected abstract Context getSortListActivity();

	private View createHeaderView(){
		View view= LayoutInflater.from(this).inflate(R.layout.view_mylocation,null);
		mylocationview= (TextView) view.findViewById(R.id.mylocation_view);
		mylocationview.setText(default_location_message);
		return view;
	}

	protected void getMyLocaltion(String address,Integer adcode){
		if ((address!=null)&&(adcode!=null)) {
			SortModel sortModel = new SortModel();
			sortModel.setName(address);
			sortModel.setCode(adcode);
			mylocationview.setText(sortModel.getName());
			myLocationSortModel = sortModel;
		}
	}



	private List<SortModel> getListData(){

		return changeAreaCodeToSortModel(sortVO.getAreaCodes());
	}

	private List<SortModel> changeAreaCodeToSortModel(List<AreaCode> areaCodes){

		List<SortModel> mSortList = new ArrayList<>();
		for (AreaCode areaCode:areaCodes){
			SortModel sortModel=new SortModel();
			sortModel.setName(areaCode.getName());
			sortModel.setCode(areaCode.getAdcode());
			sortModel.setAreaCodes(areaCode.getAreas());

			mSortList.add(sortModel);
		}
		return mSortList;
	}

	private List<SortModel> filledData(List<SortModel> date){
		List<SortModel> mSortList = new ArrayList<>();
		
		for(int i=0; i<date.size(); i++){
			SortModel oldmodel=date.get(i);

			SortModel sortModel = new SortModel();
			sortModel.setName(oldmodel.getName());
			String pinyin = characterParser.getSelling(oldmodel.getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			sortModel.setCode(oldmodel.getCode());
			sortModel.setAreaCodes(oldmodel.getAreaCodes());
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}

	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}

		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
}
