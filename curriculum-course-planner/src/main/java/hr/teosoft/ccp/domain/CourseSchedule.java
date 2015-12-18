package hr.teosoft.ccp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.buildin.hardsoft.HardSoftScoreDefinition;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.persistence.xstream.impl.score.XStreamScoreConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import hr.teosoft.ccp.domain.solver.CourseConflict;

@PlanningSolution
@XStreamAlias("CourseSchedule")
public class CourseSchedule extends AbstractPersistable implements Solution<HardSoftScore> {

	private static final long serialVersionUID = -2488767120140321376L;

	private String name;

	private List<Teacher> teacherList;
	private List<Curriculum> curriculumList;
	private List<Course> courseList;
	private List<Day> dayList;
	private List<Timeslot> timeslotList;
	private List<Period> periodList;
	private List<Room> roomList;

	private List<UnavailablePeriodPenalty> unavailablePeriodPenaltyList;

	private List<Lecture> lectureList;

	@XStreamConverter(value = XStreamScoreConverter.class, types = {HardSoftScoreDefinition.class})
	private HardSoftScore score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Teacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

	public List<Curriculum> getCurriculumList() {
		return curriculumList;
	}

	public void setCurriculumList(List<Curriculum> curriculumList) {
		this.curriculumList = curriculumList;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	public List<Day> getDayList() {
		return dayList;
	}

	public void setDayList(List<Day> dayList) {
		this.dayList = dayList;
	}

	public List<Timeslot> getTimeslotList() {
		return timeslotList;
	}

	public void setTimeslotList(List<Timeslot> timeslotList) {
		this.timeslotList = timeslotList;
	}

	@ValueRangeProvider(id = "periodRange")
	public List<Period> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<Period> periodList) {
		this.periodList = periodList;
	}

	@ValueRangeProvider(id = "roomRange")
	public List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public List<UnavailablePeriodPenalty> getUnavailablePeriodPenaltyList() {
		return unavailablePeriodPenaltyList;
	}

	public void setUnavailablePeriodPenaltyList(List<UnavailablePeriodPenalty> unavailablePeriodPenaltyList) {
		this.unavailablePeriodPenaltyList = unavailablePeriodPenaltyList;
	}

	@PlanningEntityCollectionProperty
	public List<Lecture> getLectureList() {
		return lectureList;
	}

	public void setLectureList(List<Lecture> lectureList) {
		this.lectureList = lectureList;
	}

	@Override
	public HardSoftScore getScore() {
		return score;
	}

	@Override
	public void setScore(HardSoftScore score) {
		this.score = score;
	}

	// ************************************************************************
	// Complex methods
	// ************************************************************************

	@Override
	public Collection<? extends Object> getProblemFacts() {
		List<Object> facts = new ArrayList<Object>();
		facts.addAll(teacherList);
		facts.addAll(curriculumList);
		facts.addAll(courseList);
		facts.addAll(dayList);
		facts.addAll(timeslotList);
		facts.addAll(periodList);
		facts.addAll(roomList);
		facts.addAll(unavailablePeriodPenaltyList);
		facts.addAll(precalculateCourseConflictList());
		// Do not add the planning entity's (lectureList) because that will be done automatically
		return facts;
	}

	private List<CourseConflict> precalculateCourseConflictList() {
		List<CourseConflict> courseConflictList = new ArrayList<CourseConflict>();
		for (Course leftCourse : courseList) {
			for (Course rightCourse : courseList) {
				if (leftCourse.getId() < rightCourse.getId()) {
					int conflictCount = 0;
					if (leftCourse.getTeacher().equals(rightCourse.getTeacher())) {
						conflictCount++;
					}
					for (Curriculum curriculum : leftCourse.getCurriculumList()) {
						if (rightCourse.getCurriculumList().contains(curriculum)) {
							conflictCount++;
						}
					}
					if (conflictCount > 0) {
						courseConflictList.add(new CourseConflict(leftCourse, rightCourse, conflictCount));
					}
				}
			}
		}
		return courseConflictList;
	}

}
