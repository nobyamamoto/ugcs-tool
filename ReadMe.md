# ugcs-devtool

## configuration

change application.yml

```yaml
ugcs:
  server-host: localhost
  server-port: 3334
  login: "<user>"
  password: "<password>"

```


## Build Setup

spring boot run
``` bash
./mvnw package
./mvnw spring-boot:run
```

for docker
```bash
docker build ./ -t spring-ugcs
docker run -p 8081:8080 -t spring-ugcs:latest
```


Input follow url ! ( by spring fox )

http://localhost:8080/swagger-ui.html


![image](https://user-images.githubusercontent.com/64205425/84858534-5841fa00-b0a6-11ea-8dfc-eac8a7f44198.png)
