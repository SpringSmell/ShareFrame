package com.future.sharelibrary.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.future.sharelibrary.R;
import com.future.sharelibrary.frame.ExitApplication;

/**
 * Created by chris on 2015/12/13.
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        init();
        initView();
        initData();
        initTitle();
        ExitApplication.newInstance().addActivity(this);
        super.onCreate(savedInstanceState);
    }

    public abstract void init();
    public abstract void initView();
    public abstract void initData();
    public abstract void initTitle();

    protected void setBackValid(){
        TextView backView= (TextView) findViewById(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setBackValid(View.OnClickListener onClickListener){
        TextView backView= (TextView) findViewById(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(onClickListener);
    }

    protected void setBackValid(CharSequence content){
        TextView backView= (TextView) findViewById(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        backView.setText(content);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setBackValid(CharSequence content,int icon){
        TextView backView= (TextView) findViewById(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        backView.setText(content);
        backView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(icon),null,null,null);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    protected void setTitleName(CharSequence content){
        TextView titleView= (TextView) findViewById(R.id.titleName);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(content);
    }

    protected void setRightView(CharSequence content, View.OnClickListener onClickListener){
        TextView titleRight=((TextView)findViewById(R.id.titleRight));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText(content);
        titleRight.setOnClickListener(onClickListener);
    }

    protected void setRightView(int icon, View.OnClickListener onClickListener){
        TextView titleRight=((TextView)findViewById(R.id.titleRight));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(icon),null,null,null);
        titleRight.setOnClickListener(onClickListener);
    }

    protected void setRightView(String content, int icon, View.OnClickListener onClickListener){
        TextView titleRight=((TextView)findViewById(R.id.titleRight));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(icon),null,null,null);
        titleRight.setOnClickListener(onClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.newInstance().removeActivity(this);
    }

    protected void showToast(CharSequence content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
