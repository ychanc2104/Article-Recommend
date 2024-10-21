package pine.demo.recommend.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<Result> implements Serializable {

    private static final long serialVersionUID = 5379283283517377435L;
    private boolean success;
    private Result data;
    private MetaDto meta;

    private ErrorDto error;
}
