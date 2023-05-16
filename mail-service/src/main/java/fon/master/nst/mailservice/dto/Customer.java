package fon.master.nst.mailservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Customer implements Serializable {

    private Long customerId;
    private String username;
    private Long credit;
    private String email;
    private String currentAccount;
    private String firstName;
    private String lastName;

}
