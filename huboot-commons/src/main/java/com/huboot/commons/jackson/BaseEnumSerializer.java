package com.huboot.commons.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.huboot.commons.enums.BaseEnum;

import java.io.IOException;

/**
 * 自定义enum显示规则
 */
@JacksonStdImpl
public class BaseEnumSerializer<T> extends JsonSerializer<T> {

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value instanceof String) {
            gen.writeString(value.toString());
            return;
        }
        if (value instanceof BaseEnum) {
            gen.writeString(value.toString());
            return;
        }
    }
}
