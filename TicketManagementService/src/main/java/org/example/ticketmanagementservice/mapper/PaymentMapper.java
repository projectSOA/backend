package org.example.ticketmanagementservice.mapper;

import org.example.ticketmanagementservice.api.model.PaymentResponse;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.example.ticketmanagementservice.entities.enums.PaymentStatus;
import org.mapstruct.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    /**
     * Map Payment entity to PaymentResponse (generated OpenAPI DTO)
     */
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "relatedId", source = "relatedId"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "method", source = "method", qualifiedByName = "paymentMethodToString"),
            @Mapping(target = "status", source = "status", qualifiedByName = "paymentStatusToString"),
            @Mapping(target = "transactionId", source = "transactionId"),
            @Mapping(target = "paymentDate", source = "paymentDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    })
    PaymentResponse toPaymentResponse(Payment entity);

    @Named("paymentMethodToString")
    default String paymentMethodToString(PaymentMethod method) {
        return method != null ? method.name() : null;
    }

    @Named("paymentStatusToString")
    default String paymentStatusToString(PaymentStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(java.time.LocalDateTime dateTime) {
        return dateTime != null ? dateTime.atOffset(ZoneOffset.UTC) : null;
    }
}