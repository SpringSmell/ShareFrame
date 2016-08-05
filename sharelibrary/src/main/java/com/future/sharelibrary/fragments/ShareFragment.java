package com.future.sharelibrary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.widgets.LoadingPopupWindow;

/**
 * Created by Administrator on 2016/6/12.
 */
public abstract class ShareFragment extends Fragment {//<VH extends BaseParentViewHolder> 暂未有更好的方式，暂不支持ViewHolder的继承

    private BaseParentViewHolder mViewHolder;
    private LoadingPopupWindow mLoadingPopupWindow;
    private Snackbar mSnackbar;
    /**
     * popupWindow是否可操作
     */
    private boolean isHandle = true;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mViewHolder == null || mViewHolder.rootView == null) {
            mViewHolder = onCreateViewHolder(inflater, container);
            onBindData(mViewHolder);
            onBackPressed();
            initTitle();
        }
//        if(rootView==null){
//            rootView = inflater.inflate(R.layout.app_content, container, false);
//        }
        ViewGroup parent = (ViewGroup) mViewHolder.rootView.getParent();
        if (parent != null)
            parent.removeView(mViewHolder.rootView);
        return mViewHolder.rootView;
    }

    public abstract int resultLayoutId();

    private BaseParentViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.app_content, container, false);
        View childView = inflater.inflate(resultLayoutId(), container, false);
        mViewHolder = new BaseParentViewHolder(rootView);
        ((FrameLayout) mViewHolder.getView(R.id.appContent)).addView(childView);
        return mViewHolder;
    }

    public void initTitle() {
        showTitleBar(true);
        setBackValid();
    }

    public abstract void onBindData(BaseParentViewHolder viewHolder);

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
                    getActivity().finish();
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

    public CharSequence getTitle() {
        TextView titleView = mViewHolder.getView(R.id.titleName);
        return titleView.getText();
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

    protected void showTitleBar(boolean isVisible) {
        if (isVisible) {
            mViewHolder.getView(R.id.titleContent).setVisibility(View.VISIBLE);
        } else {
            mViewHolder.getView(R.id.titleContent).setVisibility(View.GONE);
        }
    }

    public void onBackPressed() {
        mViewHolder.rootView.setFocusable(true);
        mViewHolder.rootView.setFocusableInTouchMode(true);
        mViewHolder.rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mLoadingPopupWindow != null && mLoadingPopupWindow.isShowing() && isHandle) {
                            mLoadingPopupWindow.dismiss();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    private void initPopupWindow() {
        mLoadingPopupWindow = LoadingPopupWindow.LoadingPWBuilder.getInstance(getActivity()).getPopupWindow();
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


    protected void showToast(CharSequence content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
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

    protected void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(String content, View.OnClickListener onClickListener) {
        Snackbar.make(getView(), content, Snackbar.LENGTH_SHORT).setAction("确定", onClickListener).show();
    }
}
