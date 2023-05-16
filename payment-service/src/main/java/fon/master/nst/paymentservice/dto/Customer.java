package fon.master.nst.paymentservice.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long customerId;
    private String username;
    private Long credit;
    private String email;
    private String currentAccount;
    private String firstName;
    private String lastName;

}
