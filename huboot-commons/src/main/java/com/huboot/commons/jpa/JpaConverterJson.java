package com.huboot.commons.jpa;

import com.huboot.commons.utils.JsonUtil;

import javax.persistence.AttributeConverter;

/**
 * https://stackoverflow.com/questions/25738569/jpa-map-json-column-to-java-object
 * 序列化内部类的时候，内部类一定要是public static  参考类UserEntity.ThirdOpenId
 */
//@Converter(autoApply = true) //默认会转AttributeConverter的X类型转换
public class JpaConverterJson implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object meta) {
        return JsonUtil.buildNormalMapperWithDefaultTyping().toJson(meta);
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        return JsonUtil.buildNormalMapperWithDefaultTyping().fromJson(dbData, Object.class);
    }
}
