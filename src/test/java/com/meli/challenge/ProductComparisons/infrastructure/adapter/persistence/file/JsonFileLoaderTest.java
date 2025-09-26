package com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.entity.ProductJSON;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonFileLoaderTest {

    @Mock
    private Resource mockResourceFile;

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private JsonFileLoader jsonFileLoader;


    @Test
    void whenFileIsValid_thenLoadDataSuccessfully() throws IOException {
        var product1 = new ProductJSON(
                "ID001",
                "Laptop Gamer Pro",
                "Laptop de alto rendimiento para juegos.",
                "http://example.com/laptop.jpg",
                new BigDecimal("1599.99"),
                4.8,
                Map.of("CPU", "Intel Core i9", "RAM", "32GB")
        );
        var product2 = new ProductJSON(
                "ID002",
                "Mouse Inalámbrico RGB",
                "Mouse ergonómico con luces RGB.",
                "http://example.com/mouse.jpg",
                new BigDecimal("75.50"),
                4.5,
                Map.of("DPI", "16000", "Conectividad", "Bluetooth")
        );
        List<ProductJSON> productList = List.of(product1, product2);

        String jsonContent = """
        [
          {
            "id": "ID001",
            "name": "Laptop Gamer Pro",
            "description": "Laptop de alto rendimiento para juegos.",
            "imageUrl": "http://example.com/laptop.jpg",
            "price": 1599.99,
            "rating": 4.8,
            "attributes": {
              "CPU": "Intel Core i9",
              "RAM": "32GB"
            }
          },
          {
            "id": "ID002",
            "name": "Mouse Inalámbrico RGB",
            "description": "Mouse ergonómico con luces RGB.",
            "imageUrl": "http://example.com/mouse.jpg",
            "price": 75.50,
            "rating": 4.5,
            "attributes": {
              "DPI": "16000",
              "Conectividad": "Bluetooth"
            }
          }
        ]
        """;
        InputStream inputStream = new ByteArrayInputStream(jsonContent.getBytes());

        when(mockResourceFile.getInputStream()).thenReturn(inputStream);
        when(mockObjectMapper.readValue(any(InputStream.class), any(TypeReference.class))).thenReturn(productList);

        jsonFileLoader.loadData();

        assertThat(jsonFileLoader.getProductMap()).isNotNull();
        assertThat(jsonFileLoader.getProductMap()).hasSize(2);
        assertThat(jsonFileLoader.getProductMap().get("ID001")).isEqualTo(product1);
        assertThat(jsonFileLoader.getProductMap().get("ID002").name()).isEqualTo("Mouse Inalámbrico RGB");
        assertThat(jsonFileLoader.getProductMap().get("ID001").price()).isEqualByComparingTo("1599.99");
        assertThat(jsonFileLoader.getProductMap().get("ID002").attributes()).containsEntry("DPI", "16000");
    }

    @Test
    void whenJsonArrayIsEmpty_thenProductMapIsEmpty() throws IOException {
        String jsonContent = "[]";
        InputStream inputStream = new ByteArrayInputStream(jsonContent.getBytes());
        when(mockResourceFile.getInputStream()).thenReturn(inputStream);
        when(mockObjectMapper.readValue(any(InputStream.class), any(TypeReference.class))).thenReturn(Collections.emptyList());

        jsonFileLoader.loadData();

        assertThat(jsonFileLoader.getProductMap()).isNotNull();
        assertThat(jsonFileLoader.getProductMap()).isEmpty();
    }

    @Test
    void whenResourceThrowsIOException_thenLoadDataFails() throws IOException {
        when(mockResourceFile.getInputStream()).thenThrow(new IOException("File not found"));

        assertThrows(IOException.class, () -> jsonFileLoader.loadData());
    }
}