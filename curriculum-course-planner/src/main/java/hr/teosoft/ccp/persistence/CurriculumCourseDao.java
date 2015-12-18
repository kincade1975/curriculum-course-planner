package hr.teosoft.ccp.persistence;

import org.optaplanner.examples.common.persistence.XStreamSolutionDao;

import hr.teosoft.ccp.domain.CourseSchedule;

public class CurriculumCourseDao extends XStreamSolutionDao {

	public CurriculumCourseDao() {
		super("ccp", CourseSchedule.class);
	}

}
