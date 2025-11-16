# Client Integration Guide

This guide explains how other services can use the OpenAPI specification to generate client DTOs and call the Ticket Management Payment Service.

## Step 1: Get the OpenAPI Specification

The OpenAPI YAML file is located at:
```
TicketManagementService/src/main/resources/openapi/payment-api.yaml
```

**Option A: Copy the file directly**
- Copy `payment-api.yaml` to your service's resources folder
- Example: `YourService/src/main/resources/openapi/payment-api.yaml`

**Option B: Access via HTTP (if service is running)**
- If the TicketManagementService is running, you can fetch the OpenAPI spec:
  ```
  http://localhost:8080/api-docs
  ```
- Save the JSON/YAML response to your service

**Option C: Use a shared location**
- Store the YAML in a shared repository or artifact
- Reference it via Maven dependency or URL

## Step 2: Add OpenAPI Generator Plugin to Your Service

Add the following plugin configuration to your service's `pom.xml`:

```xml
<properties>
    <openapi-generator.version>7.2.0</openapi-generator.version>
</properties>

<dependencies>
    <!-- Spring Web Client for making HTTP calls -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    
    <!-- Or use RestTemplate (Spring Web) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- OpenAPI Generator Plugin -->
        <plugin>
            <groupId>org.openapitools</groupId>
            <artifactId>openapi-generator-maven-plugin</artifactId>
            <version>${openapi-generator.version}</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                    <configuration>
                        <!-- Path to the OpenAPI YAML file -->
                        <inputSpec>${project.basedir}/src/main/resources/openapi/payment-api.yaml</inputSpec>
                        
                        <!-- Use 'java' generator for client code -->
                        <generatorName>java</generatorName>
                        
                        <!-- Choose library: resttemplate, webclient, or native -->
                        <library>resttemplate</library>
                        
                        <!-- Package names for generated code -->
                        <apiPackage>com.yourservice.client.payment.api</apiPackage>
                        <modelPackage>com.yourservice.client.payment.model</modelPackage>
                        <invokerPackage>com.yourservice.client.payment</invokerPackage>
                        
                        <!-- Configuration options -->
                        <configOptions>
                            <sourceFolder>src/main/java</sourceFolder>
                            <dateLibrary>java8</dateLibrary>
                            <java8>true</java8>
                            <library>resttemplate</library>
                            <useJakartaEe>true</useJakartaEe>
                        </configOptions>
                        
                        <output>${project.build.directory}/generated-sources/openapi</output>
                        <generateSupportingFiles>true</generateSupportingFiles>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        
        <!-- Build Helper Plugin - Add generated sources to build path -->
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>build-helper-maven-plugin</artifactId>
            <version>3.4.0</version>
            <executions>
                <execution>
                    <id>add-source</id>
                    <phase>generate-sources</phase>
                    <goals>
                        <goal>add-source</goal>
                    </goals>
                    <configuration>
                        <sources>
                            <source>${project.build.directory}/generated-sources/openapi</source>
                        </sources>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Step 3: Generate Client Code

Run Maven to generate the client code:

```bash
mvn clean install
```

This will generate:
- **API Client**: `com.yourservice.client.payment.api.PaymentsApi`
- **DTOs**: 
  - `com.yourservice.client.payment.model.TicketPurchaseRequest`
  - `com.yourservice.client.payment.model.SubscriptionPurchaseRequest`
  - `com.yourservice.client.payment.model.PaymentResponse`
  - `com.yourservice.client.payment.model.ErrorResponse`

## Step 4: Create a Payment Client Service

Create a service in your application to use the generated client:

### Using RestTemplate (Spring Web)

```java
package com.yourservice.service;

