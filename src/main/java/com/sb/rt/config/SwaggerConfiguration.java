/**
 * 
 */
package com.sb.rt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ankur.mahajan
 *
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {

	@Value("${swagger.module.name}")
	private String moduleName;

	@Value("${swagger.module.description}")
	private String description;

	@Value("${swagger.module.terms.and.conditions}")
	private String tnc;

	@Value("${swagger.module.owner.name}")
	private String ownerName;

	@Value("${swagger.module.owner.link}")
	private String ownerLink;

	@Value("${swagger.module.owner.email}")
	private String ownerEmail;

	@Value("${swagger.module.license}")
	private String license;

	@Bean
	public Docket api() {
		// return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).
		// directModelSubstitute(LocalDate.class,
		// String.class).genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).paths(Predicates.not(PathSelectors.regex("/basic-error-controller.*")))
		// .paths(Predicates.not(PathSelectors.regex("/_ah/*"))).build();
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.sb.rt.controllers"))
				.paths(Predicates.not(PathSelectors.regex("/errors.*")))
				.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(moduleName).description(description)
				.termsOfServiceUrl(tnc)
				.contact(new Contact(ownerName, ownerLink, ownerEmail))
				.license(license).build();
	}
}
