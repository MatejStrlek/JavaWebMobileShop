package hr.algebra.javawebmobileshop.paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {
    @Bean
    public PayPalHttpClient payPalHttpClient() {
        String clientId = "ASNmE3I-mCFQkqdZ0U_ffe956aaPUYA2NB6TP2vphpMtMUarEkcI_okJVZhmS7qQrmUWRFSnbDGqrV4T";
        String clientSecret = "ELn5DoKTfKWvZKaOxHvkAhGOmNk1AEkvzwfXwz51mxPL78hN48rrw3BF-GC0IrROH7ZpfcdB2776KJa6";
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        return new PayPalHttpClient(environment);
    }
}
