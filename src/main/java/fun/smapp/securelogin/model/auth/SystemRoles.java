package fun.smapp.securelogin.model.auth;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "system_roles")
public class SystemRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SYSTEM_ROLES_CONSTANT")
    private String rolesConstant;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "roles"
    )
    private Set<SystemUser> users;

    public SystemRoles() { }

    public Long getId() {
        return id;
    }

    public String getRolesConstant() {
        return rolesConstant;
    }

    public Set<SystemUser> getUsers() {
        return users;
    }

    public SystemRoles setRolesConstant(String rolesConstant) {
        this.rolesConstant = rolesConstant;
        return this;
    }

    public SystemRoles setUsers(Set<SystemUser> users) {
        this.users = users;
        return this;
    }
}
