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
	private boolean mIsDebug = false;
	private int mDefaultImgResId;
	private int mErrorImgResId;
	private ErrorListener mDefaultErrorListener;
	
	private RequestQueue mQueue;
	private ImageLoader mImgLoader;
	private Context mContext;
	
	protected DataFetcherBase(Context context) {
		this.mContext = context;
		mQueue = NetworkUtil.getInstance(context).getRequestQueue();
		mImgLoader = NetworkUtil.getInstance(context).getImageLoader();
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
	
	public void setDebug(boolean isDebug) {
		this.mIsDebug = isDebug;
	}
	
	private Listener<String> getProxyListener(int method, String url, Listener<String> listener, Map<String, String> params) {
		Listener<String> proxyListener = null;
		if(mIsDebug) {
			proxyListener = new DebugListener(method, listener, url, params);
		} else {
			proxyListener = listener;
		}
		return proxyListener;
	}
	private Listener<String> getProxyListener(int method, String url, Listener<String> listener, String... params) {
		Listener<String> proxyListener = null;
		if(mIsDebug) {
			proxyListener = new DebugListener(method, listener, url, params);
		} else {
			proxyListener = listener;
		}
		return proxyListener;
	}
	
	protected void fetchData(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		url = NetParamGenerator.getUrlWithoutParams(url);
		Listener<String> proxyListener = getProxyListener(method, url, listener, params);
		NetRequest request = new NetRequest(method, url, params, proxyListener, errorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(String url, Map<String, String> params, Listener<String> listener) {
		url = NetParamGenerator.getUrlWithoutParams(url);
		Listener<String> proxyListener = getProxyListener(Method.POST, url, listener, params);;
		NetRequest request = new NetRequest(Method.POST, url, params, proxyListener, mDefaultErrorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		url = NetParamGenerator.getUrlWithoutParams(url);
		Listener<String> proxyListener = getProxyListener(Method.POST, url, listener, params);;
		NetRequest request = new NetRequest(Method.POST, url, params, proxyListener, errorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(int method, String url, Listener<String> listener, ErrorListener errorListener, String...params) {
		if(params != null && params.length != 0)
			url = NetParamGenerator.getUrlWithParams(url, params);
		Listener<String> proxyListener = getProxyListener(method, url, listener, params);;
		NetRequest request = new NetRequest(method, url, proxyListener, errorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(String url, Listener<String> listener, String...params) {
		if(params.length != 0)
			url = NetParamGenerator.getUrlWithParams(url, params);
		Listener<String> proxyListener = getProxyListener(Method.GET, url, listener, params);;
		NetRequest request = new NetRequest(Method.GET, url, proxyListener, mDefaultErrorListener);
		mQueue.add(request);
	}
	
	protected void fetchData(String url, Listener<String> listener, ErrorListener errorListener, String...params) {
		if(params.length != 0)
			url = NetParamGenerator.getUrlWithParams(url, params);
		Listener<String> proxyListener = getProxyListener(Method.GET, url, listener, params);;
		NetRequest request = new NetRequest(Method.GET, url, proxyListener, errorListener);
		mQueue.add(request);
	}
	
	public void getImg(String imgPath, ImageView img, int defaultImgResId, int errorImgResId) {
		mImgLoader.get(imgPath, ImageLoader.getImageListener(img, defaultImgResId, errorImgResId));
	}
	
	public void getImg(String imgPath, ImageView img) {
		mImgLoader.get(imgPath, ImageLoader.getImageListener(img, mDefaultImgResId, mErrorImgResId));
	}

}
