package codechat.service;

import codechat.domain.Forum;
import codechat.domain.Person;
import codechat.repository.ForumRepository;
import codechat.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ForumRepository forumRepository;

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
