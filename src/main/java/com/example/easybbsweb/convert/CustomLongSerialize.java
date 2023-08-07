package com.example.easybbsweb.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomLongSerialize extends JsonSerializer<Long> {



    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null && value.toString().length() > 16) { // 因为前端支持最大数字是16位所以long 19 位，大于后转化成字符串，防止精度丢失
            gen.writeString(value.toString());
        } else {
            gen.writeNumber(value);
        }
    }
}