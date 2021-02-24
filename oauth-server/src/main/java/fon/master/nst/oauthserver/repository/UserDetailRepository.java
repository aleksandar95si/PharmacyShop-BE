package fon.master.nst.oauthserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fon.master.nst.oauthserver.model.User;

@Repository
public interface UserDetailRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
	
}
