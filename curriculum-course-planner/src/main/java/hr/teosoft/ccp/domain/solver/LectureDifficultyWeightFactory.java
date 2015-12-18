package hr.teosoft.ccp.domain.solver;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

import hr.teosoft.ccp.domain.Course;
import hr.teosoft.ccp.domain.CourseSchedule;
import hr.teosoft.ccp.domain.Lecture;
import hr.teosoft.ccp.domain.UnavailablePeriodPenalty;

public class LectureDifficultyWeightFactory implements SelectionSorterWeightFactory<CourseSchedule, Lecture> {

	@SuppressWarnings("rawtypes")
	@Override
	public Comparable createSorterWeight(CourseSchedule schedule, Lecture lecture) {
		Course course = lecture.getCourse();
		int unavailablePeriodPenaltyCount = 0;
		for (UnavailablePeriodPenalty penalty : schedule.getUnavailablePeriodPenaltyList()) {
			if (penalty.getCourse().equals(course)) {
				unavailablePeriodPenaltyCount++;
			}
		}
		return new LectureDifficultyWeight(lecture, unavailablePeriodPenaltyCount);
	}

	public static class LectureDifficultyWeight implements Comparable<LectureDifficultyWeight> {

		private final Lecture lecture;
		private final int unavailablePeriodPenaltyCount;

		public LectureDifficultyWeight(Lecture lecture, int unavailablePeriodPenaltyCount) {
			this.lecture = lecture;
			this.unavailablePeriodPenaltyCount = unavailablePeriodPenaltyCount;
		}

		@Override
		public int compareTo(LectureDifficultyWeight other) {
			Course course = lecture.getCourse();
			Course otherCourse = other.lecture.getCourse();
			return new CompareToBuilder()
					.append(course.getCurriculumList().size(), otherCourse.getCurriculumList().size())
					.append(unavailablePeriodPenaltyCount, other.unavailablePeriodPenaltyCount)
					.append(course.getLectureSize(), otherCourse.getLectureSize())
					.append(course.getStudentSize(), otherCourse.getStudentSize())
					.append(course.getMinWorkingDaySize(), otherCourse.getMinWorkingDaySize())
					.append(lecture.getId(), other.lecture.getId())
					.toComparison();
		}

	}

}
