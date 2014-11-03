/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
