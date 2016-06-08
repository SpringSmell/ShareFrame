package com.future.sharelibrary.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.sharelibrary.R;
import com.future.sharelibrary.frame.ExitApplication;
import com.future.sharelibrary.tools.CommonUtils;
import com.future.sharelibrary.tools.HttpUtils;
import com.future.sharelibrary.widgets.LoadingPopupWindow;

/**
 * Created by Administrator on 2016/6/1.
 */
public abstract class ThemeActivity extends AppCompatActivity {

    protected FrameLayout appContentView;
    protected RelativeLayout titleContent;
    protected Snackbar mSnackbar;
    protected AlertDialog mAlertDialog;
    protected LoadingPopupWindow mLoadingPopupWindow;
    /**
     * popupWindow是否可操作
     */
    protected boolean isHandle = true;
    /**
     * 主布局宽高
     */
    private int contentWidth, contentHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        ExitApplication.newInstance().addActivity(this);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        init();
        appContentView.addView(LayoutInflater.from(this).inflate(layoutResID, appContentView, false));
    }

    private void init() {
        if (appContentView != null) {
            return;
        }
        super.setContentView(R.layout.app_content);
        appContentView = (FrameLayout) findViewById(R.id.appContent);
        titleContent = (RelativeLayout) findViewById(R.id.titleContent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        contentWidth = appContentView.getMeasuredWidth();
        contentHeight = appContentView.getMeasuredHeight();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.pw_loading);
        builder.setInverseBackgroundForced(true);
        mAlertDialog = builder.create();
        mAlertDialog.setCanceledOnTouchOutside(true);
    }

    private void initPopupWindow() {
        mLoadingPopupWindow = LoadingPopupWindow.LoadingPWBuilder.getInstance(this).getPopupWindow();
    }

    protected void alertPopupWindow(){
        this.alertPopupWindow(true);
    }

    /**
     * 弹出加载提示框
     *
     * @param isHandle true：点击及返回键可消失 false:用户不可操作
     */
    protected void alertPopupWindow(boolean isHandle) {
        this.isHandle = isHandle;
        if (mLoadingPopupWindow == null) {
            initPopupWindow();
        }
        mLoadingPopupWindow.setOutsideTouchable(isHandle);
        mLoadingPopupWindow.showPopupWindow(titleContent);
    }

    protected void dismissPopupWindow() {
        if (mLoadingPopupWindow.isShowing()) {
            mLoadingPopupWindow.dismiss();
        }
    }


    protected void setBackValid() {
        ImageView backView = (ImageView) titleContent.findViewById(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setBackValid(View.OnClickListener onClickListener) {
        ImageView backView = (ImageView) titleContent.findViewById(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(onClickListener);
    }


    protected void setBackValid(int icon) {
        ImageView backView = (ImageView) titleContent.findViewById(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        backView.setImageResource(icon);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void setTitle(CharSequence title) {
        TextView titleView = (TextView) titleContent.findViewById(R.id.titleName);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        TextView titleView = (TextView) titleContent.findViewById(R.id.titleName);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(titleId);
    }

    protected void setRightView(CharSequence content, View.OnClickListener onClickListener) {
        TextView titleRight = ((TextView) titleContent.findViewById(R.id.titleRight));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText(content);
        titleRight.setOnClickListener(onClickListener);
    }

    protected void setRightView(int icon, View.OnClickListener onClickListener) {
        TextView titleRight = ((TextView) titleContent.findViewById(R.id.titleRight));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(icon), null, null, null);
        titleRight.setOnClickListener(onClickListener);
    }

    protected void setRightView(String content, int icon, View.OnClickListener onClickListener) {
        TextView titleRight = ((TextView) titleContent.findViewById(R.id.titleRight));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(icon), null, null, null);
        titleRight.setOnClickListener(onClickListener);
    }

    protected void showTitle(boolean isVisible) {
        if (isVisible) {
            titleContent.setVisibility(View.VISIBLE);
        } else {
            titleContent.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.newInstance().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (mLoadingPopupWindow != null && mLoadingPopupWindow.isShowing()&&isHandle) {
            mLoadingPopupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }

    protected void showToast(CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(CharSequence content) {
        this.showSnackbar(appContentView,content);
    }

    protected void showSnackbar(CharSequence content, CharSequence actionTxt, View.OnClickListener onClickListener) {
        mSnackbar=Snackbar.make(appContentView, content, Snackbar.LENGTH_SHORT).setAction(actionTxt, onClickListener);
        mSnackbar.show();
    }

    protected void showSnackbar(View parentView, CharSequence content) {
        mSnackbar=Snackbar.make(parentView, content, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }


}
