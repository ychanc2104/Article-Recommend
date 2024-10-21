package pine.demo.recommend.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaDto implements Serializable {

    private static final long serialVersionUID = -2425427890617873572L;
    private String responseTime;
    private String timestamp;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    public static MetaDto at(Date startTime) {
        if (startTime != null) {
            MetaDto meta = new MetaDto();
            meta.setResponseTime((new Date().getTime() - startTime.getTime()) + " ms");
            meta.setTimestamp(formatter.format(startTime));
            return meta;
        } else {
            MetaDto meta = new MetaDto();
            meta.setResponseTime("0 ms");
            meta.setTimestamp(formatter.format(new Date()));
            return meta;
        }
    }
}
