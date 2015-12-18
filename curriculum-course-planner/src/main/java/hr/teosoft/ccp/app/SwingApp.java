package hr.teosoft.ccp.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.persistence.AbstractSolutionExporter;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.common.swingui.SolutionPanel;

import hr.teosoft.ccp.persistence.CurriculumCourseDao;
import hr.teosoft.ccp.persistence.CurriculumCourseExporter;
import hr.teosoft.ccp.persistence.CurriculumCourseImporter;
import hr.teosoft.ccp.swingui.CurriculumCoursePanel;

public class SwingApp extends CommonApp {

	public static final String SOLVER_CONFIG = "hr/teosoft/ccp/solver/curriculumCourseSolverConfig.xml";

	public static void main(String[] args) {
		prepareSwingEnvironment();
		new SwingApp().init();
	}

	public SwingApp() {
		super("Course timetabling",
				"Official competition name: ITC 2007 track3 - Curriculum course scheduling\n\n" +
						"Assign lectures to periods and rooms.",
						CurriculumCoursePanel.LOGO_PATH);
	}

	@Override
	protected Solver createSolver() {
		SolverFactory solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
		return solverFactory.buildSolver();
	}

	@Override
	protected SolutionPanel createSolutionPanel() {
		return new CurriculumCoursePanel();
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new CurriculumCourseDao();
	}

	@Override
	protected AbstractSolutionImporter[] createSolutionImporters() {
		return new AbstractSolutionImporter[]{
				new CurriculumCourseImporter()
		};
	}

	@Override
	protected AbstractSolutionExporter createSolutionExporter() {
		return new CurriculumCourseExporter();
	}

}
