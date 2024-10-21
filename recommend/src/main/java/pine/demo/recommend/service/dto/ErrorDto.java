package pine.demo.recommend.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = -1078331158186101070L;
    private String type;
    private String code;
    private String message;
    private String stackTrace;
}
