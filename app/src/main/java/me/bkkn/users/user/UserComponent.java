package me.bkkn.users.user;

import javax.inject.Singleton;

import dagger.Subcomponent;
import me.bkkn.users.di.PerFragment;

@PerFragment
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {
    void injectUserFragment(UsersFragment usersFragment);
}
