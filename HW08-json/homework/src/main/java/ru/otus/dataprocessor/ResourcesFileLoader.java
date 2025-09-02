package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper mapper;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        try (var is = getClass().getClassLoader().getResourceAsStream(fileName)) {

            return mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, Measurement.class));

        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }
}
