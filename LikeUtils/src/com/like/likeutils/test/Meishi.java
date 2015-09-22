package com.like.likeutils.test;

import com.google.gson.annotations.SerializedName;

public class Meishi {
	public String meishiid;
	public String name;
	public String price;
	public String avatar;
	@SerializedName("sold_cnt")
	public String soldCnt;
	public String star;
}
