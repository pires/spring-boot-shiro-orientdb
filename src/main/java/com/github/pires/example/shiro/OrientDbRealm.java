package com.github.pires.example.shiro;

import com.github.pires.example.model.User;
import com.github.pires.example.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shiro authentication & authorization realm that relies on OrientDB as
 * datastore.
 */
@Component
public class OrientDbRealm extends AuthorizingRealm {

  @Autowired
  private UserRepository userRepository;

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(
      final AuthenticationToken token)
      throws AuthenticationException {
    final UsernamePasswordToken credentials = (UsernamePasswordToken) token;
    final String email = credentials.getUsername();
    if (email == null) {
      throw new UnknownAccountException("Email not provided");
    }
    final User user = userRepository.findByEmailAndActive(email, true);
    if (user == null) {
      throw new UnknownAccountException("Account does not exist");
    }
    return new SimpleAuthenticationInfo(email, user.getPassword().toCharArray(),
        ByteSource.Util.bytes(email), getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(
      final PrincipalCollection principals) {
    // TODO
    return null;
  }

}
