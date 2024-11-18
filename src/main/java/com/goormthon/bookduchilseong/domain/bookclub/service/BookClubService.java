package com.goormthon.bookduchilseong.domain.bookclub.service;

import java.util.List;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubResqeustDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubDetailDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;

public interface BookClubService {

	void createBookClub(BookClubResqeustDTO bookClubResqeustDTO);

	void joinBookClub(Long bookClubId);

	List<BookClubResponseDTO> getBookClubs();

	BookClubDetailDTO getBookClub(Long bookclubId);
}
