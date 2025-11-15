package org.example.ticketmanagementservice.mapper;


import org.example.ticketmanagementservice.dtos.PaymentDTO;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PaymentMapper {

    // Entity -> DTO

    PaymentDTO toDto(Payment entity);

    List<PaymentDTO> toDtoList(List<Payment> entities);

    // DTO -> Entity (useful when you create/save a Payment from API or another service)

    Payment toEntity(PaymentDTO dto);

}