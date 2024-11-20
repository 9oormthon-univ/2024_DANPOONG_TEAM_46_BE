package com.goormthon.bookduchilseong.domain.user.entity;

import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsign;
import com.goormthon.bookduchilseong.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "draw", nullable = false)
	private String draw;

	@Column(name = "profile", nullable = true)
	private String profile;

	@Column(name = "zodiacsigns", nullable = true)
	private String zodiacsigns;

	public void updateProfile(Zodiacsign zodiacsign) {
		this.profile = zodiacsign.getZodiacsignImg();
		this.zodiacsigns = zodiacsign.getZodiacsigns().name();
	}
}
