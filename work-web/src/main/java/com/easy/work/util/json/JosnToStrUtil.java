package com.easy.work.util.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easy.work.util.page.PageQueryResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: TODO
 * @param
 * @author Created by wuzhangwei on 2018/9/11 23:55
 */
public class JosnToStrUtil {



	public static String ObjToJsonStr_LayUi(PageQueryResult pageResult) {
		Map map = new HashMap();
		map.put("Total", pageResult.getTotalCount());
		map.put("Rows", pageResult.getResult());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageResult.getTotalCount());
		map.put("data", pageResult.getResult());
		return JSONObject.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
	}


	public static String ObjToJsonStr_LayUi(List listResult) {
		Map map = new HashMap();
		map.put("Total", listResult.size());
		map.put("Rows", listResult);
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", listResult.size());
		map.put("data", listResult);
		return JSONObject.toJSON(map).toString();
	}


}
