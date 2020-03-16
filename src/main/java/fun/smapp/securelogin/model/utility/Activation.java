package fun.smapp.securelogin.model.utility;

import fun.smapp.securelogin.model.auth.SystemUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_activation")
public class Activation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACTIVATION_TOKEN")
    private String activationToken;

    @Column(name = "VALID_UPTO")
    private LocalDateTime validUpto;

    @Column(name = "ISSUE_DATE")
    private LocalDateTime issueDate;

    @Column(name = "CONFIRM_DATE")
    private LocalDateTime confirmDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SYSTEM_USER_ID", referencedColumnName = "ID")
    private SystemUser systemUser;

    public Activation() {
    }

    public Long getId() {
        return id;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public LocalDateTime getValidUpto() {
        return validUpto;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public LocalDateTime getConfirmDate() {
        return confirmDate;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public Activation setActivationToken(String activationToken) {
        this.activationToken = activationToken;
        return this;
    }

    public Activation setValidUpto(LocalDateTime validUpto) {
        this.validUpto = validUpto;
        return this;
    }

    public Activation setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public Activation setConfirmDate(LocalDateTime confirmDate) {
        this.confirmDate = confirmDate;
        return this;
    }

    public Activation setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    @Override
    public String toString() {
        return "Activation{" +
                "id=" + id +
                ", activationToken='" + activationToken + '\'' +
                ", validUpto=" + validUpto +
                ", issueDate=" + issueDate +
                ", confirmDate=" + confirmDate +
                ", systemUser=" + systemUser +
                '}';
    }
}
