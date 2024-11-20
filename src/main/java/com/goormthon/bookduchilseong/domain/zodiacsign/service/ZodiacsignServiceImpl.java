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
import com.goormthon.bookduchilseong.global.apiPayload.code.status.ErrorStatus;
import com.goormthon.bookduchilseong.global.apiPayload.exception.GeneralException;

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
			.map(z -> new ZodiacsignResponseDTO(z.getId(), z.getZodiacsigns(), z.getZodiacsignImg(), z.getStatus()))
			.collect(Collectors.toList());
	}

	@Override
	public ZodiacsignDetailDTO getDetailZodiacsign(Long zodiacsignId) {
		Zodiacsign zodiacsign = findZodiacsignById(zodiacsignId);

		return new ZodiacsignDetailDTO(zodiacsign.getZodiacsigns(), zodiacsign.getZodiacsignImg(),
			zodiacsign.getUpdatedAt().toLocalDate());

	}

	@Override
	public void updateProfile(Long zodiacsignId, Long userId) {
		User user = findUserById(userId);

		Zodiacsign zodiacsign = findZodiacsignById(zodiacsignId);

		user.updateProfile(zodiacsign);
		userRepository.save(user);
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));
	}

	private Zodiacsign findZodiacsignById(Long zodiacsignId) {
		return zodiacsignRepository.findById(zodiacsignId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._ZODIACSIGN_NOT_FOUND));
	}
}
