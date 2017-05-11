package com.ykx.baselibs.pages;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ykx.baselibs.R;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.vo.TypeVO;

import java.util.ArrayList;
import java.util.List;

public class MultiselectListActivity extends BaseActivity {

    protected boolean isMultiselectedFlag=false;

    private ListView typeListView;

    private TypeAdapter typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMultiselectedFlag=isMultiselected();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiselect_list);

        initUI();

    }


    protected boolean isMultiselected(){
        return getIntent().getBooleanExtra("isMultiselectedFlag",false);
    }


    private void initUI(){
        typeListView= (ListView) findViewById(R.id.type_list);
        typeAdapter=new TypeAdapter(this,new ArrayList<TypeVO>());
        typeListView.setAdapter(typeAdapter);
        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypeVO typeVO = (TypeVO) typeAdapter.getItem(position);
                if (isEndFlag(typeVO)&&(isMultiselectedFlag)){
                    typeVO.setCheckFlag(!typeVO.isCheckFlag());
                    typeAdapter.notifyDataSetChanged();
                }else {
                    selectedItemClick(typeVO);
                }
            }
        });
    }

    protected void resetNewTypeVO(List<TypeVO> allTypeVO,List<TypeVO> selectedTypeVO){
        if ((allTypeVO!=null)&&(selectedTypeVO!=null)) {
            for (TypeVO types : selectedTypeVO) {
                for (TypeVO typea : allTypeVO) {
                    if (typea.getName().equals(types.getName())) {
                        typea.setCheckFlag(true);
                        break;
                    }
                }
            }
        }
    }


    protected boolean isEndFlag(TypeVO typeVO){
        return false;
    }

    /**
     * 只有一层activity
     * @return
     */
    protected boolean isOneActivity(){
        return false;
    }

    @Override
    protected void setRightView(LinearLayout rightContentView) {

        if ((getNextDrawable()==null)&&(isMultiselectedFlag)){

            TextView rightview=new TextView(this);
            rightview.setGravity(Gravity.CENTER);
            rightview.setText(getResources().getString(R.string.sys_save));
            rightview.setTextSize(15);
            rightview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
            rightContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    List<TypeVO> selectedvo=typeAdapter.getSelectedTypeVOs();
                    TypeVO.TypeVOs typeVOs=TypeVO.getTypevos();
                    typeVOs.setDatas(selectedvo);
                    Intent intent=new Intent();
                    intent.putExtra("typeVO",typeVOs);
                    if (isOneActivity()){
                        setResult(RESULT_OK,intent);
                        finish();
                    }else{
                        intent.setAction(getIntent().getStringExtra("action"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }


                }
            });

            rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

        }
    }

    /**
     * 强制显示右边图标
     * @return
     */
    protected Drawable getNextDrawable(){
        return null;
    }

    protected void selectedItemClick(TypeVO typeVO){

    }

    protected void refreshList(TypeVO typeVO){
        List<TypeVO> typeVOList= typeAdapter.getTypeVOs();
        for (TypeVO typeVO1 : typeVOList){
            if (typeVO.getName().equals(typeVO1.getName())){
                typeVO1.setCheckFlag(typeVO.isCheckFlag());
                break;
            }
        }

        typeAdapter.notifyDataSetChanged();
    }

    protected void resetData(List<TypeVO> typeVOs){
        typeAdapter.setTypeVOs(typeVOs);
    }


    class TypeAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<TypeVO> typeVOs;
        private Context context;

        public TypeAdapter(Context context, List<TypeVO> typeVOs){

            this.context=context;
            layoutInflater=LayoutInflater.from(context);
            if (typeVOs!=null){
                this.typeVOs=typeVOs;
            }else{
                this.typeVOs=new ArrayList<>();
            }

        }

        public List<TypeVO> getTypeVOs() {
            return typeVOs;
        }

        public void setTypeVOs(List<TypeVO> typeVOs) {
            this.typeVOs = typeVOs;
            this.notifyDataSetChanged();
        }



        public List<TypeVO> getSelectedTypeVOs() {
            List<TypeVO> list=new ArrayList<>();
            if (typeVOs!=null){
                for (TypeVO typeVO:typeVOs){
                    if (typeVO.isCheckFlag()){
                        list.add(typeVO);
                    }
                }
            }
            return list;
        }

        @Override
        public int getCount() {
            return typeVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return typeVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            TextView nameView;
            ImageView checkImageView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder=null;

            if (convertView==null){
                viewHolder=new ViewHolder();

                convertView=layoutInflater.inflate(R.layout.activity_type_list_item,null);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.type_list_item_view);
                viewHolder.checkImageView= (ImageView) convertView.findViewById(R.id.check_imageview);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            TypeVO typeVO=typeVOs.get(position);
            viewHolder.nameView.setText(typeVO.getName());
            Drawable drawable=getNextDrawable();
            if (drawable!=null){
                viewHolder.checkImageView.setImageDrawable(drawable);
                viewHolder.checkImageView.setVisibility(View.VISIBLE);
            }else{
//                viewHolder.checkImageView.setVisibility(View.GONE);
                if (typeVO.isCheckFlag()){
                    viewHolder.checkImageView.setVisibility(View.VISIBLE);
                    viewHolder.checkImageView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_imageview_check));
                }else{
                    viewHolder.checkImageView.setVisibility(View.GONE);
                }
            }



            return convertView;
        }
    }
}
