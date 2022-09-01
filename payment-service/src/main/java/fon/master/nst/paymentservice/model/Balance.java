package fon.master.nst.paymentservice.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "balance")
public class Balance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long balanceId;
    private String username;
    private Long credit;

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "balanceId=" + balanceId +
                ", username='" + username + '\'' +
                ", credit=" + credit +
                '}';
    }
}
