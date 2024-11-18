package com.goormthon.bookduchilseong.domain.userbookclub.entity;

import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor()
@NoArgsConstructor
@Table(name = "user_book_club")
public class UserBookClub {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_club_id")
	private BookClub bookClub;

	@Column(name = "is_owner")
	private boolean isOwner;

	@Builder
	public UserBookClub(User user, BookClub bookClub, boolean isOwner) {
		this.user = user;
		this.bookClub = bookClub;
		this.isOwner = isOwner;
	}

}
