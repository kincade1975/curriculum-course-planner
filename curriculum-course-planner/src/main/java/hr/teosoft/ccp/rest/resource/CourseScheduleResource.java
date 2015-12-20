package hr.teosoft.ccp.rest.resource;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="CourseSchedule", description="This resource represents input (problem) and output (solution) of the planning process.")
public class CourseScheduleResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the course schedule set and used internally by the planner.")
	private Long id;

	@JsonProperty("name")
	@ApiObjectField(name="name", required=true, description="Name of the course schedule (e.g. FFOS_2015_1).")
	private String name;

	@JsonProperty("secondsSpentLimit")
	@ApiObjectField(name="secondsSpentLimit", required=false, description="Number of seconds planner will spend trying to find the solution. If not set default value configured for solver is used.")
	private Long secondsSpentLimit;

	@JsonProperty("teachers")
	@ApiObjectField(name="teachers", required=true, description="List of teachers.")
	private List<TeacherResource> teachers;

	@JsonProperty("curriculums")
	@ApiObjectField(name="curriculums", required=true, description="List of curriculums.")
	private List<CurriculumResource> curriculums;

	@JsonProperty("courses")
	@ApiObjectField(name="courses", required=true, description="List of courses.")
	private List<CourseResource> courses;

	@JsonProperty("days")
	@ApiObjectField(name="days", required=true, description="List of days.")
	private List<DayResource> days;

	@JsonProperty("timeslots")
	@ApiObjectField(name="timeslots", required=true, description="List of timeslots.")
	private List<TimeslotResource> timeslots;

	@JsonProperty("periods")
	@ApiObjectField(name="periods", required=false, description="List of periods.")
	private List<PeriodResource> periods;

	@JsonProperty("rooms")
	@ApiObjectField(name="rooms", required=true, description="List of rooms.")
	private List<RoomResource> rooms;

	@JsonProperty("unavailablePeriodPenalties")
	@ApiObjectField(name="unavailablePeriodPenalties", required=false, description="List of unavailable period penalties.")
	private List<UnavailablePeriodPenaltyResource> unavailablePeriodPenalties;

	@JsonProperty("lectures")
	@ApiObjectField(name="lectures", required=false, description="List of lectures that are actually the solution of planning problem.")
	private List<LectureResource> lectures;

	@JsonProperty("score")
	@ApiObjectField(name="score", required=false, description="Score of the planning process (HardSoftScore). It also contains list of unsatisfied contraints.")
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

	public Long getSecondsSpentLimit() {
		return secondsSpentLimit;
	}

	public void setSecondsSpentLimit(Long secondsSpentLimit) {
		this.secondsSpentLimit = secondsSpentLimit;
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
		return "CourseScheduleResource [id=" + id + ", name=" + name + ", secondsSpentLimit=" + secondsSpentLimit + ", teachers=" + teachers + ", curriculums=" + curriculums + ", courses=" + courses + ", days=" + days + ", timeslots=" + timeslots + ", periods=" + periods + ", rooms=" + rooms + ", unavailablePeriodPenalties=" + unavailablePeriodPenalties + ", lectures=" + lectures + ", score=" + score + "]";
	}

}
