package shipping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.exception.CustomServiceException;
import shipping.model.User;
import shipping.service.api.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findUserByEmail(email);

        } catch (CustomServiceException e) {
            e.printStackTrace();
        }

            if (user == null) {
                throw new UsernameNotFoundException("No users with this email " + email);
            }

            String userEmail = user.getEmail();
            String password = user.getPassword();
            String role = user.getRole().toString();

            List<SimpleGrantedAuthority> authList = getAuthorities(role);

            return new org.springframework.security.core.userdetails.User(userEmail, password, true, true, true, true, authList);

    }


    private List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(role));
        return auths;
    }
}
