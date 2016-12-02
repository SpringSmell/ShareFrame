package com.future.shareframe.activities;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.future.shareframe.R;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.widgets.RoundProgressBar;

import java.util.Random;

/**
 * @author Chris zou
 * @Date 2016/12/2
 * @modifyInfo1 Zuo-2016/12/2
 * @modifyContent
 */

public class ProgressActivity extends ShareActivity {

    private RoundProgressBar mRoundProgressBar;
    int i = 1;
    private Handler mHandler = new Handler () {

        @Override public void handleMessage ( Message msg ) {

            super.handleMessage ( msg );
            if ( mRoundProgressBar.getValue () >= 100 ) {
                mRoundProgressBar.setValue ( 0 );
            }
            long progress = mRoundProgressBar.getValue () +
                    ( long ) ( new Random ().nextFloat () * 10 );
            mRoundProgressBar.setValue ( progress );
            Log.e ( "Test", "handleMessage: " + progress );
            mHandler.postDelayed ( new Runnable () {

                @Override public void run () {

                    mHandler.sendEmptyMessage ( 0 );
                }
            }, 100 );
        }
    };

    @Override public int resultLayoutResId () {

        return R.layout.activity_progress;
    }

    @Override public void onInitData () {

    }

    @Override public void onBindData ( BaseParentViewHolder holder ) {

        mRoundProgressBar = holder.getView ( R.id.progressBar );
        mRoundProgressBar.setProgress ( 0 );
        mHandler.sendEmptyMessageDelayed ( 0, 1000 );
    }
}
