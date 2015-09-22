package com.like.likeutils.network;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class  MyRequest extends StringRequest {
	
	private Map<String, String> mParams;
	
	public MyRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}
	
	public MyRequest(int method, String url, Map<String, String> params, 
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		this.mParams = params;
	}
	
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams;
	}

}
