package io.github.fishlikewater.raiden.mock.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangxiang
 */
@SpringBootApplication
@ComponentScan(value = {"io.github.fishlikewater.raiden.mock"})
public class RaidenMockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RaidenMockApplication.class, args);
    }

}
