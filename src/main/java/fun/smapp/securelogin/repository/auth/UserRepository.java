package fun.smapp.securelogin.repository.auth;

import fun.smapp.securelogin.model.auth.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, Long> {

    /**
     * This method will used by AuthenticationManager for Spring Security
     * @param username
     * @return SystemUser
     */
    public SystemUser findByUserName(String username);
}
