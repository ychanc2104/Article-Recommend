package pine.demo.recommend.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AutoCompleteSearchDio implements Serializable {
    @Schema(description = "prefix")
    @JsonProperty("prefix")
    @SerializedName("prefix")
    private String prefix;

    @Schema(description = "top k search words")
    @JsonProperty("topK")
    @SerializedName("topK")
    private Integer topK;

//    @Schema(description = "search depth")
//    @JsonProperty("searchDepth")
//    @SerializedName("searchDepth")
//    private Integer searchDepth;
}