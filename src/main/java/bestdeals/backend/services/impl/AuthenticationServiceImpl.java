package bestdeals.backend.services.impl;

import bestdeals.backend.dto.JwtAuthenticationResponse;
import bestdeals.backend.dto.RefreshTokenRequest;
import bestdeals.backend.dto.SignInRequest;
import bestdeals.backend.entities.Role;
import bestdeals.backend.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bestdeals.backend.dto.SignUpRequest;
import bestdeals.backend.entities.User;
import bestdeals.backend.repositories.UserRepository;
import bestdeals.backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public User signUp(SignUpRequest signUpRequest) {
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setRoles(signUpRequest.getRoles());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		return userRepository.save(user);
	}

	public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

		var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or Password "));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		return jwtAuthenticationResponse;
	}

	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
	{
		String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user))
		{
			var jwt = jwtService.generateToken(user);

			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			return jwtAuthenticationResponse;
		}
		return null;
	}

}
