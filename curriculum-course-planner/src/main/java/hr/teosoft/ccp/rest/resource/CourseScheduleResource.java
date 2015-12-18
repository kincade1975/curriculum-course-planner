package hr.teosoft.ccp.rest.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class CourseScheduleResource {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("teachers")
	private List<TeacherResource> teachers;

	@JsonProperty("curriculums")
	private List<CurriculumResource> curriculums;

	@JsonProperty("courses")
	private List<CourseResource> courses;

	@JsonProperty("days")
	private List<DayResource> days;

	@JsonProperty("timeslots")
	private List<TimeslotResource> timeslots;

	@JsonProperty("periods")
	private List<PeriodResource> periods;

	@JsonProperty("rooms")
	private List<RoomResource> rooms;

	@JsonProperty("unavailablePeriodPenalties")
	private List<UnavailablePeriodPenaltyResource> unavailablePeriodPenalties;

	@JsonProperty("lectures")
	private List<LectureResource> lectures;

	@JsonProperty("score")
	private ScoreResource score;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TeacherResource> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherResource> teachers) {
		this.teachers = teachers;
	}

	public List<CurriculumResource> getCurriculums() {
		return curriculums;
	}

	public void setCurriculums(List<CurriculumResource> curriculums) {
		this.curriculums = curriculums;
	}

	public List<CourseResource> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseResource> courses) {
		this.courses = courses;
	}

	public List<DayResource> getDays() {
		return days;
	}

	public void setDays(List<DayResource> days) {
		this.days = days;
	}

	public List<TimeslotResource> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(List<TimeslotResource> timeslots) {
		this.timeslots = timeslots;
	}

	public List<PeriodResource> getPeriods() {
		return periods;
	}

	public void setPeriods(List<PeriodResource> periods) {
		this.periods = periods;
	}

	public List<RoomResource> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomResource> rooms) {
		this.rooms = rooms;
	}

	public List<UnavailablePeriodPenaltyResource> getUnavailablePeriodPenalties() {
		return unavailablePeriodPenalties;
	}

	public void setUnavailablePeriodPenalties(List<UnavailablePeriodPenaltyResource> unavailablePeriodPenalties) {
		this.unavailablePeriodPenalties = unavailablePeriodPenalties;
	}

	public List<LectureResource> getLectures() {
		return lectures;
	}

	public void setLectures(List<LectureResource> lectures) {
		this.lectures = lectures;
	}

	public ScoreResource getScore() {
		return score;
	}

	public void setScore(ScoreResource score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "CourseScheduleResource [id=" + id + ", name=" + name + ", teachers=" + teachers + ", curriculums=" + curriculums + ", courses=" + courses + ", days=" + days + ", timeslots=" + timeslots + ", periods=" + periods + ", rooms=" + rooms + ", unavailablePeriodPenalties=" + unavailablePeriodPenalties + ", lectures=" + lectures + ", score=" + score + "]";
	}

}
