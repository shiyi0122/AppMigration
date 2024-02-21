package com.jxzy.AppMigration.NavigationApp.util.Result;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PageInfoSerializer extends JsonSerializer<Long> {
    public PageInfoSerializer() {
    }

    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value);
    }
}
