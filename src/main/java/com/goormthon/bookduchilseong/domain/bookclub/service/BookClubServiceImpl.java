package com.goormthon.bookduchilseong.domain.bookclub.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubResqeustDTO;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.repository.BookClubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubServiceImpl implements BookClubService {

	private final BookClubRepository bookClubRepository;

	@Override
	@Transactional
	public void createBookClub(BookClubResqeustDTO bookClubResqeustDTO) {

		BookClub bookClub = BookClub.builder()
				.title(bookClubResqeustDTO.getTitle())
				.bookTitle(bookClubResqeustDTO.getBookTitle())
				.introduction(bookClubResqeustDTO.getIntroduction())
				.startDate(LocalDate.parse(bookClubResqeustDTO.getStartDate()))
				.endDate(LocalDate.parse(bookClubResqeustDTO.getEndDate()))
				.maxParticipant(bookClubResqeustDTO.getMaxParticipant())
				.type(bookClubResqeustDTO.getType())
				.build();

		bookClubRepository.save(bookClub);
	}
}
