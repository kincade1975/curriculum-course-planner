package hr.teosoft.ccp.domain;

import java.util.List;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Each course consists of a fixed number of lectures to be scheduled in distinct periods.
 * It is attended by given number of students, and is taught by a teacher.
 * For each course there is a minimum number of days that the lectures of the course should
 * be spread in, moreover there are some periods in which the course cannot be scheduled.
 */
@XStreamAlias("Course")
public class Course extends AbstractPersistable {

	private static final long serialVersionUID = 6869875289446074508L;

	private String code;

	private Teacher teacher;
	private int lectureSize;
	private int minWorkingDaySize;

	private List<Curriculum> curriculumList;
	private int studentSize;

	/** type (e.g. P - predavanje, V - vjezbe, S - seminar) */
	private String type;

	/** preferred days (e.g. 0 = Monday, 1 = Tuesday, etc) */
	private List<Integer> preferredDays;

	/** preferred timeslots (e.g. 0 - 07:15, 1 - 08:00, etc) */
	private List<Integer> preferredTimeslots;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getLectureSize() {
		return lectureSize;
	}

	public void setLectureSize(int lectureSize) {
		this.lectureSize = lectureSize;
	}

	public int getMinWorkingDaySize() {
		return minWorkingDaySize;
	}

	public void setMinWorkingDaySize(int minWorkingDaySize) {
		this.minWorkingDaySize = minWorkingDaySize;
	}

	public List<Curriculum> getCurriculumList() {
		return curriculumList;
	}

	public void setCurriculumList(List<Curriculum> curriculumList) {
		this.curriculumList = curriculumList;
	}

	public int getStudentSize() {
		return studentSize;
	}

	public void setStudentSize(int studentSize) {
		this.studentSize = studentSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return code;
	}

}
