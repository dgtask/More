package com.benz.all.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Json处理类
 * @author xubenliang
 *
 * @param <T>
 */
public class JsonUtils<T> {

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.disableHtmlEscaping();
//        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 自定义时间解析
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                long time = json.getAsJsonPrimitive().getAsLong();
                return new Date(time);
            }
        });
    }

    /**
     *
     * 描述：将对象转化为json.
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        String json = null;
        try {
            Gson gson = gsonBuilder.create();
            json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     *
     * 描述：将列表转化为json.
     * @param list
     * @return
     */
    public static String toJson(List<?> list) {
        String json = null;
        try {
            Gson gson = gsonBuilder.create();
            json = gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     *
     * 描述：将json转化为列表.
     * @param json
     * @param typeToken new TypeToken<ArrayList<?>>() {}.getType();
     * @return
     */
    public static List<?> fromJson(String json,TypeToken typeToken) {
        List<?> list = null;
        try {
            Gson gson = gsonBuilder.create();
            Type type = typeToken.getType();
            list = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * 描述：将json转化为对象.
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class<?> clazz) {
        Object obj = null;
        try {
            Gson gson = gsonBuilder.create();
            obj = gson.fromJson(json,clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     *
     * 描述：将json转化为对象.
     * @param json
     * @param type
     * @return
     */
    public static Object fromJson(String json, Type type) {
        Object obj = null;
        try {
            Gson gson = gsonBuilder.create();
            obj = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
