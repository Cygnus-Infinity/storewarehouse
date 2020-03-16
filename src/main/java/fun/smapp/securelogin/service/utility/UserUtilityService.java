package fun.smapp.securelogin.service.utility;

import fun.smapp.securelogin.model.auth.SystemUser;
import fun.smapp.securelogin.repository.auth.RolesRepository;
import fun.smapp.securelogin.repository.auth.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserUtilityService implements User {

    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Dependency constructor for UserRepository, RoleRepository, BcryptPasswordEncoder
     * @param userRepository
     */
    public UserUtilityService(UserRepository userRepository, RolesRepository rolesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * This method will create a new user to the system.
     *
     * @param user
     */
    @Override
    public void save(SystemUser user) {
        if (user != null) {
            user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getEncryptedPassword()));
            user.setRoles(new HashSet<>(rolesRepository.findAll()));
            userRepository.save(user);
        }
    }

    /**
     * This method will find a user by username
     *
     * @param userName
     * @return
     */
    @Override
    public SystemUser findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
