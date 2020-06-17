package example.ugcs.resttool.config

import com.google.common.base.Predicate
import com.google.common.base.Predicates.or
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


@Configuration
class SwaggerConfig {

  @Bean
  fun swaggerSpringMvcPlugin(): Docket? {
    return Docket(DocumentationType.SWAGGER_2).groupName("public")
      .select()
      .paths(cameraPaths())
      .build()
      .apiInfo(apiInfo())
  }


  private fun apiInfo(): ApiInfo? {
    return ApiInfoBuilder()
      .title("UgCS Ops API")
      .description("test ")
      .termsOfServiceUrl("http://springfox.io")
      .license("Apache License Version 2.0")
      .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
      .version("2.0")
      .build()
  }

  private fun cameraPaths(): Predicate<String?>? {
    return or(
      regex("/ugcs/.*")
    )
  }
}
