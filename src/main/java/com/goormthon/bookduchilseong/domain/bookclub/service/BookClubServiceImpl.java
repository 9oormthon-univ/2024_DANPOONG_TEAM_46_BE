package com.goormthon.bookduchilseong.domain.bookclub.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubOnlyRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubTogetherRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubDetailDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubProgressDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;
import com.goormthon.bookduchilseong.domain.bookclub.entity.Book;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.entity.User;
import com.goormthon.bookduchilseong.domain.bookclub.repository.BookClubRepository;
import com.goormthon.bookduchilseong.domain.bookclub.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.bookclub.repository.UserRepository;
import com.goormthon.bookduchilseong.domain.userbookclub.entity.UserBookClub;
import com.goormthon.bookduchilseong.domain.userbookclub.repository.UserBookClubRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookClubServiceImpl implements BookClubService {

	private final BookClubRepository bookClubRepository;
	private final UserRepository userRepository;
	private final UserBookClubRepository userBookClubRepository;
	private final BookRepository bookRepository;

	@Override
	@Transactional
	public void createBookClubOnly(BookClubOnlyRequestDTO bookClubOnlyRequestDTO) {

		BookClub bookClub = BookClub.builder()
			.title(bookClubOnlyRequestDTO.getTitle())
			.introduction(bookClubOnlyRequestDTO.getIntroduction())
			.startDate(LocalDate.parse(bookClubOnlyRequestDTO.getStartDate()))
			.endDate(LocalDate.parse(bookClubOnlyRequestDTO.getEndDate()))
			.maxParticipant(bookClubOnlyRequestDTO.getMaxParticipant())
			.type(bookClubOnlyRequestDTO.getType())
			.profile(bookClubOnlyRequestDTO.getProfile())
			.build();

		bookClubRepository.save(bookClub);

		UserBookClub userBookClub = UserBookClub.builder().user(findUser(1L)).bookClub(bookClub).isOwner(true).build();

		userBookClubRepository.save(userBookClub);
	}

	@Override
	@Transactional
	public void createBookClubTogether(BookClubTogetherRequestDTO bookClubTogetherRequestDTO) {

		BookClub bookClub = BookClub.builder()
			.title(bookClubTogetherRequestDTO.getTitle())
			.introduction(bookClubTogetherRequestDTO.getIntroduction())
			.startDate(LocalDate.parse(bookClubTogetherRequestDTO.getStartDate()))
			.endDate(LocalDate.parse(bookClubTogetherRequestDTO.getEndDate()))
			.maxParticipant(bookClubTogetherRequestDTO.getMaxParticipant())
			.type(bookClubTogetherRequestDTO.getType())
			.profile(bookClubTogetherRequestDTO.getProfile())
			.build();

		bookClubRepository.save(bookClub);

		UserBookClub userBookClub = UserBookClub.builder().user(findUser(1L)).bookClub(bookClub).isOwner(true).build();

		Book book = Book.builder()
			.user(findUser(1L))
			.bookClub(bookClub)
			.title(bookClubTogetherRequestDTO.getBookTitle())
			.author(bookClubTogetherRequestDTO.getAuthor())
			.totalPage(bookClubTogetherRequestDTO.getTotalPage())
			.profile(bookClubTogetherRequestDTO.getProfile())
			.build();

		bookRepository.save(book);

		userBookClubRepository.save(userBookClub);
	}

	@Override
	@Transactional
	public void joinBookClub(Long bookclubId) {

		User user = findUser(6L);

		BookClub bookClub = bookClubRepository.findById(bookclubId)
			.orElseThrow(() -> new IllegalArgumentException("Not Found BookClub By bookclubId"));

		if (userBookClubRepository.existsByUserAndBookClub(user, bookClub)) {
			throw new IllegalArgumentException("Already Joined BookClub");
		}

		UserBookClub newUserBookClub = UserBookClub.builder().user(user).bookClub(bookClub).isOwner(false).build();

		int maxParticipant = bookClub.getMaxParticipant();
		bookClub.increaseParticipateCount(maxParticipant);

		Optional<Book> book = bookRepository.findByBookClub(bookClub);
		if (book.isPresent()) {
			Book newBook = Book.builder()
				.user(user)
				.bookClub(bookClub)
				.title(book.get().getTitle())
				.author(book.get().getAuthor())
				.totalPage(book.get().getTotalPage())
				.profile(book.get().getProfile())
				.build();

			bookRepository.save(newBook);
		}

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
			.maxParticipant(bookClub.getMaxParticipant())
			.participateCount(bookClub.getParticipateCount())
			.startDate(String.valueOf(bookClub.getStartDate()))
			.endDate(String.valueOf(bookClub.getEndDate()))
			.profile(bookClub.getProfile())
			.build();
	}

	@Override
	public List<BookClubProgressDTO> getBookClubProgresses(Long bookclubId) {

		BookClub bookClub = bookClubRepository.findById(bookclubId)
			.orElseThrow(() -> new IllegalArgumentException("Not Found BookClub By bookclubId"));

		List<UserBookClub> userBookClubs = userBookClubRepository.findByBookClub(bookClub);
		if (userBookClubs.isEmpty()) {
			throw new IllegalArgumentException("Not Found Users By bookClub");
		}

		List<BookClubProgressDTO> progressDTOs = userBookClubs.stream().map(userBookClub -> {
			User user = userBookClub.getUser();
			log.info("user: {}", user.getName());

			Book book = bookRepository.findByBookClubAndUser(bookClub, user)
				.orElseThrow(() -> new IllegalArgumentException("Not Found Book By bookClub and user"));

			return BookClubProgressDTO.builder()
				.name(user.getName())
				.zodiacsigns(user.getZodiacsigns())
				.profile(user.getProfile())
				.totalPage(book.getTotalPage())
				.readPage(book.getReadPage())
				.goalDayPage(book.getGoalDayPage())
				.build();
		}).collect(Collectors.toList());

		return progressDTOs;
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("Not Found User By userId"));
	}

}
