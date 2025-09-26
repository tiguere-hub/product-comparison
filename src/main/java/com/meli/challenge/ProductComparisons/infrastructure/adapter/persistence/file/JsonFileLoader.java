package com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.entity.ProductJSON;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JsonFileLoader {

    private static final Logger log = LoggerFactory.getLogger(JsonFileLoader.class);
    private final Resource resourceFile;
    private final ObjectMapper objectMapper;

    @Getter
    private Map<String, ProductJSON> productMap;


    public JsonFileLoader(@Value("classpath:products.json") Resource resourceFile, ObjectMapper objectMapper) {
        this.resourceFile = resourceFile;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() throws IOException {
        try (InputStream inputStream = resourceFile.getInputStream()) {
            List<ProductJSON> products = objectMapper.readValue(inputStream, new TypeReference<>() {});
            this.productMap = products.stream().collect(Collectors.toUnmodifiableMap(ProductJSON::id, Function.identity()));
            log.info("{} loaded product from JSON File.", this.productMap.size());
        }
    }
}
