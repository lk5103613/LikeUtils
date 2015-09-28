package com.like.likeutils.network;

import java.util.HashMap;
import java.util.Map;

public class NetParamGenerator {
	
	public static String getUrlWithParams(String url, String ...params) {
		String result = url;
		for(int i=params.length; i>=1; i--) {
			result = result.replace("%" + i, params[i - 1]);
		}
		return result;
	}
	
	public static String getUrlWithoutParams(String url) {
		url = url.substring(0, url.indexOf("?"));
		return url;
	}
	
	public static String getDescription(String url) {
		String[] splitArr = url.split("//");
		if(splitArr.length < 2)
			return null;
		return splitArr[splitArr.length - 1];
		
	}
	
	public static Map<String, String> getMapParams(String url, String ...params) {
		url = url.substring(url.indexOf("?") + 1);
		String[] keyValues = url.split("=|&");
		for(int i=keyValues.length - 1; i>=0; i--) {
			int index = (i+1)/2;
			keyValues[i] = params[index - 1];
			i--;
		}
		Map<String, String> result = new HashMap<String, String>();
		for(int i=0; i<keyValues.length; i++) {
			result.put(keyValues[i], keyValues[i+1]);
			i++;
		}
		return result;
	}

}
