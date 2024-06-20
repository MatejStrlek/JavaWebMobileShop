package hr.algebra.javawebmobileshop.paypal;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import hr.algebra.javawebmobileshop.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {
    @Autowired
    private PayPalHttpClient payPalHttpClient;

    public HttpResponse<Order> createOrder(Double total, String currency, String cancelUrl, String successUrl) throws IOException {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .cancelUrl(cancelUrl)
                .returnUrl(successUrl);
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .description("Mobile purchase")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(currency).value(String.format("%.2f", total)));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);

        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
        return payPalHttpClient.execute(request);
    }

    public HttpResponse<Order> captureOrder(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());
        return payPalHttpClient.execute(request);
    }
}
