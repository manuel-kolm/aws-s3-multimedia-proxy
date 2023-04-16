package com.multimedia.proxy.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableConfigurationProperties({AwsConfigProperties.class})
public class AwsAutoConfiguration {

    @Bean
    Region region(AwsConfigProperties awsConfigProperties) {
        return Region.of(awsConfigProperties.getRegion());
    }

    @Bean
    EnvironmentVariableCredentialsProvider credentialsProvider() {
        return EnvironmentVariableCredentialsProvider.create();
    }

    @Bean
    S3Client s3Client(Region region, EnvironmentVariableCredentialsProvider credentialsProvider) {
        return S3Client.builder() //
                .region(region) //
                .credentialsProvider(credentialsProvider) //
                .build();
    }
}
