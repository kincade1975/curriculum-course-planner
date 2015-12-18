package hr.teosoft.ccp.rest.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class CourseResource {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("code")
	private String code;

	@JsonProperty("type")
	private String type;

	@JsonProperty("teacherCode")
	private String teacherCode;

	@JsonProperty("lectureSize")
	private Integer lectureSize;

	@JsonProperty("minWorkingDaySize")
	private Integer minWorkingDaySize;

	@JsonProperty("curriculumCodes")
	private List<String> curriculumCodes;

	@JsonProperty("studentSize")
	private Integer studentSize;

	@JsonProperty("preferredDays")
	private List<Integer> preferredDays;

	@JsonProperty("preferredTimeslots")
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "CourseResource [id=" + id + ", code=" + code + ", type=" + type + ", teacherCode=" + teacherCode + ", lectureSize=" + lectureSize + ", minWorkingDaySize=" + minWorkingDaySize + ", curriculumCodes=" + curriculumCodes + ", studentSize=" + studentSize + ", preferredDays=" + preferredDays + ", preferredTimeslots=" + preferredTimeslots + "]";
	}

}
