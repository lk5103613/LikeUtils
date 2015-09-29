package com.like.likeutils;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDataFetcher.fetchMSList();
//		mDataFetcher.fetchMsList(true);
	}
}
