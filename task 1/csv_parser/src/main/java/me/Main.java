package me;

import me.comparator.StatusComparator;
import me.parser.CSVParser;
import me.parser.CSVWritable;
import me.parser.JSONParser;
import me.pojo.Status;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    final static Date cutoffDate = Date.from(LocalDate.parse("2017-06-30").atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .toInstant());

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Output file path argument necessary");
            System.exit(10);
        }
        String outputFilePath = args[0];

        JSONParser<Status> jsonParser = new JSONParser<>();
        List<Status> statusesRaw = jsonParser.parseJSONArrayFile("statuses.json", Status[].class);

        List<CSVWritable> statuses = statusesRaw.stream()
                .filter(status -> status.getContactTs().after(cutoffDate))
                .sorted(new StatusComparator())
                .collect(Collectors.toList());

        CSVParser parser = new CSVParser();
        File file = parser.parseToCSV(statuses, " | ", outputFilePath);

        if (file == null) {
            System.out.println("Output file not available");
            System.exit(10);
        }
    }
}
