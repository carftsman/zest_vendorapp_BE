package com.dhatvibs.modules.dto.restaurants;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonApiResponse<T> {
	
	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timestamp;

}
