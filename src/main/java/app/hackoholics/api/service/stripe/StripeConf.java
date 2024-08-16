package app.hackoholics.api.service.stripe;

import com.stripe.net.RequestOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConf {
  private final String apiKey;

  public StripeConf(@Value("${stripe.api.key}") String apiKey) {
    this.apiKey = apiKey;
  }

  public RequestOptions getOptions() {
    return RequestOptions.builder()
        .setApiKey(apiKey)
        .build();
  }
}
