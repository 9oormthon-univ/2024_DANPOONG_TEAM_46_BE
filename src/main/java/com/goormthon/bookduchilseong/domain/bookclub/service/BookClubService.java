package com.goormthon.bookduchilseong.domain.bookclub.service;

import java.util.List;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubOnlyRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubTogetherRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubDetailDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubProgressDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;

public interface BookClubService {

	void createBookClubOnly(BookClubOnlyRequestDTO bookClubOnlyRequestDTO);

	void createBookClubTogether(BookClubTogetherRequestDTO bookClubTogetherRequestDTO);

	void joinBookClub(Long bookClubId);

	List<BookClubResponseDTO> getBookClubs();

	BookClubDetailDTO getBookClub(Long bookclubId);
}
