package com.ykx.organization.pages.home.operates.curriculum;

//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;

//import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.pages.bases.BasePicAndMessageInfoActivity;
import com.ykx.organization.R;

public class CurriculumInfoDesActivity extends BasePicAndMessageInfoActivity {

//    private EditText editTextView;
//    private String des;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        des=getIntent().getStringExtra("des");
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_curriculum_info_des);
//
//        initUI();
//    }
//
//    private void initUI(){
//        editTextView= (EditText) findViewById(R.id.bk_des_content);
//        editTextView.setText(des);
//    }
//
    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.sys_activity_title_pic_message);
    }
//
//    @Override
//    protected void setRightView(LinearLayout rightContentView) {
//
//        TextView rightview=new TextView(this);
//        rightview.setGravity(Gravity.CENTER);
//        rightview.setText(getResources().getString(R.string.curriculum_activity_add_title_save));
//        rightview.setTextSize(15);
//        rightview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
//        rightContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message=editTextView.getText().toString();
//                if (message.length()<=0){
//                    toastMessage(getResources().getString(R.string.curriculum_activity_add_info_des_content_bkjj));
//                    return;
//                }
//                Intent intent=new Intent();
//                intent.putExtra("des",message);
//                setResult(RESULT_OK,intent);
//                finish();
//            }
//        });
//
//        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
//
//    }
}
