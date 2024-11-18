package com.goormthon.bookduchilseong.domain.bookclub.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubResqeustDTO;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.entity.User;
import com.goormthon.bookduchilseong.domain.bookclub.entity.UserBookClub;
import com.goormthon.bookduchilseong.domain.bookclub.repository.BookClubRepository;
import com.goormthon.bookduchilseong.domain.bookclub.repository.UserBookClubRepository;
import com.goormthon.bookduchilseong.domain.bookclub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubServiceImpl implements BookClubService {

	private final BookClubRepository bookClubRepository;
	private final UserRepository userRepository;
	private final UserBookClubRepository userBookClubRepository;

	@Override
	@Transactional
	public void createBookClub(BookClubResqeustDTO bookClubResqeustDTO) {

		// 북클럽 생성
		BookClub bookClub = BookClub.builder()
			.title(bookClubResqeustDTO.getTitle())
			.bookTitle(bookClubResqeustDTO.getBookTitle())
			.introduction(bookClubResqeustDTO.getIntroduction())
			.participateCount(1)
			.startDate(LocalDate.parse(bookClubResqeustDTO.getStartDate()))
			.endDate(LocalDate.parse(bookClubResqeustDTO.getEndDate()))
			.maxParticipant(bookClubResqeustDTO.getMaxParticipant())
			.type(bookClubResqeustDTO.getType())
			.build();

		bookClubRepository.save(bookClub);

		// 유저_북클럽 생성
		UserBookClub userBookClub = UserBookClub.builder()
			.user(findUser(1L))
			.bookClub(bookClub)
			.build();

		userBookClubRepository.save(userBookClub);
	}

	@Override
	@Transactional
	public void joinBookClub(Long bookclubId) {

		//JWT로 변경
		User user = findUser(6L);

		// 북클럽 찾기
		BookClub bookClub = bookClubRepository.findById(bookclubId)
			.orElseThrow(() -> new IllegalArgumentException("Not Found BookClub"));

		if (userBookClubRepository.existsByUserAndBookClub(user, bookClub)) {
			throw new IllegalArgumentException("Already Joined BookClub");
		}

		// 참여하기
		UserBookClub newUserBookClub = UserBookClub.builder()
			.user(user)
			.bookClub(bookClub)
			.build();

		int maxParticipant = bookClub.getMaxParticipant();
		bookClub.increaseParticipateCount(maxParticipant);

		bookClubRepository.save(bookClub);
		userBookClubRepository.save(newUserBookClub);

	}

	private User findUser(Long userId) {
		return  userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("Not Found User"));
	}

}
