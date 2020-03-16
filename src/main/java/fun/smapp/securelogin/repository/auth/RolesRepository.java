package fun.smapp.securelogin.repository.auth;

import fun.smapp.securelogin.model.auth.SystemRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<SystemRoles, Long> {
}
