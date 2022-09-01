package fon.master.nst.oauthserver.repository;

import fon.master.nst.oauthserver.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    void deleteByUsername(String username);

}
