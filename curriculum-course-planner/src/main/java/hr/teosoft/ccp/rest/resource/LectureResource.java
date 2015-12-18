package hr.teosoft.ccp.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class LectureResource {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("courseCode")
	private String courseCode;

	@JsonProperty("lectureIndexInCourse")
	private Integer lectureIndexInCourse;

	@JsonProperty("locked")
	private Boolean locked = Boolean.FALSE;

	@JsonProperty("periodCode")
	private String periodCode;

	@JsonProperty("roomCode")
	private String roomCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public int getLectureIndexInCourse() {
		return lectureIndexInCourse;
	}

	public void setLectureIndexInCourse(int lectureIndexInCourse) {
		this.lectureIndexInCourse = lectureIndexInCourse;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	@Override
	public String toString() {
		return "LectureResource [id=" + id + ", courseCode=" + courseCode + ", lectureIndexInCourse="
				+ lectureIndexInCourse + ", locked=" + locked + ", periodCode=" + periodCode + ", roomCode=" + roomCode
				+ "]";
	}

}
