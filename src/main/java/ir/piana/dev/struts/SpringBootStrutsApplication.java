package ir.piana.dev.struts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan
@SpringBootApplication
public class SpringBootStrutsApplication /*extends SpringBootServletInitializer */{
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootStrutsApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStrutsApplication.class, args);
	}

}