import com.yourservice.client.payment.api.PaymentsApi;
import com.yourservice.client.payment.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class PaymentClientService {
    
    private final PaymentsApi paymentsApi;
    
    @Value("${payment.service.url:http://localhost:8080}")
    private String paymentServiceUrl;
    
    public PaymentClientService() {
        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        
        // Create API client with base URL
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(paymentServiceUrl);
        
        // Create PaymentsApi instance
        this.paymentsApi = new PaymentsApi(apiClient);
    }
    
    /**
     * Process ticket purchase payment
     */
    public PaymentResponse processTicketPurchase(
            UUID userId,
            UUID routeId,
            UUID startStopId,
            UUID endStopId,
            java.math.BigDecimal price,
            String paymentMethod,
            String idempotencyKey) {
        
        // Create request DTO
        TicketPurchaseRequest request = new TicketPurchaseRequest();
        request.setUserId(userId);
        request.setRouteId(routeId);
        request.setStartStopId(startStopId);
        request.setEndStopId(endStopId);
        request.setPrice(price);
        request.setPaymentMethod(TicketPurchaseRequest.PaymentMethodEnum.fromValue(paymentMethod));
        request.setIdempotencyKey(idempotencyKey);
        
        // Call the API
        ResponseEntity<PaymentResponse> response = paymentsApi.processTicketPurchase(request);
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Payment processing failed");
        }
    }
    
    /**
     * Process subscription purchase payment
     */
    public PaymentResponse processSubscriptionPurchase(
            UUID userId,
            UUID subscriptionId,
            java.math.BigDecimal price,
            String paymentMethod,
            String idempotencyKey) {
        
        // Create request DTO
        SubscriptionPurchaseRequest request = new SubscriptionPurchaseRequest();
        request.setUserId(userId);
        request.setSubscriptionId(subscriptionId);
        request.setPrice(price);
        request.setMethod(SubscriptionPurchaseRequest.MethodEnum.fromValue(paymentMethod));
        request.setIdempotencyKey(idempotencyKey);
        
        // Call the API
        ResponseEntity<PaymentResponse> response = paymentsApi.processSubscriptionPurchase(request);
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Payment processing failed");
        }
    }
    
    /**
     * Get payment by ID
     */
    public PaymentResponse getPaymentById(UUID paymentId) {
        ResponseEntity<PaymentResponse> response = paymentsApi.getPaymentById(paymentId);
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Payment not found");
        }
    }
    
    /**
     * Get payment by transaction ID
     */
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        ResponseEntity<PaymentResponse> response = paymentsApi.getPaymentByTransactionId(transactionId);
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Payment not found");
        }
    }
}
```

### Using WebClient (Reactive - Spring WebFlux)

```java
package com.yourservice.service;

import com.yourservice.client.payment.api.PaymentsApi;
import com.yourservice.client.payment.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PaymentClientService {
    
    private final PaymentsApi paymentsApi;
    
    @Value("${payment.service.url:http://localhost:8080}")
    private String paymentServiceUrl;
    
    public PaymentClientService() {
        // Create WebClient
        WebClient webClient = WebClient.builder()
                .baseUrl(paymentServiceUrl)
                .build();
        
        // Create API client with WebClient
        ApiClient apiClient = new ApiClient(webClient);
        apiClient.setBasePath(paymentServiceUrl);
        
        // Create PaymentsApi instance
        this.paymentsApi = new PaymentsApi(apiClient);
    }
    
    /**
     * Process ticket purchase payment (reactive)
     */
    public Mono<PaymentResponse> processTicketPurchase(
            UUID userId,
            UUID routeId,
            UUID startStopId,
            UUID endStopId,
            java.math.BigDecimal price,
            String paymentMethod,
            String idempotencyKey) {
        
        TicketPurchaseRequest request = new TicketPurchaseRequest();
        request.setUserId(userId);
        request.setRouteId(routeId);
        request.setStartStopId(startStopId);
        request.setEndStopId(endStopId);
        request.setPrice(price);
        request.setPaymentMethod(TicketPurchaseRequest.PaymentMethodEnum.fromValue(paymentMethod));
        request.setIdempotencyKey(idempotencyKey);
        
        return paymentsApi.processTicketPurchase(request)
                .map(ResponseEntity::getBody)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment processing failed")));
    }
}
```

## Step 5: Configure Service URL

Add the payment service URL to your `application.properties` or `application.yml`:

```properties
# application.properties
payment.service.url=http://localhost:8080
```

Or:

```yaml
# application.yml
payment:
  service:
    url: http://localhost:8080
