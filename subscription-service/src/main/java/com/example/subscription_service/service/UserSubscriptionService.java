package com.example.subscription_service.service;


import com.example.subscription_service.dto.request.CreateUserSubscriptionRequest;
import com.example.subscription_service.dto.request.ValidateUserSubscriptionRequest;
import com.example.subscription_service.dto.response.MonthlySubscriptionCount;
import com.example.subscription_service.dto.response.SubscriptionCountLastTwoMonths;
import com.example.subscription_service.dto.response.UserSubscriptionResponse;
import com.example.subscription_service.dto.response.ValidateUserSubscriptionResponse;
import com.example.subscription_service.entity.Subscription;
import com.example.subscription_service.entity.UserSubscription;
import com.example.subscription_service.enums.SubscriptionStatus;
import com.example.subscription_service.enums.UserSubscriptionStatus;
import com.example.subscription_service.mapper.UserSubscriptionMapper;
import com.example.subscription_service.repository.SubscriptionRepository;
import com.example.subscription_service.repository.UserSubscriptionRepository;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;

    public UserSubscriptionService(UserSubscriptionRepository userSubscriptionRepository, SubscriptionRepository subscriptionRepository) {
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public UserSubscriptionResponse createUserSubscription(CreateUserSubscriptionRequest request) {
        Subscription sub = subscriptionRepository.findById(request.getSubscriptionId())
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        UserSubscription us = new UserSubscription();
        us.setUserId(request.getUserId());
        us.setSubscription(sub);
        us.setStatus(sub.getStatus() == SubscriptionStatus.ACTIVE ? UserSubscriptionStatus.ACTIVE : UserSubscriptionStatus.SUSPENDED);
        us.setStartDate(LocalDate.now());
        us.setEndDate(LocalDate.now().plusDays(sub.getValidityDays()));
        us.setNumberTicketsLeft(sub.getTotalTickets());
        us.setQrCode(generateQrCode()); // implement this method
        userSubscriptionRepository.save(us);

        // Generate QR code using the UUID directly
        String qrBase64 = generateQrCode(us.getId());

        us.setQrCode(qrBase64);
        userSubscriptionRepository.save(us);

        return UserSubscriptionMapper.toResponse(us);
    }

    public ValidateUserSubscriptionResponse validateUserSubscription(ValidateUserSubscriptionRequest request) {
        UserSubscription us = userSubscriptionRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        if (us.getStatus() != UserSubscriptionStatus.ACTIVE) {
            return ValidateUserSubscriptionResponse.denied("NOT_ACTIVE", "Subscription is not active");
        }
        if (us.getNumberTicketsLeft() <= 0) {
            return ValidateUserSubscriptionResponse.denied("NO_TICKETS", "No remaining tickets");
        }
        if (us.getEndDate().isBefore(LocalDate.now())) {
            return ValidateUserSubscriptionResponse.denied("EXPIRED", "Subscription has expired");
        }

        return ValidateUserSubscriptionResponse.granted(us.getNumberTicketsLeft(), us.getEndDate());
    }

    @Transactional
    public UserSubscriptionResponse decrementTicket(UUID userSubscriptionId) {
        UserSubscription us = userSubscriptionRepository.findById(userSubscriptionId)
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        if (us.getNumberTicketsLeft() <= 0) throw new RuntimeException("No tickets left");
        us.setNumberTicketsLeft(us.getNumberTicketsLeft() - 1);
        return UserSubscriptionMapper.toResponse(userSubscriptionRepository.save(us));
    }

    public List<UserSubscriptionResponse> getUserSubscriptions(UUID userId) {
        return userSubscriptionRepository.findAllByUserId(userId).stream()
                .map(UserSubscriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserSubscriptionResponse renewSubscription(UUID userSubscriptionId) {
        UserSubscription us = userSubscriptionRepository.findById(userSubscriptionId)
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        // Only ACTIVE or SUSPENDED subscriptions can be renewed
        if (us.getStatus() != UserSubscriptionStatus.ACTIVE &&
                us.getStatus() != UserSubscriptionStatus.SUSPENDED) {
            throw new RuntimeException("Subscription cannot be renewed in its current state");
        }

        // Extend end date by the original subscription validity
        int validityDays = us.getSubscription().getValidityDays();
        us.setEndDate(us.getEndDate().plusDays(validityDays));

        // Optionally reset ticket count
        us.setNumberTicketsLeft(us.getSubscription().getTotalTickets());

        // If it was suspended, activate it
        if (us.getStatus() == UserSubscriptionStatus.SUSPENDED) {
            us.setStatus(UserSubscriptionStatus.ACTIVE);
        }

        return UserSubscriptionMapper.toResponse(userSubscriptionRepository.save(us));
    }

    @Transactional
    public UserSubscriptionResponse updateUserSubscription(UUID userSubscriptionId,
                                                           UserSubscriptionStatus status,
                                                           Integer numberTicketsLeft,
                                                           LocalDate endDate) {
        UserSubscription us = userSubscriptionRepository.findById(userSubscriptionId)
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        if (status != null) us.setStatus(status);
        if (numberTicketsLeft != null) us.setNumberTicketsLeft(numberTicketsLeft);
        if (endDate != null) us.setEndDate(endDate);

        return UserSubscriptionMapper.toResponse(userSubscriptionRepository.save(us));
    }

    @Transactional
    public void deleteUserSubscription(UUID userSubscriptionId) {
        if (!userSubscriptionRepository.existsById(userSubscriptionId)) {
            throw new RuntimeException("User subscription not found");
        }
        userSubscriptionRepository.deleteById(userSubscriptionId);
    }



    private String generateQrCode(UUID uuid) {
        try {
            String content = uuid.toString();

            int width = 300;
            int height = 300;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] pngData = baos.toByteArray();

            return Base64.getEncoder().encodeToString(pngData);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR Code", e);
        }
    }

    private String decodeQrCode(String base64Qr) throws IOException, NotFoundException {
        byte[] bytes = Base64.getDecoder().decode(base64Qr);
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();
    }

    public List<Object[]> countSubscriptionsLastNYears(int years) {
        LocalDate startDate = LocalDate.now().minusYears(years).withDayOfMonth(1);

        return userSubscriptionRepository.countSubscriptionsByMonthSince(startDate);
    }

    public List<MonthlySubscriptionCount> getMonthlyStats(int years) {
        List<Object[]> rows = countSubscriptionsLastNYears(years);
        HashMap<Integer,Long> cnt = new HashMap<Integer,Long>();

        for (Object[] row : rows) {
            Integer month = (Integer) row[1];
            Long count = (Long) row[2];

            cnt.put(month, cnt.getOrDefault(month, 0L) + count);
        }
        return cnt.entrySet().stream()
                .map(entry -> new MonthlySubscriptionCount(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(MonthlySubscriptionCount::month))
                .toList();
    }

    @Transactional
    public SubscriptionCountLastTwoMonths getSubscriptionComparisonLastTwoMonths() {

        LocalDate today = LocalDate.now();

        LocalDate startOfThisMonth = today.withDayOfMonth(1);
        LocalDate startOfNextMonth = startOfThisMonth.plusMonths(1);

        long thisMonthCount = userSubscriptionRepository.countBetween(
                startOfThisMonth, startOfNextMonth
        );

        LocalDate startOfLastMonth = startOfThisMonth.minusMonths(1);

        long lastMonthCount = userSubscriptionRepository.countBetween(
                startOfLastMonth, startOfThisMonth
        );

        return new SubscriptionCountLastTwoMonths(thisMonthCount, lastMonthCount);
    }



}
