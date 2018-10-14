package com.donalola.app.config.dao;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(
        basePackages = {
                "com.donalola.app.config.dao",
                "com.donalola.foodmenu.dao"
        })
public class DynamoDBConfig {

    @Value("${dynamodb.endpoint}")
    private String dynamoDbEndpoint;
    @Value("${dynamodb.accesskey}")
    private String accessKey;
    @Value("${dynamodb.secretAccessKey}")
    private String secretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                //.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint, Regions.US_EAST_1.getName()))
                .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
                .withClientConfiguration(new ClientConfiguration().withRequestTimeout(10000))
                .build();
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

}
