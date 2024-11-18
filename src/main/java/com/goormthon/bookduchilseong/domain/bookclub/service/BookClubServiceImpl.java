package com.goormthon.bookduchilseong.domain.bookclub.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubResqeustDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubDetailDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.entity.User;
import com.goormthon.bookduchilseong.domain.bookclub.repository.BookClubRepository;
import com.goormthon.bookduchilseong.domain.bookclub.repository.UserRepository;
import com.goormthon.bookduchilseong.domain.userbookclub.entity.UserBookClub;
import com.goormthon.bookduchilseong.domain.userbookclub.repository.UserBookClubRepository;

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
			.startDate(LocalDate.parse(bookClubResqeustDTO.getStartDate()))
			.endDate(LocalDate.parse(bookClubResqeustDTO.getEndDate()))
			.maxParticipant(bookClubResqeustDTO.getMaxParticipant())
			.type(bookClubResqeustDTO.getType())
			.profile(bookClubResqeustDTO.getProfile())
			.build();

		bookClubRepository.save(bookClub);

		// 유저_북클럽 생성
		UserBookClub userBookClub = UserBookClub.builder()
			.user(findUser(1L))
			.bookClub(bookClub)
			.isOwner(true)
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
			.orElseThrow(() -> new IllegalArgumentException("Not Found BookClub By bookclubId"));

		if (userBookClubRepository.existsByUserAndBookClub(user, bookClub)) {
			throw new IllegalArgumentException("Already Joined BookClub");
		}

		// 참여하기
		UserBookClub newUserBookClub = UserBookClub.builder()
			.user(user)
			.bookClub(bookClub)
			.isOwner(false)
			.build();

		int maxParticipant = bookClub.getMaxParticipant();
		bookClub.increaseParticipateCount(maxParticipant);

		bookClubRepository.save(bookClub);
		userBookClubRepository.save(newUserBookClub);

	}

	@Override
	@Transactional(readOnly = true)
	public List<BookClubResponseDTO> getBookClubs() {

		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
		return bookClubRepository.findBookClubs(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public BookClubDetailDTO getBookClub(Long bookclubId) {

		BookClub bookClub = bookClubRepository.findById(bookclubId)
			.orElseThrow(() -> new IllegalArgumentException("Not Found BookClub By bookclubId"));

		Optional<UserBookClub> ownerUser = userBookClubRepository.findByBookClubAndIsOwner(bookClub, true);

		if (ownerUser.isEmpty()) {
			throw new IllegalArgumentException("Not Found Owner User");
		}

		return BookClubDetailDTO.builder()
			.id(bookClub.getId())
			.ownerName((ownerUser.get()).getUser().getName())
			.bookTitle(bookClub.getBookTitle())
			.maxParticipant(bookClub.getMaxParticipant())
			.participateCount(bookClub.getParticipateCount())
			.startDate(String.valueOf(bookClub.getStartDate()))
			.endDate(String.valueOf(bookClub.getEndDate()))
			.profile(bookClub.getProfile())
			.build();
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("Not Found User By userId"));
	}

}
