package com.goormthon.bookduchilseong.domain.zodiacsign.dto.response;

import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsigns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZodiacsignResponseDTO {
	private Zodiacsigns zodiacSign;
	private String zodiacSignImg;
	private Boolean status;
}
