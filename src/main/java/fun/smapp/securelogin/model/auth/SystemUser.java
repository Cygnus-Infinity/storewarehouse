package fun.smapp.securelogin.model.auth;

import fun.smapp.securelogin.model.utility.Activation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "system_user")
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_ENCRYPTED_PASSWORD")
    private String encryptedPassword;

    @Transient
    private String confirmPassword;

    @NotNull
    @Column(name = "USER_IS_ACTIVE")
    private Boolean isActive = false;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "system_user_roles",
            joinColumns = { @JoinColumn(name = "SYSTEM_USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "SYSTEM_ROLES_ID") }
    )
    private Set<SystemRoles> roles;

    @OneToOne(cascade=CascadeType.ALL, mappedBy = "systemUser")
    private Activation activation;

    public SystemUser() {
        this.activation = new Activation();
        this.activation.setSystemUser(this);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Set<SystemRoles> getRoles() {
        return roles;
    }

    public Activation getActivation() {
        return activation;
    }

    public SystemUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public SystemUser setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
        return this;
    }

    public SystemUser setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public SystemUser setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public SystemUser setRoles(Set<SystemRoles> roles) {
        this.roles = roles;
        return this;
    }

    public SystemUser setActivation(Activation activation) {
        this.activation = activation;
        return this;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", isActive=" + isActive +
                ", roles=" + roles +
                '}';
    }
}
