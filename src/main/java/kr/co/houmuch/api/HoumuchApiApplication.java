package kr.co.houmuch.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "kr.co.houmuch")
@EntityScan(basePackages = "kr.co.houmuch")
@EnableJpaRepositories(basePackages = "kr.co.houmuch")
public class HoumuchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoumuchApiApplication.class, args);
    }

}
