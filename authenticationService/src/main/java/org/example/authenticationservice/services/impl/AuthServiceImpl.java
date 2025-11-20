package org.example.authenticationservice.services.impl;

import lombok.AllArgsConstructor;
import org.example.authenticationservice.SecurityConfig.CustomUserDetails;
import org.example.authenticationservice.SecurityConfig.JwtService;
import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.LoginRequestDTO;
import org.example.authenticationservice.dtos.UserDTO;
import org.example.authenticationservice.entities.Role;
import org.example.authenticationservice.entities.User;
import org.example.authenticationservice.exception.AccountNotActivatedException;
import org.example.authenticationservice.services.AuthService;
import org.example.authenticationservice.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void SignUp(CreateUserRequestDTO createUserRequest) {
        userService.createUser(createUserRequest, Role.PASSENGER);
    }

    @Override
    public String signIn(LoginRequestDTO loginRequest) {
        try{
            UserDTO user = userService.getUserByEmail(loginRequest.email());
            System.out.println("account status: "+user.accountActivated());
            if (user == null) {
                throw new BadCredentialsException("Invalid email or password");
            }
            if (!user.accountActivated()) {
                throw new AccountNotActivatedException("Account is not activated. Please check your email to activate your account.");
            }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password()
        ));
        System.out.println("Login Success");
        SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("set context security holder");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println("fetched user details");
        String jwtToken = jwtService.generateToken(userDetails);
        return jwtToken;
    }
        catch (AccountNotActivatedException e) {
            throw e;
        }catch (BadCredentialsException e) {
        throw new BadCredentialsException("Invalid email orpassword");
        } catch (Exception e) {
        throw new RuntimeException("Authentication failed", e);
        }
    }
}
