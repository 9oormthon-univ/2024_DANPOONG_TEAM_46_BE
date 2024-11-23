package com.goormthon.bookduchilseong.domain.bookclub.service;

import java.util.List;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubOnlyRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubTogetherRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubDetailDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubGalleryDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubJoinDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubJoinedDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubProgressDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;

public interface BookClubService {

	void createBookClubOnly(BookClubOnlyRequestDTO bookClubOnlyRequestDTO, String token);

	void createBookClubTogether(BookClubTogetherRequestDTO bookClubTogetherRequestDTO, String token);

	void joinBookClub(Long bookClubId, String token);

	List<BookClubResponseDTO> getBookClubs();

	BookClubDetailDTO getBookClub(Long bookclubId);

	List<BookClubProgressDTO> getBookClubProgresses(Long bookclubId);

	List<BookClubGalleryDTO> getBookClubGallery(Long bookclubId);

	List<BookClubJoinedDTO> getJoinedBookClubs(String token);

	List<BookClubJoinDTO> getjoinBookClubs(String token);
}
