package hr.teosoft.ccp.rest.resource;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Course", description="Each course consists of a fixed number of lectures to be scheduled in distinct periods.<br>It is attended by given number of students, and is taught by a teacher.<br>For each course there is a minimum number of days that the lectures of the course should be spread in, moreover there are some periods in which the course cannot be scheduled (see UnavailablePeriodPenalty).")
public class CourseResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the course set and used internally by the planner.")
	private Long id;

	@JsonProperty("code")
	@ApiObjectField(name="code", required=true, description="Course code.")
	private String code;

	@JsonProperty("requiredRoomType")
	@ApiObjectField(name="requiredRoomType", required=true, description="Required room type. This value must match room type (e.g. L - lecture room, C - computer room, etc).")
	private String requiredRoomType;

	@JsonProperty("teacherCode")
	@ApiObjectField(name="teacherCode", required=true, description="Teacher code.")
	private String teacherCode;

	@JsonProperty("lectureSize")
	@ApiObjectField(name="lectureSize", required=true, description="Lecture size in one week.")
	private Integer lectureSize;

	@JsonProperty("minWorkingDaySize")
	@ApiObjectField(name="minWorkingDaySize", required=true, description="Minimum number of days that the lectures of the course should be spread in.")
	private Integer minWorkingDaySize;

	@JsonProperty("curriculumCodes")
	@ApiObjectField(name="curriculumCodes", required=true, description="List of curriculum codes this course is part of.")
	private List<String> curriculumCodes;

	@JsonProperty("studentSize")
	@ApiObjectField(name="studentSize", required=true, description="Number of students attending the course.")
	private Integer studentSize;

	@JsonProperty("preferredDays")
	@ApiObjectField(name="preferredDays", required=false, description="List of preferred days.<br>Possible values: 0 = Monday, 1 = Tuesday, 2 = Wednesday, 3 = Thursday, 4 = Friday, 5 = Saturday, 6 = Sunday")
	private List<Integer> preferredDays;

	@JsonProperty("preferredTimeslots")
	@ApiObjectField(name="preferredTimeslots", required=false, description="List of preferred timeslots.<br>Possible values: 0 = 07:15, 1 = 08:00, 2 = 08:45, 3 = 09:30, 4 = 10:30, 5 = 11:15, 6 = 12:15, 7 = 13:00, 8 = 14:00, 9 = 14:45, 10 = 15:45, 11 = 16:30, 12 = 17:30, 13 = 18:15, 14 = 19:15")
	private List<Integer> preferredTimeslots;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRequiredRoomType() {
		return requiredRoomType;
	}

	public void setRequiredRoomType(String requiredRoomType) {
		this.requiredRoomType = requiredRoomType;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public Integer getLectureSize() {
		return lectureSize;
	}

	public void setLectureSize(Integer lectureSize) {
		this.lectureSize = lectureSize;
	}

	public Integer getMinWorkingDaySize() {
		return minWorkingDaySize;
	}

	public void setMinWorkingDaySize(Integer minWorkingDaySize) {
		this.minWorkingDaySize = minWorkingDaySize;
	}

	public List<String> getCurriculumCodes() {
		return curriculumCodes;
	}

	public void setCurriculumCodes(List<String> curriculumCodes) {
		this.curriculumCodes = curriculumCodes;
	}

	public Integer getStudentSize() {
		return studentSize;
	}

	public void setStudentSize(Integer studentSize) {
		this.studentSize = studentSize;
	}

	public List<Integer> getPreferredDays() {
		return preferredDays;
	}

	public void setPreferredDays(List<Integer> preferredDays) {
		this.preferredDays = preferredDays;
	}

	public List<Integer> getPreferredTimeslots() {
		return preferredTimeslots;
	}

	public void setPreferredTimeslots(List<Integer> preferredTimeslots) {
		this.preferredTimeslots = preferredTimeslots;
	}

	@Override
	public String toString() {
		return "CourseResource [id=" + id + ", code=" + code + ", requiredRoomType=" + requiredRoomType + ", teacherCode=" + teacherCode + ", lectureSize=" + lectureSize + ", minWorkingDaySize=" + minWorkingDaySize + ", curriculumCodes=" + curriculumCodes + ", studentSize=" + studentSize + ", preferredDays=" + preferredDays + ", preferredTimeslots=" + preferredTimeslots + "]";
	}

}
