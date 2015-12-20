package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Lecture", description="Lecture is a planning entity.")
public class LectureResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=true, description="Unique identifier of the lecture set and used internally by the planner.")
	private Long id;

	@JsonProperty("courseCode")
	@ApiObjectField(name="courseCode", required=true, description="Course code.")
	private String courseCode;

	@JsonProperty("lectureIndexInCourse")
	@ApiObjectField(name="lectureIndexInCourse", required=true, description="Index of the lecture within the week (e.g if course has four lectures in one week that this possible values will go from 0 to 3). ")
	private Integer lectureIndexInCourse;

	@JsonProperty("locked")
	@ApiObjectField(name="locked", required=true, description="Indicates if lecture should be locked during planning.")
	private Boolean locked = Boolean.FALSE;

	@JsonProperty("periodCode")
	@ApiObjectField(name="periodCode", required=true, description="Period code. Period code is combination of day index and timeslot index concatenated by '-' character (e.g. 0-1 represents Monday 08:00).")
	private String periodCode;

	@JsonProperty("roomCode")
	@ApiObjectField(name="roomCode", required=true, description="Room code.")
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
