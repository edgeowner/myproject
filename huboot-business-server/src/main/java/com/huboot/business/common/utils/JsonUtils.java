package com.huboot.business.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.huboot.business.common.component.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by wjc on 2016/11/2.
 */
public class JsonUtils {

    private static final ObjectMapper JSON = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static {
        JSON.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JSON.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
    }
    /*private static JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
    private AccountBean bean = null;*/

    /**
     * 如果对象为Null,返回"null".
     * 如果集合为空集合,返回"[]".
     */
    public static String toJsonString(Object object) {
        try {
            return JSON.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("", e);
            throw new BizException("json解析错误");
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * <p>List<String> ,Map<String,Object>
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz){
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) JSON.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("", e);
            throw new BizException("json解析错误");
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * <p>
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     */
    public static <T> List<T> fromJsonToList(String jsonString, Class<T> clazz){
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return JSON.readValue(jsonString, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            logger.warn("", e);
            throw new BizException("json解析错误");
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * <p>
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     */
    public static <K, V> Map<K, V> fromJsonToMap(String jsonString/*, Class<Map<K, V>> clazz*/){
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return JSON.readValue(jsonString, new TypeReference<Map<K, V>>() {
            });
        } catch (IOException e) {
            logger.warn("", e);
            throw new BizException("json解析错误");
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * <p>
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     */
    public static <K, V> Map<K, V> fromObjectToMap(Object object/*, Class<Map<K, V>> clazz*/) {
        if (StringUtils.isEmpty(object)) {
            return null;
        }
        String jsonString = toJsonString(object);
        return fromJsonToMap(jsonString/*, clazz*/);

    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * <p>
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     */
    public static <T> T fromMapToObject(Map map, Class<T> clazz) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        String jsonString = toJsonString(map);
        return (T) fromJson(jsonString, clazz);

    }

    public static JsonNode fromStringToNode(String json) {
        try {
            return JSON.readTree(json);
        } catch (IOException e) {
            logger.warn("", e);
            throw new BizException("json解析错误");
        }
    }

    public static void main(String[] args) {
        String json = "{\"name\":\"小民\",\"age\":20,\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\"}";
//        Map<String, String> a = fromJsonToMap(json, Map<String, String>.getClass());

    }
}
