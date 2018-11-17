package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "aws")
@Data
public class AwsConfiguration {
	public String cloudFrontDomain;
	public String s3StaticFilePath;
}
