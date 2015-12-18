package hr.teosoft.ccp.domain.solver;

import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import hr.teosoft.ccp.domain.Lecture;

public class MovableLectureSelectionFilter implements SelectionFilter<Lecture> {

	@Override
	public boolean accept(ScoreDirector scoreDirector, Lecture lecture) {
		return !lecture.isLocked();
	}

}
