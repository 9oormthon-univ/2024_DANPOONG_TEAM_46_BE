package com.goormthon.bookduchilseong.domain.zodiacsign.dto.response;

import java.time.LocalDate;

import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsigns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZodiacsignDetailDTO {
	private Zodiacsigns zodiacSignName;
	private String zodiacSignImg;
	private LocalDate createdAt;
}
