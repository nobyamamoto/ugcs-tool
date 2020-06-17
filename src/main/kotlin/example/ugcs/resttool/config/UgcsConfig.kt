package example.ugcs.resttool.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig() {
  @Bean
  @ConfigurationProperties(prefix = "ugcs")
  fun ugcsConfig() : UgcsConfig {
    return UgcsConfig()
  }
}

class UgcsConfig{
  lateinit var serverHost: String
  lateinit var serverPort: String
  lateinit var login: String
  lateinit var password: String
}
