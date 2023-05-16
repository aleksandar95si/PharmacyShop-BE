package fon.master.nst.paymentservice.repository;

import fon.master.nst.paymentservice.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Balance findByUsername(String username);

    Balance findByAccountNumber(String accountNumber);

}
