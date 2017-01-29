package codechat.service;

import codechat.domain.Forum;
import codechat.domain.Person;
import codechat.repository.ForumRepository;
import codechat.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class ForumService {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ForumRepository forumRepository;

    public String createForum(Model model, Forum forum, BindingResult bindingResult) {
        boolean hasErrors = false;

        List<String> errors = new ArrayList<>();

        if (forum == null || bindingResult == null || bindingResult.hasErrors()) {
            errors.add("Error in forum creation.");
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("errors", errors);
            return "redirect:/main";
        }

        this.forumRepository.save(forum);

        Person person = this.personService.getAuthenticatedPerson();
        this.addAdminToForum(person, forum);
        this.addMemberToForum(person, forum);

        // TODO: Redirect to the newly created forum.
        return "redirect:/main";
    }

    public void addMemberToForum(Person member, Forum forum) {
        if (!forum.getForumMembers().contains(member)) {
            forum.addForumMember(member);
        }

        if (!member.getMemberForums().contains(forum)) {
            member.addMemberForum(forum);
        }
        this.personRepository.save(member);
        this.forumRepository.save(forum);
    }

    public void addAdminToForum(Person admin, Forum forum) {
        if (!forum.getForumAdmins().contains(admin)) {
            forum.addForumAdmin(admin);
        }

        if (!admin.getAdminForums().contains(forum)) {
            admin.addAdminForum(forum);
        }
        this.personRepository.save(admin);
        this.forumRepository.save(forum);
    }
}
