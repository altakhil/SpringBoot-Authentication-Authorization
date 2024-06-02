package bestdeals.backend.services;

import bestdeals.backend.dto.JwtAuthenticationResponse;
import bestdeals.backend.dto.RefreshTokenRequest;
import bestdeals.backend.dto.SignInRequest;
import bestdeals.backend.dto.SignUpRequest;
import bestdeals.backend.entities.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
