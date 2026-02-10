package egovframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
	exclude = {
			org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
			org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
	        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
		}
	)
@ComponentScan(basePackages = "egovframework")
public class EgovBootApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		System.out.println("##### ITES start #####");

		SpringApplication springApplication = new SpringApplication(EgovBootApplication.class);
		springApplication.run(args);

		System.out.println("##### ITES End #####");
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EgovBootApplication.class);
    }

}
