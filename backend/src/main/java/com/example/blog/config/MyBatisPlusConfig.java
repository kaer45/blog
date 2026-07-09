
package com.example.blog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.blog.mapper")
public class MyBatisPlusConfig {
}
