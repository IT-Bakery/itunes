package com.rascal.itunes;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateClient {

  @Bean
  public RestTemplate restTemplate() {
    var factory = new SimpleClientHttpRequestFactory();
    factory.setReadTimeout((int) TimeUnit.SECONDS.toMillis(5));
    factory.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(5));
    var restTemplate = new RestTemplate(factory);

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL, MediaType.APPLICATION_JSON));
    restTemplate.getMessageConverters().add(0, converter);

    return restTemplate;
  }
}

