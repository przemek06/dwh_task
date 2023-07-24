package parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class JSONParser<T> {

    public List<T> parseJSONArrayFile(String path, Class<T[]> clazz) {
        try {
            InputStream inputStream = getResourceFileInputStream(path);

            var jsonReader = new JsonReader(new InputStreamReader(inputStream));
            var gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            return Arrays.asList(gson.fromJson(jsonReader, clazz));

        } catch (IOException e) {
            // I assume the file used later in the application exists
            throw new RuntimeException(e);
        }

    }
    private InputStream getResourceFileInputStream(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(path);
    }

}
