package com.future.sharelibrary.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/12.
 */
public abstract class BaseFragment extends Fragment {

    protected void showToast(String content){
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(String content,View.OnClickListener onClickListener){
        Snackbar.make(getView(),content,Snackbar.LENGTH_SHORT).setAction("确定",onClickListener).show();
    }
}
