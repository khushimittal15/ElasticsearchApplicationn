package com.elasticsearch.esapplication;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.esapplication.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ElasticSearchQuery {
    @Autowired
    ElasticsearchClient elasticsearchClient;
    private final String indexName = "products";

    public String createOrUpdateDocument(Product product) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(product.getId())
                .document(product)
        );
        if (response.result().name().equals("Created")) {
            return new StringBuilder("Document has successfully been created!").toString();
        } else if (response.result().name().equals("Updated")) {
            return new StringBuilder("Document has successfully been updated!").toString();
        } else {
            return new StringBuilder("Error while performing the operation.").toString();
        }
    }


    public Product getDocumentById(String productId) throws IOException {
        Product product = null;
        GetResponse<Product> response = elasticsearchClient.get(g -> g.index(indexName).id(productId), Product.class);
        if (response.found()) {
            product = response.source();
            System.out.println("The name of product is" + product.getName());
        } else {
            System.out.println("Product not found.");
        }
        return product;
    }

    public String deleteDocumentById(String productId) throws IOException {
        DeleteRequest request = DeleteRequest.of(i -> i.index(indexName).id(productId));
        DeleteResponse response = elasticsearchClient.delete(request);
        if (Objects.nonNull(response.result()) && !response.result().name().equals("NotFound")) {
            return new StringBuilder("Product with id " + response.id() + " has been deleted").toString();
        }
        System.out.println("Product not found");
        return new StringBuilder("Product with id " + response.id() + " has not been found.").toString();
    }


    public List<Product> searchAllDocuments() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(i -> i.index(indexName));
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, Product.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<Product> products = new ArrayList<>();
        for (Hit hit : hits) {
            System.out.print(((Product) hit.source()));
            products.add((Product) hit.source());
        }
        return products;
    }
}
