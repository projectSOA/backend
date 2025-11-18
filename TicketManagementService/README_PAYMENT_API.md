# Payment API with OpenAPI/Swagger Integration

This service implements payment processing using Stripe API with OpenAPI 3.0 specification for API contract definition.

## Overview

- **OpenAPI Specification**: `src/main/resources/openapi/payment-api.yaml`
- **Generated Code Location**: `target/generated-sources/openapi/` (after build)
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## Quick Start

### 1. Build the Project

```bash
cd TicketManagementService
mvn clean install
```

This will:
- Generate API interfaces in `org.example.ticketmanagementservice.api`
- Generate DTOs in `org.example.ticketmanagementservice.api.model`
- Add generated sources to the build path

### 2. Configure Stripe

Set your Stripe API key:

**Option 1: Environment Variable (Recommended)**
```bash
export STRIPE_API_KEY=your_stripe_secret_key_here
```

**Option 2: application.properties**
```properties
stripe.api.key=${STRIPE_API_KEY:your_stripe_secret_key_here}
```

**Note:** Never commit real API keys to version control. Use environment variables or secure configuration management.

### 3. Run the Application

```bash
mvn spring-boot:run
```

The controller already implements the generated `PaymentsApi` interface and uses generated DTOs directly. No manual updates needed after build!

### 4. Access Swagger UI

After starting the application, access Swagger UI at: http://localhost:8080/swagger-ui.html

## API Endpoints

### POST /api/payments/ticket-purchase
Process payment for ticket purchase.

**Request Body:**
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "routeId": "550e8400-e29b-41d4-a716-446655440001",
  "startStopId": "550e8400-e29b-41d4-a716-446655440002",
  "endStopId": "550e8400-e29b-41d4-a716-446655440003",
  "price": 25.50,
  "paymentMethod": "CREDIT_CARD",
  "idempotencyKey": "optional-key"
}
```

### POST /api/payments/subscription-purchase
Process payment for subscription purchase.

**Request Body:**
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "subscriptionId": "550e8400-e29b-41d4-a716-446655440001",
  "price": 99.99,
  "method": "CREDIT_CARD",
  "idempotencyKey": "optional-key"
}
```

### GET /api/payments/{paymentId}
Get payment details by payment ID.

### GET /api/payments/transaction/{transactionId}
Get payment details by Stripe transaction ID.

## Using the Contract in Other Services

**📖 See [CLIENT_INTEGRATION_GUIDE.md](./CLIENT_INTEGRATION_GUIDE.md) for detailed instructions on how other services can integrate with this payment service.**

The OpenAPI YAML file serves as the contract for other services. To use it:

1. **Copy the YAML file** to your service:
   ```
   src/main/resources/openapi/payment-api.yaml
   ```

2. **Add OpenAPI Generator plugin** to your `pom.xml`:
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
               </configuration>
           </execution>
       </executions>
   </plugin>
   ```

3. **Generate client code**:
   ```bash
   mvn clean install
   ```

4. **Use generated DTOs** to call the payment service.

## Project Structure

```
TicketManagementService/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/ticketmanagementservice/
│   │   │       ├── api/                    # Generated (after build)
│   │   │       │   └── PaymentsApi.java
│   │   │       ├── api/model/              # Generated (after build)
│   │   │       │   ├── TicketPurchaseRequest.java
│   │   │       │   ├── SubscriptionPurchaseRequest.java
│   │   │       │   └── PaymentResponse.java
│   │   │       ├── controller/
│   │   │       │   └── PaymentController.java
│   │   │       ├── services/
│   │   │       │   ├── PaymentService.java
│   │   │       │   └── StripeService.java
│   │   │       └── mapper/
│   │   │           ├── PaymentMapper.java
│   │   │           ├── TicketPurchaseToPaymentMapper.java
│   │   │           └── SubscriptionPurchaseToPaymentMapper.java
│   │   └── resources/
│   │       ├── openapi/
│   │       │   └── payment-api.yaml        # OpenAPI contract
│   │       └── application.properties
└── target/
    └── generated-sources/
        └── openapi/                        # Generated code
```

## Notes

- The OpenAPI YAML file is the **single source of truth** for the API contract
- After modifying `payment-api.yaml`, run `mvn clean install` to regenerate code
- Generated code should **not** be manually edited
- All DTOs are generated from OpenAPI - no internal DTOs are used
- The service uses generated OpenAPI DTOs directly (TicketPurchaseRequest, SubscriptionPurchaseRequest, PaymentResponse)

## Troubleshooting

### Build fails with "Cannot find symbol"
- Run `mvn clean install` to generate the OpenAPI classes first
- Ensure the build-helper-maven-plugin is configured correctly

### Stripe API errors
- Verify your Stripe API key is set correctly
- Check that the key has the correct permissions
- Ensure you're using the correct environment (test vs production)

### Swagger UI not accessible
- Verify SpringDoc dependency is in `pom.xml`
- Check that the application is running on port 8080
- Try accessing `/api-docs` to see if the OpenAPI JSON is generated

