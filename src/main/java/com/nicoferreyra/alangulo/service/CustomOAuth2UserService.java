package com.nicoferreyra.alangulo.service;

import com.nicoferreyra.alangulo.enums.eRol;
import com.nicoferreyra.alangulo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import com.nicoferreyra.alangulo.model.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends OidcUserService {

    private final UsersRepository usersRepository;

    @Value("${app.security.admins}")
    private List<String> emailsAdmin;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);

        String email = Objects.toString(oidcUser.getAttributes().get("email"), "");
        String name = Objects.toString(oidcUser.getAttributes().get("name"), "Usuario sin nombre");
        String picture = Objects.toString(oidcUser.getAttributes().get("picture"), null);

        User user  = saveOrUpdate(email, name, picture);
        var autoridad = new SimpleGrantedAuthority("ROLE_" + user.getRol());

        return new DefaultOidcUser(List.of(autoridad), oidcUser.getIdToken(), oidcUser.getUserInfo());
    }

    private User saveOrUpdate(String email, String name, String picture) {

        Optional <User> userOptional = usersRepository.findByEmail(email);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            user.setNombre(name);
            user.setPhoto(picture);

            if (emailsAdmin.contains(user.getEmail())) {
                user.setRol(eRol.OWNER);
            }

            return usersRepository.save(user);

        } else {
            if (emailsAdmin.contains(email)) {
                return usersRepository.save(User.builder()
                        .photo(picture)
                        .email(email)
                        .rol(eRol.OWNER)
                        .nombre(name).build());
            } else {
                return usersRepository.save(User.builder()
                        .email(email)
                        .photo(picture)
                        .rol(eRol.USER)
                        .nombre(name)
                        .build());
            }
        }
    }
}
