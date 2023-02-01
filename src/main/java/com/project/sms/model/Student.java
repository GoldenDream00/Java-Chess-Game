package com.project.sms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Document(collection = "Student")

public class Student {

	@Id
	private String id; // id
	@DBRef
	private List<Guardian> guardian;
	private String school;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	private Boolean allowPictures;

	private int chesshoodRating;
	private int eCFrating;
	private int puzzleRating;

	@CreatedDate
	private LocalDate registrationDate;
	//Instead we're using nicknames
	private String nickname;

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	public int getPuzzleRating() {
		return puzzleRating;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public int geteCFrating() {
		return eCFrating;
	}

	public int getChesshoodRating() {
		return chesshoodRating;
	}

	public Boolean getAllowPictures() {
		return allowPictures;
	}

	public LocalDate getDob() {
		return dob;
	}

	public String getSchool() {
		return school;
	}

	public List<Guardian> getGuardian() {
		return guardian;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setPuzzleRating(int puzzleRating) {
		this.puzzleRating = puzzleRating;
	}

	public void seteCFrating(int eCFrating) {
		this.eCFrating = eCFrating;
	}

	public void setChesshoodRating(int chesshoodRating) {
		this.chesshoodRating = chesshoodRating;
	}

	public void setAllowPictures(Boolean allowPictures) {
		this.allowPictures = allowPictures;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setGuardian(List<Guardian> guardian) {
		this.guardian = guardian;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
