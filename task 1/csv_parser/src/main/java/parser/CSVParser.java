package parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class CSVParser {

    public File parseToCSV(List<CSVWritable> writables, String delimeter, String path) {
        File file = new File(path);
        try (OutputStream outputStream = new FileOutputStream(file)) {

            String csv = writables.stream()
                    .map(w -> String.join(delimeter, w.parseToCSVRecord()) + "\n")
                    .collect(Collectors.joining());

            outputStream.write(csv.getBytes(StandardCharsets.UTF_8));

            return file;
        } catch (IOException e) {
            return null;
        }
    }
}
