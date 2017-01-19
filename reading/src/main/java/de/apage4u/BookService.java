package de.apage4u;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
class BookService {

  private static final String HTTP_LOCALHOST_8090_RECOMMENDED = "http://localhost:8090/recommended";

  private final RestTemplate restTemplate;

  public BookService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  @HystrixCommand(fallbackMethod = "reliable")
  String readingList() {
    URI uri = URI.create(HTTP_LOCALHOST_8090_RECOMMENDED);

    return this.restTemplate.getForObject(uri, String.class);
  }

  public String reliable() {
    return "Cloud Native Java (O'Reilly) - FALLBACK";
  }
}