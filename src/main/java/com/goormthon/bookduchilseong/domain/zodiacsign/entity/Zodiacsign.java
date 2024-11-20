package com.goormthon.bookduchilseong.domain.zodiacsign.entity;

import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zodiac_sign")
public class Zodiacsign extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "zodiac_sign_name", nullable = false)
	@Enumerated(EnumType.STRING) // 열거형을 문자열로 저장
	private Zodiacsigns zodiacsigns;

	@Column(name = "status", nullable = false)
	private Boolean status;

	@Column(name = "zodiac_sign_Img", nullable = false)
	private String zodiacsignImg;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Builder
	public Zodiacsign(Zodiacsigns zodiacsigns, Boolean status, String zodiacsignImg, User user) {
		this.zodiacsigns = zodiacsigns;
		this.status = status;
		this.zodiacsignImg = zodiacsignImg;
		this.user = user;
	}
}
