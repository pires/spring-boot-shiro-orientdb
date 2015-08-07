package com.github.pires.example.shiro;

import com.github.pires.example.model.Permission;
import com.github.pires.example.model.Role;
import com.github.pires.example.model.User;
import com.github.pires.example.repository.UserRepository;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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
        // retrieve role names and permission names
        final String email = (String) principals.getPrimaryPrincipal();
        final User user = userRepository.findByEmailAndActive(email, true);
        if (user == null) {
            throw new UnknownAccountException("Account does not exist");
        }
        final int totalRoles = user.getRoles().size();
        final Set<String> roleNames = new LinkedHashSet<>(totalRoles);
        final Set<String> permissionNames = new LinkedHashSet<>();
        if (totalRoles > 0) {
            for (Role role : user.getRoles()) {
                roleNames.add(role.getName());
                for (Permission permission : role.getPermissions()) {
                    permissionNames.add(permission.getName());
                }
            }
        }
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissionNames);
        return info;
    }

}
