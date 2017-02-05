Codechat is a social media website under construction.
It has functional friend requests, friends and admins.
Forum functionality is not implemented yet. Anyway, it
has well enough functionality for several vulnerabilities ;)

Three (3) user accounts have been created, for testing purposes.
Name    Password    Role
user1   foo         USER
user2   bar         USER
admin1  admin       ADMIN

More user account with USER role can be created using the form
on the `/main` page. ADMIN users should have all the same rights
as USER users, and additionally they should be able to access
the admin functionality (see all username's and change anyone's
password) in `/admin` path.

Codechat values privacy highly so regular users should not be able to
find out usernames or other data of other users who are not their
friends. However, in reality everyone can find out all the usernames,
and find out who are friends with who.

Currently there isn't any interesting functionality related to forums.
However, of course it's possible you to find some vulnerabilities there
too, but there are no designed vulnerabilities related to requests of
paths `/forums` or `/forums/{id}`.

Codechat provides the following vulnerabilities of
[OWASP's top 10 2013 list.](https://www.owasp.org/index.php/Top_10_2013-Top_10)

1. XSS in unescaped friend request messages.
2. Insecure direct object references to personal user pages
   of all users.
3. CSRF in unescaped friend request messages.
4. Missing function level access control in `/admin` path,
   so admin functionality in `/admin` path is available to
   anyone requesting the path `/admin`.
5. Unsecure authentication in username and password are
   sent unencrypted in a GET request.

How to get started:
1. Clone the repository: `git clone https://github.com/nrz/cybersecuritybase-project`.
2. Open the recently cloned repository in some Maven-aware IDE, eg. Netbeans.
   or, alternatively, run `mvn package` to build a jar.

Vulnerability #1: A3-Cross-Site Scripting (XSS).

Steps to reproduce:
1. Clean and build project.
2. Run project.
3. Enter [http://127.0.0.1:8080](http://127.0.0.1:8080).
4. Login as `user1` with password `foo`.
5. Write in field "Friend's username" the string `user2`.
6. Write in field "Message for friend request" the string
   `<script>alert('xss!')</script>`.
7. Logout by pressing the "Logout" button.
8. Login as `user2` with password `bar`.
9. Javascript alert box with message "xss!" appears on screen.

How to fix: Change `th:utext` into `th:text` in
[src/main/resources/templates/main.html](src/main/resources/templates/main.html).

Note: the above fix also fixes the the CSRF vulnerability,
because the below CSRF is done using XSS. So I recommend to
fix XSS only after testing and fixing CSRF.

Note: In Linux/Unix systems this can be located easily with `grep`:
`grep -R '\<th:utext\>'`.

Vulnerability #2: A4-Insecure Direct Object References.

Steps to reproduce:
1. Clean and build project.
2. Run project.
3. Enter [http://127.0.0.1:8080](http://127.0.0.1:8080).
4. Login as `user1` with password `foo`.
5. Access the personal page of `user2` (not a friend of `user1` by
   default) by accessing the page `http://127.0.0.1:8080/persons/2` or
   the page `http://localhost:8080/persons/2`.

How to fix: Add the following lines in
[src/main/java/codechat/controller/PersonController.java](src/main/java/codechat/controller/PersonController.java`),
beginning from line 69:

if (!authenticatedPerson.getMyFriends().contains(friend)) {
    // You are not authorized to see person pages of
    // people who are not your friends in Codechat!
    return "redirect:/main";
}

The above lines check that the person whose page you are requesting
is a friend of yours in Codechat. Codechat values privacy highly and
should not show any personal data to users who are not your friends.

Vulnerability #3: A8-Cross-Site Request Forgery (CSRF).

Steps to reproduce:
 1. Make sure that you have not fixed the vulnerability #1
    (A3-Cross-Site Scripting (XSS)).
 2. Clean and build project.
 3. Run project.
 4. Enter [http://127.0.0.1:8080](http://127.0.0.1:8080).
 5. Login as `user1` with password `foo`.
 6. Write in field "Friend's username" the string `user2`.
 7. Write in field "Message for friend request" the string
    `<script>window.location.href='/logout'</script>`.
    If you want an an alert box to be sure that logging out was caused
    by a malicious malicious script (XSS), you can instead write the
    following string in field "Message for friend request":
    `<script>window.location.href='/logout'; alert('you have been logged out!')</script>`.
 8. Logout by pressing the "Logout" button.
 9. Login as `user2` with password `bar`.
10. `user2` gets immediately logged out and gets always logged
    immediately out as long as the malicious friend request remains.

How to fix: delete the command `http.csrf().disable();` from
[src/main/java/codechat/config/SecurityConfiguration.java](src/main/java/codechat/config/SecurityConfiguration.java).

Vulnerability #4: A7-Missing Function Level Access Control.

Steps to reproduce:
 1. Clean and build project.
 2. Run project.
 3. Enter [http://127.0.0.1:8080](http://127.0.0.1:8080).
 4. Login as `user1` with password `foo`.
 5. Enter admin page by requesting `http://127.0.0.1:8080/admin` or
    `http://localhost:8080/admin`.
 6. Click on link "Change password for user: user2", which takes you to
    [http://127.0.0.1:8080/admin/persons/2/newpassword](http://127.0.0.1:8080/admin/persons/2/newpassword) or
    [http://localhost:8080/admin/persons/2/newpassword](http://localhost:8080/admin/persons/2/newpassword).
 7. Write a password in the "New password" field.
 8. Click on "Change user's password" button. The password is changed.
 9. Logout by clicking the logout button.
10. You can now login as `user2` with the password you just set while
    logged in as user1, who is not an admin.

How to fix: change the line `.antMatchers("/admin/**").hasAnyAuthority("USER");`
to `.antMatchers("/admin/**").hasAnyAuthority("ADMIN");` in file
[src/main/java/codechat/config/SecurityConfiguration.java](src/main/java/codechat/config/SecurityConfiguration.java).

Vulnerability #5. A2-Broken Authentication And Session Management.

Steps to reproduce:
1. Install Postman and Postman Interceptor.
2. Clean and build project.
3. Run project.
4. Enter [http://127.0.0.1:8080](http://127.0.0.1:8080).
5. Click Postman icon in Chrome plugin row.
6. Turn Request Capture ON in Postman Interceptor.
7. Login as `user1` with password `foo`.
8. Click Postman icon in Chrome plugin row to see latest requests.
9. The unencrypted password was sent as a part of the URL in a GET
   request:
   `GET [http://127.0.0.1:8080/login?username=user1&password=foo](http://127.0.0.1:8080/login?username=user1&password=foo).

How to fix: change the line `<form action="#" th:action="@{/login}" method="GET">`
in `main.html` to `<form action="#" th:action="@{/login}" method="POST">` in file
[src/main/resources/templates/main.html](src/main/resources/templates/main.html).

My code: copyright (C) 2017 Antti Nuortimo. License is GPL3, or (at your option),
any later version. See [COPYING](copying) file for license.
