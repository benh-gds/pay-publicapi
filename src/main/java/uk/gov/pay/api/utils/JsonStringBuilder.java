package uk.gov.pay.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonStringBuilder {
    private static final ObjectWriter WRITER = new ObjectMapper()
            .writer();

    private String root;
    private Map<String, Object> map;

    private boolean prettyPrint;

    public static String jsonString(String key, Object value) {
        return new JsonStringBuilder().add(key, value).build();
    }

    public JsonStringBuilder() {
        map = new LinkedHashMap<>();
        prettyPrint = true;
    }

    public JsonStringBuilder noPrettyPrint() {
        prettyPrint = false;
        return this;
    }

    public JsonStringBuilder addRoot(String root) {
        this.root = root;
        return this;
    }

    public JsonStringBuilder add(String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
        return this;
    }

    public JsonStringBuilder addToMap(String mapKey, String key, String value) {
        Map<String, Object> nestedMap = ensureNestedMap(mapKey);
        nestedMap.put(key, value);
        return this;
    }

    public JsonStringBuilder addToMap(String mapKey) {
        ensureNestedMap(mapKey);
        return this;
    }

    public String build() {
        ObjectWriter writer = WRITER;
        if (prettyPrint) {
            writer = writer.withDefaultPrettyPrinter();
        }

        if (root != null) {
            writer = writer
                    .with(SerializationFeature.WRAP_ROOT_VALUE)
                    .withRootName(root);
        }

        return asJsonString(writer, map);
    }

    private String asJsonString(ObjectWriter writer, Object object) {
        try {
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error processing card details", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> ensureNestedMap(String mapKey) {
        Map<String, Object> nestedMap = (Map<String, Object>) map.get(mapKey);
        if (nestedMap == null) {
            nestedMap = new LinkedHashMap<>();
            map.put(mapKey, nestedMap);
        }
        return nestedMap;
    }
}
