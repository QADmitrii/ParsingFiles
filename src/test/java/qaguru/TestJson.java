package qaguru;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import qaguru.domain.Parameters;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class TestJson {
    ClassLoader classLoader = TestJson.class.getClassLoader();

    @Test
    void jsonParser() throws Exception {
        InputStream is = classLoader.getResourceAsStream("Product.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Parameters Parameters = objectMapper.readValue(is, Parameters.class);
        assertThat(Parameters.name).isEqualTo("Boots");
        assertThat(Parameters.price).isEqualTo(2000);
        assertThat(Parameters.category_name).isEqualTo("Test");
        assertThat(Parameters.mod).isEqualTo("Color");


    }
}
