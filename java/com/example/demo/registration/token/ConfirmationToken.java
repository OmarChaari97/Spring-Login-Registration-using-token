package com.example.demo.registration.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.example.demo.appUser.AppUser;
@Entity
public class ConfirmationToken {
	
	 @SequenceGenerator(
	            name = "confirmation_token_sequence",
	            sequenceName = "confirmation_token_sequence",
	            allocationSize = 1
	    )
	    @Id
	    @GeneratedValue(
	            strategy = GenerationType.SEQUENCE,
	            generator = "confirmation_token_sequence"
	    )
	private Long id;
	@Column(nullable = false)
	private String token;
	private LocalDateTime createdAt ;
	private LocalDateTime expiredAt ;
    private LocalDateTime confirmedAt;

	@ManyToOne
	@JoinColumn( nullable = false, name = "app_user_id")
	private AppUser appUser ;
	public String getToken() {
		return token;
	}
	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}
	
	public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, AppUser appUser) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.appUser = appUser;
	}
	
	public ConfirmationToken() {
		
	}
	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}
	public void setConfirmedAt(LocalDateTime confirmedAt) {
		this.confirmedAt = confirmedAt;
	}
	
	

}
