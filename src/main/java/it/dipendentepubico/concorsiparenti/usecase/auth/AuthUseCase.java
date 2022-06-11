package it.dipendentepubico.concorsiparenti.usecase.auth;

import it.dipendentepubico.concorsiparenti.domain.reqsponse.AuthenticateUserRequest;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.AuthenticateUserResponse;
import it.dipendentepubico.concorsiparenti.jpa.auth.entity.UserEntity;

public interface AuthUseCase {
    AuthenticateUserResponse authenticate(AuthenticateUserRequest request);

    void registerUser(UserEntity userEntity);
}
