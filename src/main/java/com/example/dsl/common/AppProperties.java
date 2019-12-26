package com.example.dsl.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "app-config") // 설정 파일로부터 데이터를 자동 셋 되도록함
@Getter
@Setter
public class AppProperties {
    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;
}
