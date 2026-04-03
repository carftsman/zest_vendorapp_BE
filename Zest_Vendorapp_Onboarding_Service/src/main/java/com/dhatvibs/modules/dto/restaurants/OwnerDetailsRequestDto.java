package com.dhatvibs.modules.dto.restaurants;

import lombok.Data;

@Data
public class OwnerDetailsRequestDto {
	
	private String fullName;
	private String email;
	private String password;
	private String confirmPassword;
	

}
