package hr.teosoft.ccp.solver.move;

import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import org.optaplanner.core.impl.heuristic.selector.move.generic.SwapMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import hr.teosoft.ccp.domain.Lecture;

public class DifferentCourseSwapMoveFilter implements SelectionFilter<SwapMove> {

	@Override
	public boolean accept(ScoreDirector scoreDirector, SwapMove move) {
		Lecture leftLecture = (Lecture) move.getLeftEntity();
		Lecture rightLecture = (Lecture) move.getRightEntity();
		return !leftLecture.getCourse().equals(rightLecture.getCourse());
	}

}
