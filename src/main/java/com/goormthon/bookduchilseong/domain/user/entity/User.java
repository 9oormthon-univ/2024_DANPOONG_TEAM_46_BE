package com.goormthon.bookduchilseong.domain.user.entity;

import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsign;
import com.goormthon.bookduchilseong.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name; // 유저 이름

	@Column(name = "draw", nullable = false)
	private int draw; // 뽑기

	@Column(name = "kakaothumbnail", nullable = true)
	private String kakaothumbnail;

	@Column(name = "profile", nullable = true)
	private String profile; // 프로필 사진 uri(nullable)

	@Column(name = "zodiacsigns", nullable = true)
	private String zodiacsigns; // 프로필 별자리 이름(nullable)


	public void updateProfile(Zodiacsign zodiacsign) {
		this.profile = zodiacsign.getZodiacsignImg();
		this.zodiacsigns = zodiacsign.getZodiacsigns().name();
	}

	public void addDraw() {
		this.draw = draw + 1;
	}

	public void decreaseDrawCount() {
		this.draw = this.draw - 1;
	}
}
