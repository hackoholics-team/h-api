package app.hackoholics.api.endpoint.validator;

import app.hackoholics.api.endpoint.rest.model.Payment;
import app.hackoholics.api.model.exception.BadRequestException;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator implements Consumer<Payment> {
  @Override
  public void accept(Payment payment) {
    var builder = new StringBuilder();
    if (payment.getAmount() == null) {
      builder.append("Amount is mandatory. ");
    }
    if (payment.getCurrency() == null) {
      builder.append("Currency is mandatory. ");
    }
    if (payment.getPaymentMethodId() == null) {
      builder.append("PaymentMethod is mandatory. ");
    }

    if (!builder.isEmpty()) {
      throw new BadRequestException(builder.toString());
    }
  }
}
