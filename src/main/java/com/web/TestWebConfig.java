package com.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.repository.TestRepository;

@ComponentScan( basePackageClasses = TestRestController.class )
@Configuration
@Import( TestRepository.class )
public class TestWebConfig {
}
