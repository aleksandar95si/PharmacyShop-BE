package fon.master.nst.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fon.master.nst.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
