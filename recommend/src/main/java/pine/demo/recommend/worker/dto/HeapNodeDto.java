package pine.demo.recommend.worker.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class HeapNodeDto implements Serializable {
    private static final long serialVersionUID = -5682912060252763026L;

    public HeapNodeDto(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    private String word;

    private Integer count;
}
