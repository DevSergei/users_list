package me.bkkn.users.user;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Subcomponent(modules = {UserModule.class})
@Singleton
public interface UserComponent {
    void injectUserFragment(UsersFragment usersFragment);
}