```

For production:
```properties
payment.service.url=https://payment-service.example.com
```

## Step 6: Use the Client in Your Service

```java
package com.yourservice.controller;

import com.yourservice.service.PaymentClientService;
import com.yourservice.client.payment.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private PaymentClientService paymentClientService;
    
    @PostMapping("/{orderId}/pay")
    public PaymentResponse processPayment(@PathVariable UUID orderId, @RequestBody PaymentRequest req) {
        // Process payment using the generated client
        return paymentClientService.processTicketPurchase(
            req.getUserId(),
            req.getRouteId(),
            req.getStartStopId(),
            req.getEndStopId(),
            req.getPrice(),
            "CREDIT_CARD",
            UUID.randomUUID().toString() // idempotency key
        );
    }
}
```

## Alternative: Using Feign Client (Recommended for Spring Cloud)

If you're using Spring Cloud, you can use Feign with OpenAPI:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

Then create a Feign client interface:

```java
@FeignClient(name = "payment-service", url = "${payment.service.url}")
public interface PaymentServiceClient {
    
    @PostMapping("/api/payments/ticket-purchase")
    PaymentResponse processTicketPurchase(@RequestBody TicketPurchaseRequest request);
    
    @PostMapping("/api/payments/subscription-purchase")
    PaymentResponse processSubscriptionPurchase(@RequestBody SubscriptionPurchaseRequest request);
    
    @GetMapping("/api/payments/{paymentId}")
    PaymentResponse getPaymentById(@PathVariable UUID paymentId);
}
```

## Error Handling

The generated client will throw exceptions. Handle them appropriately:

```java
try {
    PaymentResponse response = paymentClientService.processTicketPurchase(...);
    // Process success
} catch (ApiException e) {
    // Handle API errors
    if (e.getCode() == 404) {
        // Not found
    } else if (e.getCode() == 400) {
        // Bad request
    } else if (e.getCode() >= 500) {
        // Server error
    }
}
```

## Best Practices

1. **Idempotency Keys**: Always provide idempotency keys for payment operations to ensure safe retries
2. **Error Handling**: Implement proper error handling for all payment operations
3. **Timeout Configuration**: Configure appropriate timeouts for HTTP clients
4. **Retry Logic**: Implement retry logic for transient failures
5. **Logging**: Log all payment operations for audit purposes
6. **Validation**: Validate DTOs before sending requests

## Example: Complete Integration

```java
@Service
public class OrderService {
    
    @Autowired
    private PaymentClientService paymentClient;
    
    @Transactional
    public Order createOrderWithPayment(OrderRequest orderRequest) {
        // Create order
        Order order = new Order();
        // ... set order fields
        
        // Process payment
        try {
            PaymentResponse payment = paymentClient.processTicketPurchase(
                orderRequest.getUserId(),
                orderRequest.getRouteId(),
                orderRequest.getStartStopId(),
                orderRequest.getEndStopId(),
                orderRequest.getPrice(),
                "CREDIT_CARD",
                order.getId().toString() // Use order ID as idempotency key
            );
            
            // Link payment to order
            order.setPaymentId(payment.getId());
            order.setPaymentStatus(payment.getStatus().toString());
            
            return orderRepository.save(order);
        } catch (Exception e) {
            // Handle payment failure
            throw new PaymentProcessingException("Failed to process payment", e);
        }
    }
}
```

## Summary

1. Copy `payment-api.yaml` to your service
2. Add OpenAPI Generator plugin to `pom.xml`
3. Run `mvn clean install` to generate client code
4. Create a service class using the generated API client
5. Configure the payment service URL
6. Use the client in your business logic

The generated DTOs ensure type safety and consistency across services!

