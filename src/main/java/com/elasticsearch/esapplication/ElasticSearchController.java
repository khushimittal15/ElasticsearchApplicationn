package com.elasticsearch.esapplication;

import ch.qos.logback.core.model.Model;
import com.elasticsearch.esapplication.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/home")
public class ElasticSearchController {
    @Autowired
    private ElasticSearchQuery elasticSearchQuery;

    @PostMapping("/createDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Product product) throws IOException{
        String response = elasticSearchQuery.createOrUpdateDocument(product);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/deleteProduct/{productId}")
    public String  deleteDocumentById(@PathVariable("productId") String productId) throws IOException{
        return elasticSearchQuery.deleteDocumentById(productId);
    }
    @GetMapping("/findProduct/{productId}")
    public Product getDocumentById(@PathVariable("productId") String productId) throws IOException{
        return elasticSearchQuery.getDocumentById(productId) ;
    }
    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() throws IOException{
        return elasticSearchQuery.searchAllDocuments() ;
    }
}


