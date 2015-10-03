package com.like.likeutils.network;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

public class  NetRequest extends StringRequest {
	
	private static final String PROTOCOL_CHARSET = "utf-8";
	
	private Map<String, String> mParams;
	private String mRequestBody;
	
	public NetRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}
	
	public NetRequest(int method, String url, Map<String, String> params, 
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		this.mParams = params;
	}
	
	public NetRequest(int method, String url, Object obj, Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		mRequestBody = GsonUtil.gson.toJson(obj);
	}
	
	public byte[] getBody() throws AuthFailureError {
		try {
            return mRequestBody == null ? super.getBody() : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        } 
    }
	
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams;
	}

}
