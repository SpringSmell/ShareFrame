package com.future.sharelibrary.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.frame.ExitApplication;
import com.future.sharelibrary.function.SystemBarTintManager;
import com.future.sharelibrary.widgets.LoadingPopupWindow;

/**
 * Created by Administrator on 2016/6/1.
 */
public abstract class ThemeActivity extends AppCompatActivity {

    private Snackbar mSnackbar;
    private AlertDialog mAlertDialog;
    private LoadingPopupWindow mLoadingPopupWindow;
    /**
     * popupWindow是否可操作
     */
    private boolean isHandle = true;
    /**
     * 主布局宽高
     */
    protected int contentWidth, contentHeight;
    /**
     * 基础holder，没有更好的传入方式，暂不支持扩展
     */
    private BaseParentViewHolder mViewHolder;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        ExitApplication.newInstance().addActivity(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        init();
        FrameLayout appContent = mViewHolder.getView(R.id.appContent);
        appContent.addView(view);
    }

    private void init() {
        if (mViewHolder != null) {
            return;
        }
        mViewHolder = OnCreateViewHolder(R.layout.app_content);
        super.setContentView(mViewHolder.rootView);
    }

    public BaseParentViewHolder OnCreateViewHolder(int resId) {
        mViewHolder = new BaseParentViewHolder(LayoutInflater.from(this).inflate(resId, null));
        return mViewHolder;
    }

    public void setStatusTintColor(int color){
        tintManager.setStatusBarTintColor(color);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        contentWidth = mViewHolder.getView(R.id.appContent).getMeasuredWidth();
        contentHeight = mViewHolder.getView(R.id.appContent).getMeasuredHeight();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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

    protected void alertPopupWindow() {
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
        mLoadingPopupWindow.showPopupWindow(mViewHolder.rootView);
    }

    protected void dismissPopupWindow() {
        if (mLoadingPopupWindow.isShowing()) {
            mLoadingPopupWindow.dismiss();
        }
    }
    protected void setBackValid(boolean isVisible) {
        if(isVisible){
            this.setBackValid(0, null);
        }else{
            mViewHolder.getView(R.id.titleLeft).setVisibility(View.GONE);
        }

    }
    protected void setBackValid() {
        this.setBackValid(0, null);
    }

    protected void setBackValid(int icon) {
        this.setRightView(icon, null);
    }

    protected void setBackValid(int icon, View.OnClickListener onClickListener) {
        ImageView backView = mViewHolder.getView(R.id.titleLeft);
        backView.setVisibility(View.VISIBLE);
        if (icon != 0)
            backView.setImageResource(icon);
        if (onClickListener == null)
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            };
        backView.setOnClickListener(onClickListener);
    }

    public void setTitle(int titleResId) {
        this.setTitle(getString(titleResId));
    }

    public void setTitle(CharSequence title) {
        TextView titleView = mViewHolder.getView(R.id.titleName);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);
    }

    protected void setRightView(CharSequence content, View.OnClickListener onClickListener) {
        this.setRightView(content, 0, onClickListener);
    }

    protected void setRightView(int icon, View.OnClickListener onClickListener) {
        this.setRightView("", icon, onClickListener);
    }

    protected void setRightView(CharSequence content, int icon, View.OnClickListener onClickListener) {
        TextView titleRight = mViewHolder.getView(R.id.titleRight);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText(content);
        if (icon != 0)
            titleRight.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(icon), null);
        titleRight.setOnClickListener(onClickListener);
    }

    protected void showTitle(boolean isVisible) {
        if (isVisible) {
            mViewHolder.getView(R.id.titleContent).setVisibility(View.VISIBLE);
        } else {
            mViewHolder.getView(R.id.titleContent).setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mLoadingPopupWindow != null && mLoadingPopupWindow.isShowing() && isHandle) {
            mLoadingPopupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }

    protected void showToast(CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(CharSequence content) {
        this.showSnackbar(mViewHolder.rootView, content);
    }

    protected void showSnackbar(CharSequence content, CharSequence actionTxt, View.OnClickListener onClickListener) {
        mSnackbar = Snackbar.make(mViewHolder.rootView, content, Snackbar.LENGTH_SHORT).setAction(actionTxt, onClickListener);
        mSnackbar.show();
    }

    protected void showSnackbar(View parentView, CharSequence content) {
        mSnackbar = Snackbar.make(parentView, content, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    public BaseParentViewHolder getViewHolder() {
        return this.mViewHolder;
    }

    public RelativeLayout getTitleView() {
        return this.mViewHolder.getView(R.id.titleContent);
    }

    public <T> T getMainView() {
        return (T) this.mViewHolder.rootView;
    }

    public void setBackGround(int id){
        mViewHolder.rootView.setBackgroundResource(id);
    }
    public void setBackGroundColor(int id){
        mViewHolder.rootView.setBackgroundColor(id);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackGround(Drawable drawable){
        mViewHolder.rootView.setBackground(drawable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.newInstance().removeActivity(this);
        this.mViewHolder.onDestroy();
    }

}
