package se.kth.iv1201.group4.recruitment.dataReader.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Converts json arrays to {@link List} of {@link Map} with the content
 *
 * @author Filip Garamvölgyi
 */
public class JsonToMapReader {
    /**
     * Creates a List of maps containing all of the objects in a JSON file.
     * All objects of the JSON file need to be wrapped around an array.
     *
     * @param path  the path to the JSON file needs to be in the resource folder in classpath.
     * @return      returns a List of maps with the content of the file.
     */
    public List<Map<String, Object>> convertJsonFileToMap(final String path){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(path);
        List<Map<String,Object>> content = new ArrayList<Map<String, Object>>();
        
        try {
            String json = readFromInputStream(is);
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            content = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return content;
    }
    private String readFromInputStream(InputStream is){
        StringBuilder data = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while((line = br.readLine()) != null) data.append(line);
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return data.toString();
    }
}
