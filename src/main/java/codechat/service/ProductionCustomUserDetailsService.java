package codechat.service;

import codechat.domain.Person;
import codechat.repository.PersonRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Profile("production")
@Service
public class ProductionCustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("username must not be null!");
        }

        if (username.isEmpty()) {
            throw new UsernameNotFoundException("username must not be an empty string!");
        }

        Person person = this.personRepository.findByUsername(username);
        String password = person.getPassword();

        if (person == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        if (password == null || password.equals("")) {
            throw new UsernameNotFoundException("Set a password!");
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
