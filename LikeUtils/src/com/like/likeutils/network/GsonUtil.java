package com.like.likeutils.network;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {

	public static Gson gson = new Gson();

	public static <T> T fromJson(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}
	
	public static <T> T fromJsonForGeneric(String json, Class<T> target) {
		Type type = new TypeToken<T>(){}.getType();
		return gson.fromJson(json, type);
	}
	
	
}
