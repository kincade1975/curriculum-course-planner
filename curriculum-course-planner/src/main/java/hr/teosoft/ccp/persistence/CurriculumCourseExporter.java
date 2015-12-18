package hr.teosoft.ccp.persistence;

import java.io.IOException;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionExporter;

import hr.teosoft.ccp.domain.CourseSchedule;
import hr.teosoft.ccp.domain.Lecture;

public class CurriculumCourseExporter extends AbstractTxtSolutionExporter {

	private static final String OUTPUT_FILE_SUFFIX = "sol";

	public static void main(String[] args) {
		new CurriculumCourseExporter().convertAll();
	}

	public CurriculumCourseExporter() {
		super(new CurriculumCourseDao());
	}

	@Override
	public String getOutputFileSuffix() {
		return OUTPUT_FILE_SUFFIX;
	}

	@Override
	public TxtOutputBuilder createTxtOutputBuilder() {
		return new CurriculumCourseOutputBuilder();
	}

	public static class CurriculumCourseOutputBuilder extends TxtOutputBuilder {

		private CourseSchedule schedule;

		@SuppressWarnings("rawtypes")
		@Override
		public void setSolution(Solution solution) {
			schedule = (CourseSchedule) solution;
		}

		@Override
		public void writeSolution() throws IOException {
			for (Lecture lecture : schedule.getLectureList()) {
				bufferedWriter.write(lecture.getCourse().getCode()
						+ " r" + lecture.getRoom().getCode()
						+ " " + lecture.getPeriod().getDay().getDayIndex()
						+ " " + lecture.getPeriod().getTimeslot().getTimeslotIndex() + "\r\n");
			}
		}
	}

}
