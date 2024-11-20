package com.goormthon.bookduchilseong.domain.zodiacsign.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.repository.UserRepository;
import com.goormthon.bookduchilseong.domain.zodiacsign.dto.response.ZodiacsignDetailDTO;
import com.goormthon.bookduchilseong.domain.zodiacsign.dto.response.ZodiacsignResponseDTO;
import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsign;
import com.goormthon.bookduchilseong.domain.zodiacsign.repository.ZodiacsignRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZodiacsignServiceImpl implements ZodiacsignService {

	private final UserRepository userRepository;
	private final ZodiacsignRepository zodiacsignRepository;

	@Override
	public List<ZodiacsignResponseDTO> getMyZodiacsigns(Long userId) {

		List<Zodiacsign> zodiacsign = zodiacsignRepository.findByUser(findUserById(userId));

		return zodiacsign.stream()
			.map(z -> new ZodiacsignResponseDTO(z.getId(), z.getZodiacsignes(), z.getZodiacsignImg(), z.getStatus()))
			.collect(Collectors.toList());
	}

	@Override
	public ZodiacsignDetailDTO getDetailZodiacsign(Long zodiacsignId) {

		Zodiacsign zodiacsign = zodiacsignRepository.findById(zodiacsignId)
			.orElseThrow(() -> new IllegalArgumentException("Zodiacsign not found"));

		return new ZodiacsignDetailDTO(zodiacsign.getZodiacsignes(), zodiacsign.getZodiacsignImg(), zodiacsign.getUpdatedAt().toLocalDate());

	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
	}
}
