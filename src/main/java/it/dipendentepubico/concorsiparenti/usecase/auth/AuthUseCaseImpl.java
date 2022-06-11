package it.dipendentepubico.concorsiparenti.usecase.auth;

import it.dipendentepubico.concorsiparenti.domain.auth.UserDetailsImpl;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.AuthenticateUserRequest;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.AuthenticateUserResponse;
import it.dipendentepubico.concorsiparenti.jpa.auth.entity.UserEntity;
import it.dipendentepubico.concorsiparenti.jpa.auth.repository.UserRepository;
import it.dipendentepubico.concorsiparenti.jwt.JwtUtils;
import it.dipendentepubico.concorsiparenti.rest.auth.model.Role;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthUseCaseImpl implements AuthUseCase{

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    protected ModelMapper mapper;

    @Override
    public AuthenticateUserResponse authenticate(AuthenticateUserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Role> roles = userDetails.getAuthorities().stream()
                .map(item -> {
                    String authority = item.getAuthority();
                    return Role.valueOf(authority.substring("ROLE_".length()));
                })
                .collect(Collectors.toList());

        AuthenticateUserResponse userResponse = new AuthenticateUserResponse();
        userResponse.setJwt(jwt);
        userResponse.setUserDetails(userDetails);
        userResponse.setRoles(roles);

        return userResponse;
    }

    @Override
    public void registerUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
