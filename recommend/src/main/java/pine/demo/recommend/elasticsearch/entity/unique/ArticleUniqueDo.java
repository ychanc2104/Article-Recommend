package pine.demo.recommend.elasticsearch.entity.unique;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
public class ArticleUniqueDo {
    @Field(type = FieldType.Text)
    private String website;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String url;

    @Field(type = FieldType.Text)
    private String introduction;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date articleDate;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date createdDate;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date updatedDate;
}