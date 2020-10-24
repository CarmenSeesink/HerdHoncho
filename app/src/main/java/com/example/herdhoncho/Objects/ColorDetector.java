package com.example.herdhoncho.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ColorDetector {

    List<Color> data;
    ObjectMapper mapper;

//    public ColorDetector(String configurationFile) throws IOException {
//        mapper = new ObjectMapper();
//        data = Arrays.asList(mapper.readValue(new File(configurationFile), Color[].class));
//    }

    public ColorDetector(InputStream stream) throws IOException {
        mapper = new ObjectMapper();
        data =  Arrays.asList(mapper.readValue(stream, Color[].class));
    }

    public List<Color> getData() {
        return data;
    }

    public List<Color> findColor(String hex) {
        return this.findColor(Long.parseLong(hex,16));
    }

    public List<Color> findColor(long value) {
        List<Color> toReturn = new ArrayList<>();
        for(Color item: data) {
            if(item.existsInRange(value))
                toReturn.add(item);
        }
        return toReturn;
    }
}
