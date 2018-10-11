package com.xq.gymnasium.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@RestController
public class InterfaceCallAndReturn {

	/**
	 * 返回的json你要取什么参数的值？
	 * @param url 路径
	 * @param param 接口参数
	 * @param want 想要返回的参数的值
	 * @return 返回想要的数据 为json对象
	 * @throws Exception
	 */
	public static JSONObject returnJson(String url,StringBuffer param,String want) throws Exception {
		JSONObject string = null;
		try {
			//工具
			StringBuffer sb = new StringBuffer(url);
			String returnvalue = "";
			String real = "";
			//set参数
			sb.append(param);
			//设置url
			URL realUrl = new URL(sb.toString());
			//打开链接
			HttpURLConnection  con = (HttpURLConnection) realUrl.openConnection();
			//设置请求方式
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Access-Control-Allow-Origin", "*");
			con.setRequestMethod("GET");
			con.setDoInput(true);
			//接受返回的json
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while((returnvalue = in.readLine()) != null) {
				real = returnvalue;
			}
			in.close();
			//将字符串变成json
			JSONObject json = JSONObject.parseObject(real);
			//取返回json里的值
		    string = (JSONObject) json.get(want);
		    System.out.println("string:"+string);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return string;
	}
	
	/**
	 * 返回的json你要取什么参数的值？
	 * @param url 路径
	 * @param param 接口参数
	 * @param want 想要返回的参数的值
	 * @return 返回想要的数据 为json对象
	 * @throws Exception
	 */
	public static Object returnObj(String url,StringBuffer param,String want) throws Exception {
		try {
			//工具
			StringBuffer sb = new StringBuffer(url);
			String returnvalue = "";
			String real = "";
			//set参数
			sb.append(param);
			//设置url
			URL realUrl = new URL(sb.toString());
			//打开链接
			HttpURLConnection  con = (HttpURLConnection) realUrl.openConnection();
			//设置请求方式
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Access-Control-Allow-Origin", "*");
			con.setRequestMethod("GET");
			con.setDoInput(true);
			//接受返回的json
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while((returnvalue = in.readLine()) != null) {
				real = returnvalue;
			}
			in.close();
			//将字符串变成json
			JSONObject json = JSONObject.parseObject(real);
			//取返回json里的值
			return json.get(want);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 你想要data里面的什么值？
	 * @param url 路径
	 * @param param 路径参数
	 * @param want 想要的什么值
	 * @return 想要的值 为String类型
	 * @throws Exception
	 */
	public static String dataWant(String url,StringBuffer param,String want) throws Exception {
		//取data的值
		String wantDataParam = returnJson(url, param, "data").getString(want);
		return wantDataParam;
	}
	/**
	 * 取data里面的数组
	 * @param url 路径
	 * @param param 路径参数
	 * @param want 想要的数组名
	 * @return 一个json数组
	 * @throws Exception
	 */
	@RequestMapping("dataWantArray")
	public static JSONArray dataWantArray(String url,StringBuffer param,String want) throws Exception {
		//取得数组
		
		JSONArray jarray = returnJson(url, param, "data").getJSONArray(want);
		System.out.println(jarray);
		return (JSONArray) jarray;
	}
}
