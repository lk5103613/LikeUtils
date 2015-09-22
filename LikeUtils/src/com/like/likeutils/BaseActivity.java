package com.like.likeutils;

import com.like.likeutils.test.DataFetcherTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class BaseActivity extends Activity {
	
	protected Context mContext;
	protected DataFetcherTest mDataFetcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mDataFetcher = new DataFetcherTest(mContext);
	}

}
