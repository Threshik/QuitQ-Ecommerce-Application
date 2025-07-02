package com.hexaware.quitQ_backend_1.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Order;
import com.hexaware.quitQ_backend_1.entities.Payment;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.PaymentMethod;
import com.hexaware.quitQ_backend_1.enums.PaymentStatus;


public interface PaymentRepository extends JpaRepository<Payment, Long>{
	Optional<Payment> findByOrder(Order order);
	List<Payment> findByOrderUser(User user);
	List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
	List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);
	List<Payment> findByPaymentDateAfter(LocalDateTime date);
	List<Payment> findByPaymentDateBefore(LocalDateTime date);

}
