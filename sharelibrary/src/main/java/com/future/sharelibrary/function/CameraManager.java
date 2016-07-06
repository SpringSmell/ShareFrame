package com.future.sharelibrary.function;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * Created by Administrator on 2016/6/8.
 */
public class CameraManager {

    private static CameraManager instance;
    public static final int PHOTO_ALBUM=0x1;
    public static final int CAMERA=0x2;

    public static CameraManager getInstance(){
        if(instance==null){
            instance=new CameraManager();
        }
        return instance;
    }

    /**
     * 调用系统相机
     * @param activity
     */
    public void goCamera(Activity activity){
        /**
         * 调用系统相机，指定图片保存路径
         */
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        mImgPath = mRootPath + System.currentTimeMillis() + ".jpg";
//        Uri uri = Uri.fromFile(new File(mImgPath));
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, CAMERA);
    }

    /**
     * 调用系统相册
     * @param activity
     */
    public void goPhotoAlbum(Activity activity){
        Intent intent_album = new Intent(Intent.ACTION_PICK, null);
        intent_album.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent_album, PHOTO_ALBUM);
    }
}
