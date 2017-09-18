package com.boby.livinghelper.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;


public final class GsonUtils {

	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();

	public static String toJson(Object o) {
		String temp = gson.toJson(o);
		return temp;
	}

	public static Object fromJson(String json, Class<?> classType) {
		Object object = null;
		try {
			object = gson.fromJson(json, classType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			System.out.println("数据转换异常");
			return null;
		}
		return object;
	}


	/**
	 * 将对象转为JSON字符串
	 */
	public static String objectToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);

	}

	/**
	 * 将JSON字符串转为对象
	 */
	public static Object jsonToObject(String json,Class<?> clz) throws JSONException {
		Gson gson = new Gson();
		return gson.fromJson(json, clz);

	}




}
