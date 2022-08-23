package fon.master.nst.dto;

public class MailResponse {

    private MailStatus mailStatus;

    public MailResponse(MailStatus mailStatus) {
        this.mailStatus = mailStatus;
    }

    public MailStatus getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(MailStatus mailStatus) {
        this.mailStatus = mailStatus;
    }
}
