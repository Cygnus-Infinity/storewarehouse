package fun.smapp.securelogin.service.auth;

import fun.smapp.securelogin.model.auth.SystemRoles;
import fun.smapp.securelogin.model.auth.SystemUser;
import fun.smapp.securelogin.repository.auth.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SystemUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    /**
     * This Constructor is used to AutoWired the UserRepository Dependency
     * @param userRepository
     */
    public SystemUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // Find the User name either throw Exception
        SystemUser user = userRepository.findByUserName(userName);

        if (user == null) throw new UsernameNotFoundException(userName);

        // Set the Roles for auth user
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (SystemRoles roles : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", roles.getRolesConstant())));
        }

        return new User(user.getUserName(), user.getEncryptedPassword(), user.getActive(), true, true, true, grantedAuthorities);
    }
}
