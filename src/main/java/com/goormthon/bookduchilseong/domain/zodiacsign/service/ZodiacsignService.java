package com.goormthon.bookduchilseong.domain.zodiacsign.service;

import java.util.List;

import com.goormthon.bookduchilseong.domain.zodiacsign.dto.response.ZodiacsignDetailDTO;
import com.goormthon.bookduchilseong.domain.zodiacsign.dto.response.ZodiacsignResponseDTO;

public interface ZodiacsignService {
	List<ZodiacsignResponseDTO> getMyZodiacsigns(Long userId);

	ZodiacsignDetailDTO getDetailZodiacsign(Long zodiacsignId);
}
