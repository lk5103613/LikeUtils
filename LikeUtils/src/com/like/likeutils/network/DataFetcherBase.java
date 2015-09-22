package com.like.likeutils.network;

import java.util.Map;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.like.likeutils.R;

public class DataFetcherBase {
	
	private int mDefaultImgResId;
	private int mErrorImgResId;
	private ErrorListener mDefaultErrorListener;
	
	private RequestQueue mQueue;
	private ImageLoader mImgLoader;
	private Context mContext;
	
	protected DataFetcherBase(Context context) {
		this.mContext = context;
		mQueue = MyNetworkUtil.getInstance(context).getRequestQueue();
		mImgLoader = MyNetworkUtil.getInstance(context).getImageLoader();
		mDefaultImgResId = R.drawable.default_img;
		mErrorImgResId = R.drawable.default_img;
		mDefaultErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError err) {
				if(err != null) {
					if(err.networkResponse != null){
						int statusCode = err.networkResponse.statusCode;
						if(statusCode >= 400 && statusCode < 500) {
							Toast.makeText(mContext, "请求错误", Toast.LENGTH_SHORT).show();
						} else if(statusCode >= 500) {
							Toast.makeText(mContext, "服务器内部错误", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext, "访问出现错误，请检查网络", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		};
	}
	
	public void setDefaultImgResId(int imgResId) {
		this.mDefaultImgResId = imgResId;
	}
	
	public void setErrorImgResId(int errorResId) {
		this.mErrorImgResId = errorResId;
	}
	
	public void setDefaultErrorListener(ErrorListener errorListener) {
		this.mDefaultErrorListener = errorListener;
	}
	
	protected void fetchData(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		url = NetParamGenerator.getUrlWithoutParams(url);
		MyRequest request = new MyRequest(method, url, params, listener, errorListener);
		mQueue.add(request);
	}
	
	protected <T> void fetchData (int method, String url, Map<String, String> params, boolean isGeneric, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		url = NetParamGenerator.getUrlWithoutParams(url);
		MyConvertRequest<T> request = new MyConvertRequest<T>(method, url, isGeneric, clazz, listener, errorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(String url, Map<String, String> params, Listener<String> listener) {
		url = NetParamGenerator.getUrlWithoutParams(url);
		MyRequest request = new MyRequest(Method.POST, url, params, listener, mDefaultErrorListener);
		mQueue.add(request);
	}
	
	protected <T> void fetchData(String url, Map<String, String> params, boolean isGeneric, Class<T> clazz, Listener<T> listener) {
		url = NetParamGenerator.getUrlWithoutParams(url);
		MyConvertRequest<T> request = new MyConvertRequest<T>(Method.POST, url, isGeneric, clazz, listener, mDefaultErrorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(int method, String url, Listener<String> listener, ErrorListener errorListener, String...params) {
		if(params.length != 0)
			url = NetParamGenerator.getUrlWithParams(url, params);
		MyRequest request = new MyRequest(method, url, listener, errorListener);
		mQueue.add(request);
	}
	
	protected <T> void fetchData(int method, String url, boolean isGeneric, Class<T> clazz, Listener<T> listener, ErrorListener errorListener, String...params) {
		if(params.length != 0)
			url = NetParamGenerator.getUrlWithParams(url, params);
		MyConvertRequest<T> request = new MyConvertRequest<T>(method, url, isGeneric, clazz, listener, errorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(String url, Listener<String> listener, String...params) {
		if(params.length != 0)
			url = NetParamGenerator.getUrlWithParams(url, params);
		MyRequest request = new MyRequest(Method.GET, url, listener, mDefaultErrorListener);
		mQueue.add(request);
	}
	
	protected <T> void fetchData(String url, boolean isGeneric, Class<T> clazz, Listener<T> listener, String...params) {
		if(params.length != 0)
			url = NetParamGenerator.getUrlWithParams(url, params);
		MyConvertRequest<T> request = new MyConvertRequest<T>(url, isGeneric, clazz, listener, mDefaultErrorListener);
		mQueue.add(request);
	}
	
	public void fetchImg(String imgPath, ImageView img, int defaultImgResId, int errorImgResId) {
		mImgLoader.get(imgPath, ImageLoader.getImageListener(img, defaultImgResId, errorImgResId));
	}
	
	public void fetchImg(String imgPath, ImageView img) {
		mImgLoader.get(imgPath, ImageLoader.getImageListener(img, mDefaultImgResId, mErrorImgResId));
	}

}
