package com.elasticsearch.esapplication.model;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Data
@Document(indexName = "products")
public class Product {
    @Id
    private String id ;
    @Field(name = "name",type = FieldType.Text)
    private String name ;
    @Field(name = "description ",type = FieldType.Text)
    private String description ;
    @Field(name = "price",type = FieldType.Double)
    private double price ;

}
