package codechat.service;

import codechat.domain.Person;
import codechat.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class PersonService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AuthenticationManager authManager;

    public String addPerson(
            Model model,
            Person person,
            BindingResult bindingResult) {
        boolean hasErrors = false;

        List<String> errors = new ArrayList<>();

        if (person == null || bindingResult == null || bindingResult.hasErrors()) {
            errors.add("Error in login.");
            hasErrors = true;
        }

        if (person != null && this.personRepository.findByUsername(person.getUsername()) != null) {
            // Occupied username!
            errors.add("The username is already in use!");
            hasErrors = true;
        }

        if (hasErrors || person == null) {
            model.addAttribute("errors", errors);
            return "registrationform";
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(person.getUsername(), person.getPassword());

        person.setUnencryptedPassword(person.getPassword());

        // Set the bcrypt-encrypted password.
        person.setPassword(this.passwordEncoder.encode(person.getPassword()));
        person.setUserRole("USER");

        // Save the new Person in the repository.
        this.personRepository.save(person);

        // Authenticate the new Person.
        Authentication auth = this.authManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/main";
    }

    public Person getAuthenticatedPerson() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Person person = this.personRepository.findByUsername(auth.getName());
            return person;
        }
        return null;
    }
}
