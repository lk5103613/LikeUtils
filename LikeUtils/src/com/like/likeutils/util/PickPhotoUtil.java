package com.like.likeutils.util;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class PickPhotoUtil {
	public final static int REQUEST_TAKE_PHOTO = 0;
	public final static int REQUEST_FROM_FILE = 1;
	
	private static PickPhotoUtil mInstance;
	private Activity mActivity;
	private ContentResolver mResolver;
	
	private PickPhotoUtil(Activity activity, ContentResolver contentResolver) {
		mResolver = contentResolver;
		mActivity = activity;
	}
	
	public static PickPhotoUtil getInstance(Activity activity, ContentResolver contentResolver) {
		if(mInstance == null)
			mInstance = new PickPhotoUtil(activity, contentResolver);
		return mInstance;
	}
	
	public static Bitmap getResizeBitmap(File file) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);	
		return resizeBmp;
	}
	
	private File getFileByUri(Uri uri) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		final Cursor cursor = mResolver.query(uri,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		final File file = new File(picturePath);
		cursor.close();
		return file;
	}
	
	private Uri getUriByData(Intent data) {
		Uri uri = data.getData();
		if(uri == null) {
			Bundle extras = data.getExtras();
		    Bitmap bitmap = (Bitmap) extras.get("data");
		    uri  = Uri.parse(MediaStore.Images.Media.insertImage(mResolver, bitmap, null, null));
		}
	    return uri;
	}
	
	private File getFileByData(Intent data) {
		Uri uri = getUriByData(data);
		return getFileByUri(uri);
	}
	
	private Bitmap getBitmapByData(Intent data) {
		File file = getFileByData(data);
		return getResizeBitmap(file);
	}
	
	private void takePhoto() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		mActivity.startActivityForResult(intent, REQUEST_TAKE_PHOTO);
	}
	
	private void fromFile() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		mActivity.startActivityForResult(intent, REQUEST_FROM_FILE);
	}

}
