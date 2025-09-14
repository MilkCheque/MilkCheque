package com.whoami.milkcheque.service;

import com.whoami.milkcheque.config.PaymobConfig;
import com.whoami.milkcheque.exception.PaymobException;
import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.PaymentModel;
import com.whoami.milkcheque.repository.CustomerOrderRepository;
import com.whoami.milkcheque.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

  private final PaymobConfig paymobConfig;
  private final RestTemplate restTemplate = new RestTemplate();
  private final PaymentRepository paymentRepository;
  private final CustomerOrderRepository customerOrderRepository;

  public PaymentService(
      PaymobConfig paymobConfig,
      PaymentRepository paymentRepository,
      CustomerOrderRepository customerOrderRepository) {
    this.paymobConfig = paymobConfig;
    this.paymentRepository = paymentRepository;
    this.customerOrderRepository = customerOrderRepository;
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
    requestBody.put("callback_url", "https://6971e220c85c.ngrok-free.ap/payments/callback");
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

  public ResponseEntity<String> paymob(Integer amountCents, String merchantOrderId, String email) {
    String authToken = authenticate();
    Integer orderId = registerOrder(authToken, amountCents, merchantOrderId);
    String paymentToken = generatePaymentKey(authToken, orderId, amountCents, email);
    String iframe = getIframeUrl(paymentToken);
    return ResponseEntity.ok().body(iframe);
  }

  //    public ResponseEntity<String> processCallBack(Map<String, Object> payload ){
  //        try {
  //            Map<String, Object> obj = (Map<String, Object>) payload.get("obj");
  //            if(obj==null){
  //                return ResponseEntity.badRequest().body("Missing obj in callback");
  //            }
  //            Map<String, Object> order = (Map<String, Object>) obj.get("order") ;
  //
  //
  //            Integer amountCents= Integer.valueOf(obj.get("amount_cents").toString());
  //            String currency= obj.get("currency").toString();
  //            String success =  obj.get("success").toString();
  //            String transactionId = (obj.get("id").toString());
  //            Long orderId = Long.valueOf(obj.get("order_id").toString());
  //            Integer customer_order_id=
  // Integer.valueOf(order.get("merchant_order_id").toString());
  //
  //            String paymobOrderId=order.get("id").toString();
  //
  //
  //            String createdAtISO = order.get("created_at").toString();
  //            DateTimeFormatter formatter =
  // DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
  //            LocalDateTime createdAt = LocalDateTime.parse(createdAtISO, formatter);
  //
  //            String status;
  //            if (success != null && success.equalsIgnoreCase("true")) {
  //                 status="success";
  //            } else {
  //                 status="fail";
  //            }
  //            CustomerOrderModel customerOrder = customerOrderRepository.findById(orderId)
  //                    .orElseThrow(() -> new PaymobException("Order not found"));
  //            PaymentModel paymentModel = new PaymentModel();
  //            paymentModel.setAmountCents(amountCents);
  //            paymentModel.setCurrency(currency);
  //            paymentModel.setPaymentToken(paymobOrderId);
  //            paymentModel.setStatus(status);
  //            paymentModel.setTransactionId(transactionId);
  //            paymentModel.setCreatedAt(createdAt);
  //            paymentModel.setCustomerOrderModel(customerOrder);
  //
  //
  //
  //            paymentRepository.save(paymentModel);
  //            return ResponseEntity.ok().body("success");
  //        }
  //        catch (Exception e){
  //            throw new PaymobException(e.getMessage());
  //        }
  //
  //
  //
  //        }
  public ResponseEntity<String> processCallBack(Map<String, Object> payload) {
    try {
      Map<String, Object> obj = (Map<String, Object>) payload.get("obj");
      if (obj == null) {
        return ResponseEntity.badRequest().body("Missing obj in callback");
      }

      // Extract nested order
      Map<String, Object> order = (Map<String, Object>) obj.get("order");
      if (order == null) {
        return ResponseEntity.badRequest().body("Missing order in callback");
      }

      // Parse fields safely
      Integer amountCents = Integer.valueOf(obj.get("amount_cents").toString());
      String currency = obj.get("currency").toString();
      Boolean success = Boolean.valueOf(obj.get("success").toString());
      String transactionId = obj.get("id").toString();

      // merchant_order_id = your system’s order ID
      Long merchantOrderId = Long.valueOf(order.get("merchant_order_id").toString());
      String paymobOrderId = order.get("id").toString();

      // Handle created_at date with flexible formatter
      String createdAtISO = order.get("created_at").toString();
      LocalDateTime createdAt;
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        createdAt = LocalDateTime.parse(createdAtISO, formatter);
      } catch (Exception ex) {
        DateTimeFormatter fallback = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        createdAt = LocalDateTime.parse(createdAtISO, fallback);
      }

      String status = success ? "success" : "fail";

      // Find customer order by merchant_order_id (not paymob order_id)
      CustomerOrderModel customerOrder =
          customerOrderRepository
              .findById(merchantOrderId)
              .orElseThrow(
                  () -> new PaymobException("Order not found with id: " + merchantOrderId));

      // Save payment
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
      e.printStackTrace(); // log full stack trace
      throw new PaymobException("Callback processing failed: " + e.getMessage());
    }
  }
}
