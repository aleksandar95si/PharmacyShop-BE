package fon.master.nst.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fon.master.nst.userservice.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    void deleteByUsername(String username);

}
