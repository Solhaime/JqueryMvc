package SpringBootApp.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize( JsonParser jsonParser , DeserializationContext deserializationContext ) throws IOException, JsonProcessingException {
        return Long.parseLong(jsonParser.getText());
    }
}
