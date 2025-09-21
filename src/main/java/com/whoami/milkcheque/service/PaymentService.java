package com.whoami.milkcheque.service;

import com.whoami.milkcheque.config.PaymobConfig;
import com.whoami.milkcheque.dto.request.PaymentRequest;
import com.whoami.milkcheque.exception.PaymobException;
import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.LinkedOrderModel;
import com.whoami.milkcheque.model.PaymentModel;
import com.whoami.milkcheque.repository.CustomerOrderRepository;
import com.whoami.milkcheque.repository.LinkedOrderRepository;
import com.whoami.milkcheque.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PaymentService {

  private final PaymobConfig paymobConfig;
  private final RestTemplate restTemplate = new RestTemplate();
  private final PaymentRepository paymentRepository;
  private final CustomerOrderRepository customerOrderRepository;
  private final LinkedOrderRepository linkedOrderRepository;

  public PaymentService(
      PaymobConfig paymobConfig,
      PaymentRepository paymentRepository,
      CustomerOrderRepository customerOrderRepository,
      LinkedOrderRepository linkedOrderRepository) {
    this.paymobConfig = paymobConfig;
    this.paymentRepository = paymentRepository;
    this.customerOrderRepository = customerOrderRepository;
    this.linkedOrderRepository = linkedOrderRepository;
  }

  private String authenticate() {
    String url = "https://accept.paymob.com/api/auth/tokens";

    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("api_key", paymobConfig.getApiKey());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

    Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
    return response != null ? (String) response.get("token") : null;
  }

  private Integer registerOrder(String authToken, Integer amountCents, String merchantOrderId) {
    String url = "https://accept.paymob.com/api/ecommerce/orders";

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("auth_token", authToken);
    requestBody.put("delivery_needed", false);
    requestBody.put("amount_cents", amountCents);
    requestBody.put("currency", "EGP");
    requestBody.put("merchant_order_id", merchantOrderId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
    return response != null ? (Integer) response.get("id") : null;
  }

  private String generatePaymentKey(
      String authToken, Integer orderId, Integer amountCents, String email) {
    String url = "https://accept.paymob.com/api/acceptance/payment_keys";

    Map<String, Object> billingData = new HashMap<>();
    billingData.put("apartment", "NA");
    billingData.put("email", email);
    billingData.put("floor", "NA");
    billingData.put("first_name", "Test");
    billingData.put("street", "NA");
    billingData.put("building", "NA");
    billingData.put("phone_number", "+201234567890");
    billingData.put("shipping_method", "NA");
    billingData.put("postal_code", "NA");
    billingData.put("city", "Cairo");
    billingData.put("country", "EG");
    billingData.put("last_name", "User");
    billingData.put("state", "Cairo");

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("auth_token", authToken);
    requestBody.put("amount_cents", amountCents);
    requestBody.put("order_id", orderId);
    requestBody.put("billing_data", billingData);
    requestBody.put("currency", "EGP");
    requestBody.put("integration_id", paymobConfig.getIntegrationId());
    requestBody.put(
        "callback_url",
        "https://milkchequeapp-fzemegbdh9hgbkhf.spaincentral-01.azurewebsites.net/payments/callback");
    requestBody.put("expiration", 3600);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
    return response != null ? (String) response.get("token") : null;
  }

  private String getIframeUrl(String paymentToken) {
    return "https://accept.paymob.com/api/acceptance/iframes/"
        + paymobConfig.getIframeId()
        + "?payment_token="
        + paymentToken;
  }

  public ResponseEntity<String> paymob(PaymentRequest paymentRequest) {
    try {
      Integer amountCents = paymentRequest.getAmountCents();
      String merchantOrderId = paymentRequest.getMerchantOrderId();
      String email = paymentRequest.getEmail();
      ArrayList<Long> otherMerchantsOrderId = paymentRequest.getOtherMerchantsOrderId();

      if (isOrderPaid(merchantOrderId)) {
        throw new PaymobException("Order" + merchantOrderId + "is already paid");
      }

      // TODO: create its own method that is disgusting
      if (!otherMerchantsOrderId.isEmpty()) {
        for (Long orderId : otherMerchantsOrderId) {
          boolean exists = customerOrderRepository.existsById(orderId);
          boolean isOrderPaid = isOrderPaid(orderId.toString());
          if (!exists) {
            throw new PaymobException("Order with ID " + orderId + " does not exist");
          }
          if (isOrderPaid) {
            throw new PaymobException("Order with ID " + orderId + " is already paid");
          }
          String joinedIds =
              otherMerchantsOrderId.stream().map(String::valueOf).collect(Collectors.joining(","));
          LinkedOrderModel linkedOrderModel = new LinkedOrderModel(merchantOrderId, joinedIds);
          linkedOrderRepository.save(linkedOrderModel);
        }
      }

      String authToken = authenticate();
      Integer orderId = registerOrder(authToken, amountCents, merchantOrderId);
      String paymentToken = generatePaymentKey(authToken, orderId, amountCents, email);
      String iframe = getIframeUrl(paymentToken);
      return ResponseEntity.ok().body(iframe);
    } catch (PaymobException e) {
      throw new PaymobException(e.getMessage());
    }
  }

  public boolean isOrderPaid(String merchantOrderId) {
    Long orderId = Long.valueOf(merchantOrderId);
    CustomerOrderModel order =
        customerOrderRepository
            .findById(orderId)
            .orElseThrow(() -> new PaymobException("Order not found with id: " + merchantOrderId));

    return order.getPaid();
  }

  // TODO: CLEAN THIS FUNCTION IT SUCKS
  public ResponseEntity<String> processCallBack(Map<String, Object> payload) {
    log.info("process Call back >>>>>>>>>>>>>>>>> ");

    try {
      Map<String, Object> obj = (Map<String, Object>) payload.get("obj");
      if (obj == null) {
        return ResponseEntity.badRequest().body("Missing obj in callback");
      }

      Map<String, Object> order = (Map<String, Object>) obj.get("order");
      if (order == null) {
        return ResponseEntity.badRequest().body("Missing order in callback");
      }

      log.info("value of status from payload: ", obj.get("success"));

      Integer amountCents = Integer.valueOf(obj.get("amount_cents").toString());
      String currency = obj.get("currency").toString();
      Boolean isSuccess = (Boolean) obj.get("success");
      String transactionId = obj.get("id").toString();
      Long merchantOrderId = Long.valueOf(order.get("merchant_order_id").toString());
      String paymobOrderId = order.get("id").toString();
      String createdAtISO = order.get("created_at").toString();
      LocalDateTime createdAt;
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        createdAt = LocalDateTime.parse(createdAtISO, formatter);
      } catch (Exception ex) {
        DateTimeFormatter fallback = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        createdAt = LocalDateTime.parse(createdAtISO, fallback);
      }

      String status = isSuccess ? "success" : "fail";
      CustomerOrderModel customerOrder =
          customerOrderRepository
              .findById(merchantOrderId)
              .orElseThrow(
                  () -> new PaymobException("Order not found with id: " + merchantOrderId));

      if (isSuccess) {
        log.info("----------------------------------in conditions ---------------");
        customerOrder.setPaid(true);
        customerOrderRepository.save(customerOrder);

        LinkedOrderModel linkedOrders =
            linkedOrderRepository.findByMerchantOrderId(merchantOrderId);

        if (linkedOrders != null && linkedOrders.getLinkedOrdersId() != null) {
          String[] ids = linkedOrders.getLinkedOrdersId().split(",");

          for (String id : ids) {
            Long orderId = Long.valueOf(id);

            CustomerOrderModel otherOrderId =
                customerOrderRepository.findById(orderId).orElse(null);
            if (otherOrderId != null && !otherOrderId.getPaid()) {
              otherOrderId.setPaid(true);
              customerOrderRepository.save(otherOrderId);
            }
          }
        }
      }

      PaymentModel paymentModel = new PaymentModel();
      paymentModel.setAmountCents(amountCents);
      paymentModel.setCurrency(currency);
      paymentModel.setPaymentToken(paymobOrderId);
      paymentModel.setStatus(status);
      paymentModel.setTransactionId(transactionId);
      paymentModel.setCreatedAt(createdAt);
      paymentModel.setCustomerOrderModel(customerOrder);

      paymentRepository.save(paymentModel);

      return ResponseEntity.ok("success");
    } catch (Exception e) {
      e.printStackTrace();
      throw new PaymobException("Callback processing failed: " + e.getMessage());
    }
  }
}
