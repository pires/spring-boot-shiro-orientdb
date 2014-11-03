package com.github.pires.example.rest;

import org.apache.shiro.mgt.SecurityManager;
import com.github.pires.example.model.User;
import com.github.pires.example.repository.UserRepository;
import java.util.List;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO add description
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private static final Logger log = LoggerFactory.
      getLogger(UserController.class);

  @Autowired
  private SecurityManager sm;

  @Autowired
  private DefaultPasswordService passwordService;

  @Autowired
  private UserRepository userRepository;

  @RequestMapping(value = "/auth", method = POST)
  public void authenticate(@RequestBody final UsernamePasswordToken credentials) {
    final Subject subject = new Subject.Builder(sm).buildSubject();
    subject.login(credentials);
    // set attribute that will allow session querying
    subject.getSession().setAttribute("email", credentials.getUsername());
  }

  @RequestMapping(method = GET)
  @RequiresAuthentication
  public List<User> getAll() {
    return userRepository.findAll();
  }

//  @RequestMapping(value = "/created_between", method = GET)
//  public List<User> getAllCreatedBetween(@RequestParam long start,
//      @RequestParam long end) {
//    return smRepo.findByTimestampBetween(start, end);
//  }
  @RequestMapping(method = PUT)
  public void put(@RequestBody User newUser) {
    log.info("Received new user request -> {}", newUser);

    // validate email is not yet registered
    if (userRepository.findByEmail(newUser.getEmail()) != null) {
      throw new EmaiAlreadyInUseException();
    }

    // store into orientdb
    newUser.setCreated(System.currentTimeMillis());
    newUser.setActive(true);
    newUser.setPassword(passwordService.encryptPassword(newUser.getPassword()));
    userRepository.save(newUser);
  }

}
