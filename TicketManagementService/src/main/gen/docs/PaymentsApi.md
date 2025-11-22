# PaymentsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getPaymentById**](PaymentsApi.md#getPaymentById) | **GET** /api/v1/payments/{paymentId} | Get payment by ID |
| [**getPaymentByTransactionId**](PaymentsApi.md#getPaymentByTransactionId) | **GET** /api/v1/payments/transaction/{transactionId} | Get payment by transaction ID |
| [**processSubscriptionPurchase**](PaymentsApi.md#processSubscriptionPurchase) | **POST** /api/v1/payments/subscription-purchase | Process subscription purchase payment |
| [**processTicketPurchase**](PaymentsApi.md#processTicketPurchase) | **POST** /api/v1/payments/ticket-purchase | Process ticket purchase payment |


<a id="getPaymentById"></a>
# **getPaymentById**
> PaymentResponse getPaymentById(paymentId)

Get payment by ID

Retrieve payment details by payment ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PaymentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PaymentsApi apiInstance = new PaymentsApi(defaultClient);
    UUID paymentId = UUID.randomUUID(); // UUID | Unique identifier of the payment
    try {
      PaymentResponse result = apiInstance.getPaymentById(paymentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PaymentsApi#getPaymentById");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **paymentId** | **UUID**| Unique identifier of the payment | |

### Return type

[**PaymentResponse**](PaymentResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Payment found |  -  |
| **404** | Payment not found |  -  |
| **500** | Internal server error |  -  |

<a id="getPaymentByTransactionId"></a>
# **getPaymentByTransactionId**
> PaymentResponse getPaymentByTransactionId(transactionId)

Get payment by transaction ID

Retrieve payment details by Stripe transaction ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PaymentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PaymentsApi apiInstance = new PaymentsApi(defaultClient);
    String transactionId = "transactionId_example"; // String | Stripe transaction ID
    try {
      PaymentResponse result = apiInstance.getPaymentByTransactionId(transactionId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PaymentsApi#getPaymentByTransactionId");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **transactionId** | **String**| Stripe transaction ID | |

### Return type

[**PaymentResponse**](PaymentResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Payment found |  -  |
| **404** | Payment not found |  -  |
| **500** | Internal server error |  -  |

<a id="processSubscriptionPurchase"></a>
# **processSubscriptionPurchase**
> PaymentResponse processSubscriptionPurchase(subscriptionPurchaseRequest)

Process subscription purchase payment

Process a payment for subscription purchase using Stripe API

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PaymentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PaymentsApi apiInstance = new PaymentsApi(defaultClient);
    SubscriptionPurchaseRequest subscriptionPurchaseRequest = new SubscriptionPurchaseRequest(); // SubscriptionPurchaseRequest | 
    try {
      PaymentResponse result = apiInstance.processSubscriptionPurchase(subscriptionPurchaseRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PaymentsApi#processSubscriptionPurchase");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **subscriptionPurchaseRequest** | [**SubscriptionPurchaseRequest**](SubscriptionPurchaseRequest.md)|  | |

### Return type

[**PaymentResponse**](PaymentResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Payment processed successfully |  -  |
| **400** | Bad request - Invalid input data |  -  |
| **500** | Internal server error - Payment processing failed |  -  |

<a id="processTicketPurchase"></a>
# **processTicketPurchase**
> TicketPurchaseResponse processTicketPurchase(ticketPurchaseRequest)

Process ticket purchase payment

Process a payment for ticket purchase using Stripe API

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PaymentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PaymentsApi apiInstance = new PaymentsApi(defaultClient);
    TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(); // TicketPurchaseRequest | 
    try {
      TicketPurchaseResponse result = apiInstance.processTicketPurchase(ticketPurchaseRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PaymentsApi#processTicketPurchase");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ticketPurchaseRequest** | [**TicketPurchaseRequest**](TicketPurchaseRequest.md)|  | |

### Return type

[**TicketPurchaseResponse**](TicketPurchaseResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Payment + Ticket returned successfully |  -  |
| **400** | Bad request - Invalid input data |  -  |
| **500** | Internal server error - Payment processing failed |  -  |

