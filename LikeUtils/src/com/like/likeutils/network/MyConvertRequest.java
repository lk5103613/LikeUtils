package com.like.likeutils.network;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class MyConvertRequest<T> extends Request<T> {

	private Listener<T> mListener;
	private Class<T> mClass;
	private Map<String, String> mParams;
	private boolean mIsGeneric = false;

	public MyConvertRequest(int method, String url, boolean isGeneric, Class<T> clazz, 
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mListener = listener;
		this.mClass = clazz;
		this.mIsGeneric = isGeneric;
	}
	
	public MyConvertRequest(int method, String url, Class<T> clazz, 
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mListener = listener;
		this.mClass = clazz;
		this.mIsGeneric = false; 
	}
	
	public MyConvertRequest(String url, boolean isGeneric, Class<T> clazz, 
			Listener<T> listener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.mListener = listener;
		this.mClass = clazz;
		this.mIsGeneric = isGeneric; 
	}
	
	public MyConvertRequest(String url, Class<T> clazz, 
			Listener<T> listener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.mListener = listener;
		this.mClass = clazz;
		this.mIsGeneric = false; 
	}

	public MyConvertRequest(int method, String url, boolean isGeneric, Class<T> clazz, 
			Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mListener = listener;
		this.mParams = params;
		this.mClass = clazz;
		this.mIsGeneric = isGeneric;
	}
	
	public MyConvertRequest(int method, String url, Class<T> clazz, Map<String, String> params,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mListener = listener;
		this.mParams = params;
		this.mClass = clazz;
		this.mIsGeneric = false;
	}
	
	public MyConvertRequest(String url, boolean isGeneric, Class<T> clazz, Map<String, String> params,
			Listener<T> listener, ErrorListener errorListener) {
		super(Method.POST, url, errorListener);
		this.mListener = listener;
		this.mParams = params;
		this.mClass = clazz;
		this.mIsGeneric = isGeneric;
	}
	
	public MyConvertRequest(String url, Class<T> clazz, Map<String, String> params,
			Listener<T> listener, ErrorListener errorListener) {
		super(Method.POST, url, errorListener);
		this.mListener = listener;
		this.mParams = params;
		this.mClass = clazz;
		this.mIsGeneric = false;
	}
	
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if(mParams != null)
			return mParams;
		return super.getParams();
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		T result;
		if(mIsGeneric) {
			result = GsonUtil.fromJsonForGeneric(parsed, mClass);
		} else {
			result = GsonUtil.fromJson(parsed, mClass);
		}
		return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
	}

}
