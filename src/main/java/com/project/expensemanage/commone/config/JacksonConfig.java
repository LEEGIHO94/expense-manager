package com.project.expensemanage.commone.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {
    private static final String HYPHEN_PATTERN = "yyyy-M-d";
    private static final String SLASH_PATTERN = "yyyy/M/d";
    private static final String DOT_PATTERN = "yyyy.M.d";
    private static final List<DateTimeFormatter> formatters = List.of(
            DateTimeFormatter.ofPattern(HYPHEN_PATTERN),
            DateTimeFormatter.ofPattern(SLASH_PATTERN),
            DateTimeFormatter.ofPattern(DOT_PATTERN)
    );
    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    //직렬화
    public class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException{
            for (DateTimeFormatter formatter : formatters) {
                try{
                    gen.writeString(value.format(formatter));
                    break;
                } catch (DateTimeException ignored) {
                }
            }
        }
    }
    //역직렬화
    public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            for (DateTimeFormatter formatter : formatters) {
                return LocalDate.parse(p.getValueAsString(),formatter);
            }
            throw new JsonParseException(p, "Unable to parse date: [" + p.getValueAsString()
                    + "]. Supported formats: " + List.of(HYPHEN_PATTERN, SLASH_PATTERN, DOT_PATTERN));
        }
    }
}
