package com.lumimindsinc.domain_name.usersregistration.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class ConnectionAWSS3Config {
    @Value("${aws.s3.accessKey}")
    private String awsId;

    @Value("${aws.s3.secretKey}")
    private String awsKey;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(awsId, awsKey);
    }
    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard().withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
    }

}