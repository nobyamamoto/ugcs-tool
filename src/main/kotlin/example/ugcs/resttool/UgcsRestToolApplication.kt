package example.ugcs.resttool

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
class UgcsDevtoolApplication

fun main(args: Array<String>) {
    runApplication<UgcsDevtoolApplication>(*args)
}
