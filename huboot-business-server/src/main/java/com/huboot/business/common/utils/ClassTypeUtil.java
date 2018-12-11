package com.huboot.business.common.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/9/17 0017.
 */
public class ClassTypeUtil {

    private static final Map<String,String> CLASS_TYPE_MAPPING = new HashMap<>();

    private static final Map<String,List<String>> CLASS_TYPE_REVERSE = new HashMap<>();

    public static final String BUSINESS_CAR = "商务车";
    public static final String SUV_CAR = "SUV";
    public static final String COMFORTABLE_CAR = "舒适型";
    public static final String LUXURY_CAR = "豪华型";
    public static final String ECONOMIC_CAR = "经济型";
    public static final String TRUCK_CAR = "货车";
    public static final String PICKUP_CAR = "皮卡";
    public static final String SPORTS_CAR = "跑车";
    public static final String BUS_CAR = "客车";

    static {

        CLASS_TYPE_MAPPING.put("MPV",BUSINESS_CAR);
        CLASS_TYPE_MAPPING.put("中型SUV",SUV_CAR);
        CLASS_TYPE_MAPPING.put("中型车",COMFORTABLE_CAR);
        CLASS_TYPE_MAPPING.put("中大型SUV",SUV_CAR);
        CLASS_TYPE_MAPPING.put("中大型车",LUXURY_CAR);
        CLASS_TYPE_MAPPING.put("大型SUV",SUV_CAR);
        CLASS_TYPE_MAPPING.put("大型车",LUXURY_CAR);
        CLASS_TYPE_MAPPING.put("小型SUV",SUV_CAR);
        CLASS_TYPE_MAPPING.put("小型车",ECONOMIC_CAR);
        CLASS_TYPE_MAPPING.put("微卡",TRUCK_CAR);
        CLASS_TYPE_MAPPING.put("微型车",ECONOMIC_CAR);
        CLASS_TYPE_MAPPING.put("微面",TRUCK_CAR);
        CLASS_TYPE_MAPPING.put("皮卡",PICKUP_CAR);
        CLASS_TYPE_MAPPING.put("紧凑型SUV",SUV_CAR);
        CLASS_TYPE_MAPPING.put("紧凑型车",ECONOMIC_CAR);
        CLASS_TYPE_MAPPING.put("跑车",SPORTS_CAR);
        CLASS_TYPE_MAPPING.put("轻客",BUS_CAR);

        Set<Map.Entry<String, String>> set = CLASS_TYPE_MAPPING.entrySet();
        set.stream().forEach(entry -> {
            if(CLASS_TYPE_REVERSE.containsKey(entry.getValue())){
                List<String> v = CLASS_TYPE_REVERSE.get(entry.getValue());
                v.add(entry.getKey());
                return;
            }
            List list = new ArrayList();
            list.add(entry.getKey());
            CLASS_TYPE_REVERSE.put(entry.getValue(),list);
        });

    }

    public static List<String> mapping(List<String> values){
        List<String> list = new ArrayList<>();
        for (String v :
                values) {
            String newType =CLASS_TYPE_MAPPING.get(v);
            list.add(newType);
        }
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }

    public static List<String> getAll(){
        List newTypes = CLASS_TYPE_MAPPING.values().stream().distinct().collect(Collectors.toList());
        return newTypes;
    }

    public static String mapping(String old){
        String mapping = CLASS_TYPE_MAPPING.get(old);
        return mapping;
    }


    public static List<String> reverseMapping(List<String> keys){
        List<String> list = new ArrayList<>();
        for (String key :
                keys) {
            List<String> newType =CLASS_TYPE_REVERSE.get(key);
            list.addAll(newType);
        }
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }


    public static List<String> reverseMapping(String key){
        List<String> s = CLASS_TYPE_REVERSE.get(key);
        return s;
    }

    public static Map<String,List<String>> getReverseMapping(){
        return CLASS_TYPE_REVERSE;
    }

}
