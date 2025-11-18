# OpenAPI/Swagger Setup for Ticket Management Service

This service uses OpenAPI 3.0 specification to define the payment API contract and automatically generates DTOs and API interfaces.

## Setup Instructions

### 1. Build the Project

Run the following command to generate the OpenAPI DTOs and interfaces:

```bash
mvn clean install
```

This will:
- Generate API interfaces in `target/generated-sources/openapi/src/main/java/org/example/ticketmanagementservice/api/`
- Generate DTOs in `target/generated-sources/openapi/src/main/java/org/example/ticketmanagementservice/api/model/`
- Add the generated sources to the build path

### 2. Update PaymentController

After the first build, update `PaymentController.java` to:
1. Implement the generated `PaymentsApi` interface
2. Use the generated model classes directly instead of reflection

Example:
```java
@RestController
public class PaymentController implements PaymentsApi {
    // Use generated classes:
    // - org.example.ticketmanagementservice.api.model.TicketPurchaseRequest
    // - org.example.ticketmanagementservice.api.model.SubscriptionPurchaseRequest
    // - org.example.ticketmanagementservice.api.model.PaymentResponse
}
```

### 3. Configure Stripe API Key

Set your Stripe API key in `application.properties` or as an environment variable:

```properties
stripe.api.key=${STRIPE_API_KEY:your_stripe_secret_key_here}
```

Or set the environment variable:
```bash
export STRIPE_API_KEY=your_stripe_secret_key_here
```

**Security Note:** Never commit real API keys to version control. Always use environment variables or secure configuration management in production.

### 4. Access Swagger UI

After starting the application, access Swagger UI at:
- http://localhost:8080/swagger-ui.html

API documentation (OpenAPI JSON) is available at:
- http://localhost:8080/api-docs

## Using the OpenAPI Contract in Other Services

The OpenAPI YAML file is located at:
```
src/main/resources/openapi/payment-api.yaml
```

Other services can:
1. Copy this YAML file to their project
2. Use OpenAPI Generator to generate client DTOs
3. Use the generated DTOs to call this service

### Example: Generate Client DTOs in Another Service

Add to `pom.xml`:
```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.2.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/openapi/payment-api.yaml</inputSpec>
                <generatorName>java</generatorName>
                <library>resttemplate</library>
                <apiPackage>com.yourapp.client.api</apiPackage>
                <modelPackage>com.yourapp.client.model</modelPackage>
                <configOptions>
                    <sourceFolder>src/main/java</sourceFolder>
                    <dateLibrary>java8</dateLibrary>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## API Endpoints

- `POST /api/payments/ticket-purchase` - Process ticket purchase payment
- `POST /api/payments/subscription-purchase` - Process subscription purchase payment
- `GET /api/payments/{paymentId}` - Get payment by ID
- `GET /api/payments/transaction/{transactionId}` - Get payment by Stripe transaction ID

## Notes

- The OpenAPI YAML file is the single source of truth for the API contract
- After modifying `payment-api.yaml`, run `mvn clean install` to regenerate code
- The generated code is in `target/generated-sources/openapi/` and should not be manually edited

