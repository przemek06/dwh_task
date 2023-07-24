package me.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.parser.CSVWritable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Status implements CSVWritable {

    @SerializedName("kontakt_id")
    private long contactId;
    @SerializedName("klient_id")
    private long clientId;
    @SerializedName("pracownik_id")
    private long employeeId;
    private String status;
    @SerializedName("kontakt_ts")
    private Date contactTs;

    @Override
    public List<String> parseToCSVRecord() {
        return Arrays.asList(String.valueOf(contactId), String.valueOf(clientId), String.valueOf(employeeId), status, contactTs.toString());
    }
}
