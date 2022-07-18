package com.example.demo.appUser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class AppUserService implements UserDetailsService {
	
	private final static String USER_NOT_FOUND = "User with email %s not found !";
    private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private final ConfirmationTokenService confirmationTokenService ;
	
	@Autowired
	public AppUserService(AppUserRepository appUserRepository, ConfirmationTokenService confirmationTokenService ) {
		this.appUserRepository = appUserRepository;
		this.confirmationTokenService = confirmationTokenService;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
	}
	public String signup(AppUser appUser)
	{
		 boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
	        if (userExists) {
	            // TODO check of attributes are the same and
	            // TODO if email not confirmed send confirmation email.

	            throw new IllegalStateException("email already taken");
	        }
		
		String encodedpassword = bCryptPasswordEncoder.encode(appUser.getPassword());
		appUser.setPassword(encodedpassword);
		appUserRepository.save(appUser);
		
		//return token
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		return token;
		//email sender
	}
	  public int enableAppUser(String email) {
	        return appUserRepository.enableAppUser(email);
	    }
}
