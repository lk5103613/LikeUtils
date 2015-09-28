package com.like.likeutils.test;

import android.content.Context;

import com.android.volley.Response.Listener;
import com.like.likeutils.network.DataFetcherBase;

public class DataFetcherTest extends DataFetcherBase {
	
    public DataFetcherTest(Context context) {
		super(context);
		setDebug(true);
	}
    
	public static final String BASE_URL = "http://120.26.67.29";
    public static final String GET_MS_LIST = BASE_URL + "/index.php/Meishi/findListPage?currentPage=%1//获取列表";
    
    
    public void fetchMSList() {
    	fetchData(GET_MS_LIST,  new Listener<String>() {
			@Override
			public void onResponse(String response) {
			}
		},"0");
    }
    
    public void fetchMsList(boolean test) {
    }
    
}
