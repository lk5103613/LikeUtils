package com.like.likeutils.network;

import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;

public class DebugListener implements Listener<String> {
	
	private int mMethod = -1;
	private Listener<String> mListener;
	private String mDes;
	private String mUrl;
	private String mBaseUrl;
	private Map<String, String> mParams;
	
	public DebugListener(int method, Listener<String> listener, String url, Map<String, String> params) {
		this.mMethod = method;
		this.mUrl = url;
		this.mBaseUrl = NetParamGenerator.getUrlWithoutParams(mUrl);
		this.mParams = params;
		this.mListener = listener;
		this.mDes = NetParamGenerator.getDescription(url);
	}
	
	public DebugListener(int method, Listener<String> listener, String url, String...params) {
		this.mMethod = method;
		this.mUrl = url;
		this.mBaseUrl = NetParamGenerator.getUrlWithoutParams(mUrl);
		this.mParams = NetParamGenerator.getMapParams(url, params);
		this.mListener = listener;
		this.mDes = NetParamGenerator.getDescription(url);
	}

	@Override
	public void onResponse(String response) {
		String method = "unknown";
		switch (mMethod) {
		case Method.GET:
			method = "GET";
			break;
		case Method.POST:
			method = "POST";
			break;
		case Method.PUT:
			method = "PUT";
			break;
		case Method.DELETE:
			method = "DELETE";
			break;
		default:
			break;
		}
		String desOut = "";
		if(mDes != null) {
			desOut = "============ " + mDes + "============ \r\n";
		}
		String methodOut = "METHOD : " + method + "\r\n";
		String urlOut =    "URL    : " + mBaseUrl + "\r\n";
		StringBuilder paramsOut = new StringBuilder("PARAMS : \r\n");
		for(String key : mParams.keySet()) {
			paramsOut.append("         " + key + " = " + mParams.get(key) + "\r\n");
		}
		String dataOut =   "DATA   : " + response + "\r\n";
		String endOut =    "============ END ============";
		System.out.println("\r\n" + desOut + methodOut + urlOut + paramsOut.toString() + dataOut + endOut);
		mListener.onResponse(response);
	}

}
