package app.hackoholics.api.endpoint.validator;

import app.hackoholics.api.endpoint.rest.model.PaymentMethod;
import app.hackoholics.api.model.exception.BadRequestException;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodValidator implements Consumer<PaymentMethod> {
  @Override
  public void accept(PaymentMethod paymentMethod) {
    var builder = new StringBuilder();
    if (paymentMethod.getNumber() == null) {
      builder.append("Number is mandatory. ");
    }
    if (paymentMethod.getCvc() == null) {
      builder.append("CVC is mandatory. ");
    }
    if (paymentMethod.getBrand() == null) {
      builder.append("Brand is mandatory. ");
    }
    if (paymentMethod.getExpMonth() == null) {
      builder.append("Expiration month is mandatory. ");
    }
    if (paymentMethod.getExpYear() == null) {
      builder.append("Expiration year is mandatory.");
    }
    if (!builder.isEmpty()) {
      throw new BadRequestException(builder.toString());
    }
  }
}
