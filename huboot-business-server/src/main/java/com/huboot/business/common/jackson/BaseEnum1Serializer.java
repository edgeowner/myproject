package com.huboot.business.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.fasterxml.jackson.databind.util.EnumValues;
import com.huboot.business.common.enums.BaseEnum;

import java.io.IOException;


@SuppressWarnings("rawtypes")
public class BaseEnum1Serializer<T extends BaseEnum> extends EnumSerializer {

    public BaseEnum1Serializer(EnumValues v, Boolean serializeAsIndex) {
        super(v, serializeAsIndex);
    }

    @Override
    public void serializeWithType(Enum<?> value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g,
                typeSer.typeId(value, JsonToken.VALUE_STRING));
        serialize(value, g, provider);
        typeSer.writeTypeSuffix(g, typeIdDef);
        super.serializeWithType(value, g, provider, typeSer);
    }

/* private Class<T> clazz;
    private String[] proptertiesName;
    public BaseEnumSerializer(Class<T> clazz,String ... proptertiesName) {
        super(clazz,false);
        this.clazz = clazz;
        this.proptertiesName = proptertiesName;
    }

    public void write(JSONSerializer serializer, Object object,
                      Object fieldName, Type fieldType) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object == null) {
            serializer.getWriter().writeNull();
            return;
        }
        T e = clazz.cast(object) ;
        serializer.write(e.name());
        if(proptertiesName==null){
            return;
        }
        for(String propertyName:proptertiesName){
            propertyName = propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
            try {
                Method method = clazz.getMethod("get" + propertyName);
                if(fieldName instanceof Integer){
                    out.write(",");
                    serializer.write(method.invoke(e));
                }else{
                    out.write(",");
                    serializer.write(fieldName + propertyName);
                    out.write(":");
                    serializer.write(method.invoke(e));
                }
            } catch (Exception e1) {
                return;
            }
        }
    }*/

}
