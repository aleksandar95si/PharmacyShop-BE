package fon.master.nst.paymentservice.repository;

import fon.master.nst.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Payment findByUsername(String username);

}
