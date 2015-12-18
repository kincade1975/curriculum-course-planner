package hr.teosoft.ccp.rest.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import hr.teosoft.ccp.app.SwingApp;
import hr.teosoft.ccp.domain.Course;
import hr.teosoft.ccp.domain.CourseSchedule;
import hr.teosoft.ccp.domain.Curriculum;
import hr.teosoft.ccp.domain.Day;
import hr.teosoft.ccp.domain.Lecture;
import hr.teosoft.ccp.domain.Period;
import hr.teosoft.ccp.domain.Room;
import hr.teosoft.ccp.domain.Teacher;
import hr.teosoft.ccp.domain.Timeslot;
import hr.teosoft.ccp.domain.UnavailablePeriodPenalty;
import hr.teosoft.ccp.rest.resource.CourseResource;
import hr.teosoft.ccp.rest.resource.CourseScheduleResource;
import hr.teosoft.ccp.rest.resource.CurriculumResource;
import hr.teosoft.ccp.rest.resource.DayResource;
import hr.teosoft.ccp.rest.resource.LectureResource;
import hr.teosoft.ccp.rest.resource.PeriodResource;
import hr.teosoft.ccp.rest.resource.RoomResource;
import hr.teosoft.ccp.rest.resource.ScoreResource;
import hr.teosoft.ccp.rest.resource.TeacherResource;
import hr.teosoft.ccp.rest.resource.TimeslotResource;
import hr.teosoft.ccp.rest.resource.UnavailablePeriodPenaltyResource;
import hr.teosoft.ccp.rest.resource.UnsatisfiedConstraint;

@Service
public class PlannerService {

	static final Logger LOGGER = LoggerFactory.getLogger(PlannerService.class);

	private Solver solver;
	private ScoreDirector scoreDirector;

	public PlannerService() {
		if (solver == null) {
			solver = SolverFactory.createFromXmlResource(SwingApp.SOLVER_CONFIG).buildSolver();
			ScoreDirectorFactory scoreDirectorFactory = solver.getScoreDirectorFactory();
			scoreDirector = scoreDirectorFactory.buildScoreDirector();
		}
	}

	public CourseSchedule getSolution() throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			CourseScheduleResource resource = mapper.readValue(new File("/Users/dblazevic/git/curriculum-course-planner/curriculum-course-planner/src/main/resources/ffos_2015_1.json"), CourseScheduleResource.class);
			CourseSchedule entity = convert(resource);
			return entity;
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	public CourseScheduleResource solve(CourseScheduleResource courseScheduleResource) throws Exception {
		CourseSchedule bestSolution = null;
		// convert to planning solution entity
		CourseSchedule courseSchedule = convert(courseScheduleResource);

		// solver planning problem
		solver.solve(courseSchedule);

		// get best solution
		bestSolution = (CourseSchedule) solver.getBestSolution();

		// convert and return best solution
		return convert(bestSolution);
	}

	/**
	 * Method converts course schedule resource into corresponding entity.
	 * @param courseScheduleResource
	 * @return
	 * @throws Exception
	 */
	private CourseSchedule convert(CourseScheduleResource courseScheduleResource) throws Exception {
		CourseSchedule courseSchedule = new CourseSchedule();
		courseSchedule.setId((courseScheduleResource.getId() != null) ? courseScheduleResource.getId() : generateId());
		courseSchedule.setName(courseScheduleResource.getName());

		// teachers
		List<Teacher> teachers = new ArrayList<Teacher>();
		for (TeacherResource resource : courseScheduleResource.getTeachers()) {
			teachers.add(convert(resource));
		}
		courseSchedule.setTeacherList(teachers);

		// curriculums
		List<Curriculum> curriculums = new ArrayList<Curriculum>();
		for (CurriculumResource resource : courseScheduleResource.getCurriculums()) {
			curriculums.add(convert(resource));
		}
		courseSchedule.setCurriculumList(curriculums);

		// rooms
		List<Room> rooms = new ArrayList<Room>();
		for (RoomResource resource : courseScheduleResource.getRooms()) {
			rooms.add(convert(resource));
		}
		courseSchedule.setRoomList(rooms);

		// courses
		List<Course> courses = new ArrayList<Course>();
		for (CourseResource resource : courseScheduleResource.getCourses()) {
			courses.add(convert(resource, teachers, curriculums));
		}
		courseSchedule.setCourseList(courses);

		// days
		List<Day> days = new ArrayList<Day>();
		for (DayResource resource : courseScheduleResource.getDays()) {
			days.add(convert(resource));
		}
		courseSchedule.setDayList(days);

		// timeslots
		List<Timeslot> timeslots = new ArrayList<Timeslot>();
		for (TimeslotResource resource : courseScheduleResource.getTimeslots()) {
			timeslots.add(convert(resource));
		}
		courseSchedule.setTimeslotList(timeslots);

		// periods
		List<Period> periods = new ArrayList<Period>();
		if (courseScheduleResource.getPeriods().isEmpty()) {
			for (Day day : days) {
				for (Timeslot timeslot : timeslots) {
					Period period = new Period();
					period.setId(generateId());
					period.setDay(day);
					period.setTimeslot(timeslot);
					periods.add(period);
					day.getPeriodList().add(period);
				}
			}
		} else {
			for (PeriodResource resource : courseScheduleResource.getPeriods()) {
				periods.add(convert(resource, days, timeslots));
			}

			for (Day day : days) {
				for (Period period : periods) {
					if (day.getDayIndex() == period.getDay().getDayIndex()) {
						day.getPeriodList().add(period);
					}
				}
			}
		}
		courseSchedule.setPeriodList(periods);

		// unavailable period penalties
		List<UnavailablePeriodPenalty> unavailablePeriodPenalties = new ArrayList<UnavailablePeriodPenalty>();
		for (UnavailablePeriodPenaltyResource resource : courseScheduleResource.getUnavailablePeriodPenalties()) {
			unavailablePeriodPenalties.add(convert(resource, courses, periods));
		}
		courseSchedule.setUnavailablePeriodPenaltyList(unavailablePeriodPenalties);

		// lectures
		List<Lecture> lectures = new ArrayList<Lecture>();
		if (courseScheduleResource.getLectures().isEmpty()) {
			for (Course course : courses) {
				for (int i = 0; i < course.getLectureSize(); i++) {
					Lecture lecture = new Lecture();
					lecture.setId(generateId());
					lecture.setCourse(course);
					lecture.setLectureIndexInCourse(i);
					lecture.setLocked(false);
					lectures.add(lecture);
				}
			}
		} else {
			for (LectureResource resource : courseScheduleResource.getLectures()) {
				lectures.add(convert(resource, courses, periods, rooms));
			}
		}
		courseSchedule.setLectureList(lectures);

		return courseSchedule;
	}

	/**
	 * Method converts course schedule entity into corresponding resource.
	 * @param bestSolution
	 * @return
	 * @throws Exception
	 */
	private CourseScheduleResource convert(CourseSchedule bestSolution) throws Exception {
		CourseScheduleResource courseScheduleResource = new CourseScheduleResource();
		courseScheduleResource.setId(bestSolution.getId());
		courseScheduleResource.setName(bestSolution.getName());

		// teachers
		List<TeacherResource> teachers = new ArrayList<TeacherResource>();
		for (Teacher entity : bestSolution.getTeacherList()) {
			teachers.add(convert(entity));
		}
		courseScheduleResource.setTeachers(teachers);

		// curriculums
		List<CurriculumResource> curriculums = new ArrayList<CurriculumResource>();
		for (Curriculum entity : bestSolution.getCurriculumList()) {
			curriculums.add(convert(entity));
		}
		courseScheduleResource.setCurriculums(curriculums);

		// rooms
		List<RoomResource> rooms = new ArrayList<RoomResource>();
		for (Room enity : bestSolution.getRoomList()) {
			rooms.add(convert(enity));
		}
		courseScheduleResource.setRooms(rooms);

		// courses
		List<CourseResource> courses = new ArrayList<CourseResource>();
		for (Course enity : bestSolution.getCourseList()) {
			courses.add(convert(enity));
		}
		courseScheduleResource.setCourses(courses);

		// days
		List<DayResource> days = new ArrayList<DayResource>();
		for (Day entity : bestSolution.getDayList()) {
			days.add(convert(entity));
		}
		courseScheduleResource.setDays(days);

		// timeslots
		List<TimeslotResource> timeslots = new ArrayList<TimeslotResource>();
		for (Timeslot entity : bestSolution.getTimeslotList()) {
			timeslots.add(convert(entity));
		}
		courseScheduleResource.setTimeslots(timeslots);

		// periods
		List<PeriodResource> periods = new ArrayList<PeriodResource>();
		for (Period entity : bestSolution.getPeriodList()) {
			periods.add(convert(entity));
		}
		courseScheduleResource.setPeriods(periods);

		// unavailable period penalties
		List<UnavailablePeriodPenaltyResource> unavailablePeriodPenalties = new ArrayList<UnavailablePeriodPenaltyResource>();
		for (UnavailablePeriodPenalty entity : bestSolution.getUnavailablePeriodPenaltyList()) {
			unavailablePeriodPenalties.add(convert(entity));
		}
		courseScheduleResource.setUnavailablePeriodPenalties(unavailablePeriodPenalties);

		// lectures
		List<LectureResource> lectures = new ArrayList<LectureResource>();
		for (Lecture entity : bestSolution.getLectureList()) {
			lectures.add(convert(entity));
		}
		courseScheduleResource.setLectures(lectures);

		// score
		ScoreResource score = new ScoreResource();
		scoreDirector.setWorkingSolution(bestSolution);
		score.setScore(scoreDirector.calculateScore().toString());

		for (ConstraintMatchTotal constraintMatchTotal : scoreDirector.getConstraintMatchTotals()) {
			UnsatisfiedConstraint unsatisfiedConstraint = new UnsatisfiedConstraint();
			unsatisfiedConstraint.setConstraintName(constraintMatchTotal.getConstraintName());
			unsatisfiedConstraint.setWeightTotal(constraintMatchTotal.getWeightTotalAsNumber());

			score.getUnsatisfiedConstraints().add(unsatisfiedConstraint);

			for (ConstraintMatch constraintMatch : constraintMatchTotal.getConstraintMatchSet()) {
				unsatisfiedConstraint.setDetails(constraintMatch.toString());
			}
		}

		courseScheduleResource.setScore(score);

		return courseScheduleResource;
	}

	/**
	 * Helper method that converts teacher resource to corresponding entity.
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	private Teacher convert(TeacherResource resource) throws Exception {
		Teacher entity = new Teacher();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setCode(resource.getCode());
		return entity;
	}

	/**
	 * Helper method that converts teacher entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private TeacherResource convert(Teacher entity) throws Exception {
		TeacherResource resource = new TeacherResource();
		resource.setId(entity.getId());
		resource.setCode(entity.getCode());
		return resource;
	}

	/**
	 * Helper method that converts curriculum resource to corresponding entity.
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	private Curriculum convert(CurriculumResource resource) throws Exception {
		Curriculum entity = new Curriculum();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setCode(resource.getCode());
		return entity;
	}

	/**
	 * Helper method that converts curriculum entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private CurriculumResource convert(Curriculum entity) throws Exception {
		CurriculumResource resource = new CurriculumResource();
		resource.setId(entity.getId());
		resource.setCode(entity.getCode());
		return resource;
	}

	/**
	 * Helper method that converts room resource to corresponding entity.
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	private Room convert(RoomResource resource) throws Exception {
		Room entity = new Room();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setCode(resource.getCode());
		entity.setType(resource.getType());
		entity.setCapacity(resource.getCapacity());
		return entity;
	}

	/**
	 * Helper method that converts room entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private RoomResource convert(Room entity) throws Exception {
		RoomResource resource = new RoomResource();
		resource.setId(entity.getId());
		resource.setCode(entity.getCode());
		resource.setType(entity.getType());
		resource.setCapacity(entity.getCapacity());
		return resource;
	}

	/**
	 * Helper method that converts day resource to corresponding entity.
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	private Day convert(DayResource resource) throws Exception {
		Day entity = new Day();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setDayIndex(resource.getDayIndex());
		entity.setPeriodList(new ArrayList<Period>());
		return entity;
	}

	/**
	 * Helper method that converts day entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private DayResource convert(Day entity) throws Exception {
		DayResource resource = new DayResource();
		resource.setId(entity.getId());
		resource.setDayIndex(entity.getDayIndex());
		return resource;
	}

	/**
	 * Helper method that converts timeslot resource to corresponding entity.
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	private Timeslot convert(TimeslotResource resource) throws Exception {
		Timeslot entity = new Timeslot();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setTimeslotIndex(resource.getTimeslotIndex());
		return entity;
	}

	/**
	 * Helper method that converts timeslot entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private TimeslotResource convert(Timeslot entity) throws Exception {
		TimeslotResource resource = new TimeslotResource();
		resource.setId(entity.getId());
		resource.setTimeslotIndex(entity.getTimeslotIndex());
		return resource;
	}

	/**
	 * Helper method that converts period resource to corresponding entity.
	 * @param resource
	 * @param days
	 * @param timeslots
	 * @return
	 * @throws Exception
	 */
	private Period convert(PeriodResource resource, List<Day> days, List<Timeslot> timeslots) throws Exception {
		Period entity = new Period();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setDay(getDay(resource.getCode(), days));
		entity.setTimeslot(getTimeslot(resource.getCode(), timeslots));
		return entity;
	}

	/**
	 * Helper method that converts period entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private PeriodResource convert(Period entity) throws Exception {
		PeriodResource resource = new PeriodResource();
		resource.setId(entity.getId());
		resource.setCode(entity.getDay().getDayIndex() + "-" + entity.getTimeslot().getTimeslotIndex());
		return resource;
	}

	/**
	 * Helper method that converts unavailable period penalty resource to corresponding entity.
	 * @param resource
	 * @param courses
	 * @param periods
	 * @return
	 * @throws Exception
	 */
	private UnavailablePeriodPenalty convert(UnavailablePeriodPenaltyResource resource, List<Course> courses, List<Period> periods) throws Exception {
		UnavailablePeriodPenalty entity = new UnavailablePeriodPenalty();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setCourse(getCourse(resource.getCourseCode(), courses));
		entity.setPeriod(getPeriod(resource.getPeriodCode(), periods));
		return entity;
	}

	/**
	 * Helper method that converts unavailable period penalty entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private UnavailablePeriodPenaltyResource convert(UnavailablePeriodPenalty entity) throws Exception {
		UnavailablePeriodPenaltyResource resource = new UnavailablePeriodPenaltyResource();
		resource.setId(entity.getId());
		resource.setCourseCode(entity.getCourse().getCode());
		resource.setPeriodCode(entity.getPeriod().getDay().getDayIndex() + "-" + entity.getPeriod().getTimeslot().getTimeslotIndex());
		return resource;
	}

	/**
	 * Helper method that converts course resource to corresponding entity.
	 * @param resource
	 * @param teachers
	 * @param curriculums
	 * @return
	 * @throws Exception
	 */
	private Course convert(CourseResource resource, List<Teacher> teachers, List<Curriculum> curriculums) throws Exception {
		Course entity = new Course();
		entity.setId((resource.getId() != null) ? resource.getId() : generateId());
		entity.setCode(resource.getCode());
		entity.setTeacher(getTeacher(resource.getTeacherCode(), teachers));
		entity.setLectureSize(resource.getLectureSize());
		entity.setMinWorkingDaySize(resource.getMinWorkingDaySize());
		entity.setCurriculumList(getCurriculums(resource.getCurriculumCodes(), curriculums));
		entity.setStudentSize(resource.getStudentSize());
		entity.setType(resource.getType());
		entity.setPreferredDays(resource.getPreferredDays());
		entity.setPreferredTimeslots(resource.getPreferredTimeslots());
		return entity;
	}

	/**
	 * Helper method that converts course entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private CourseResource convert(Course entity) throws Exception {
		CourseResource resource = new CourseResource();
		resource.setId(entity.getId());
		resource.setCode(entity.getCode());
		resource.setTeacherCode(entity.getTeacher().getCode());
		resource.setLectureSize(entity.getLectureSize());
		resource.setMinWorkingDaySize(entity.getMinWorkingDaySize());
		resource.setCurriculumCodes(getCurriculumCodes(entity.getCurriculumList()));
		resource.setStudentSize(entity.getStudentSize());
		resource.setType(entity.getType());
		resource.setPreferredDays(entity.getPreferredDays());
		resource.setPreferredTimeslots(entity.getPreferredTimeslots());
		return resource;
	}

	private Lecture convert(LectureResource resource, List<Course> courses, List<Period> periods, List<Room> rooms) throws Exception {
		Lecture entity = new Lecture();
		entity.setId(resource.getId());
		entity.setCourse(getCourse(resource.getCourseCode(), courses));
		entity.setLectureIndexInCourse(resource.getLectureIndexInCourse());
		entity.setLocked(resource.isLocked());
		entity.setPeriod(getPeriod(resource.getPeriodCode(), periods));
		entity.setRoom(getRoom(resource.getRoomCode(), rooms));
		return entity;
	}

	/**
	 * Helper method that converts lecture entity to corresponding resource.
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private LectureResource convert(Lecture entity) throws Exception {
		LectureResource resource = new LectureResource();
		resource.setId(entity.getId());
		resource.setCourseCode(entity.getCourse().getCode());
		resource.setLectureIndexInCourse(entity.getLectureIndexInCourse());
		resource.setLocked(entity.isLocked());
		resource.setPeriodCode(entity.getPeriod().getDay().getDayIndex() + "-" + entity.getPeriod().getTimeslot().getTimeslotIndex());
		resource.setRoomCode(entity.getRoom().getCode());
		return resource;
	}

	/**
	 * Helper method that returns teacher with given code.
	 * @param code
	 * @param teachers
	 * @return
	 * @throws Exception
	 */
	private Teacher getTeacher(String code, List<Teacher> teachers) throws Exception {
		for (Teacher teacher : teachers) {
			if (teacher.getCode().equals(code)) {
				return teacher;
			}
		}
		throw new Exception("Teacher with code [" + code + "] not found!!!");
	}

	/**
	 * Helper method that returns course with given code.
	 * @param code
	 * @param courses
	 * @return
	 * @throws Exception
	 */
	private Course getCourse(String code, List<Course> courses) throws Exception {
		for (Course course : courses) {
			if (course.getCode().equals(code)) {
				return course;
			}
		}
		throw new Exception("Course with code [" + code + "] not found!!!");
	}

	/**
	 * Helper method that returns curriculums with given codes.
	 * @param codes
	 * @param curriculums
	 * @return
	 * @throws Exception
	 */
	private List<Curriculum> getCurriculums(List<String> codes, List<Curriculum> curriculums) throws Exception {
		List<Curriculum> result = new ArrayList<Curriculum>();
		for (String code : codes) {
			result.add(getCurriculum(code, curriculums));
		}
		return result;
	}

	/**
	 * Helper method that returns curriculum with given code.
	 * @param code
	 * @param curriculums
	 * @return
	 * @throws Exception
	 */
	private Curriculum getCurriculum(String code, List<Curriculum> curriculums) throws Exception {
		for (Curriculum curriculum : curriculums) {
			if (curriculum.getCode().equals(code)) {
				return curriculum;
			}
		}
		throw new Exception("Curriculum with code [" + code + "] not found!!!");
	}

	/**
	 * Helper method that returns list of curriculum codes for given curriculum list.
	 * @param curriculums
	 * @return
	 * @throws Exception
	 */
	private List<String> getCurriculumCodes(List<Curriculum> curriculums) throws Exception {
		List<String> result = new ArrayList<>();
		for (Curriculum curriculum : curriculums) {
			result.add(curriculum.getCode());
		}
		return result;
	}

	/**
	 * Helper method that returns day from given period code.
	 * @param code
	 * @param days
	 * @return
	 * @throws Exception
	 */
	private Day getDay(String code, List<Day> days) throws Exception {
		int dayIndex = Integer.parseInt(code.substring(0, code.indexOf("-")));
		for (Day day : days) {
			if (day.getDayIndex() == dayIndex) {
				return day;
			}
		}
		throw new Exception("Day with index [" + dayIndex + "] not found!!!");
	}

	/**
	 * Helper method that returns timeslot from given period code.
	 * @param code
	 * @param timeslots
	 * @return
	 * @throws Exception
	 */
	private Timeslot getTimeslot(String code, List<Timeslot> timeslots) throws Exception {
		int timeslotIndex = Integer.parseInt(code.substring(code.indexOf("-") + 1));
		for (Timeslot timeslot : timeslots) {
			if (timeslot.getTimeslotIndex() == timeslotIndex) {
				return timeslot;
			}
		}
		throw new Exception("Timeslot with index [" + timeslotIndex + "] not found!!!");
	}

	/**
	 * Helper method that returns period with given period code.
	 * @param code
	 * @param periods
	 * @return
	 * @throws Exception
	 */
	private Period getPeriod(String code, List<Period> periods) throws Exception {
		int dayIndex = Integer.parseInt(code.substring(0, code.indexOf("-")));
		int timeslotIndex = Integer.parseInt(code.substring(code.indexOf("-") + 1));
		for (Period period : periods) {
			if (period.getDay().getDayIndex() == dayIndex && period.getTimeslot().getTimeslotIndex() == timeslotIndex) {
				return period;
			}
		}
		throw new Exception("Period with code [" + code + "] not found!!!");
	}

	/**
	 * Helper method that returns room with given code.
	 * @param code
	 * @param rooms
	 * @return
	 * @throws Exception
	 */
	private Room getRoom(String code, List<Room> rooms) throws Exception {
		for (Room room : rooms) {
			if (room.getCode().equals(code)) {
				return room;
			}
		}
		throw new Exception("Room with code [" + code + "] not found!!!");
	}

	/**
	 * Helper method that generates and returns unique identifier of the entity.
	 * @return
	 * @throws Exception
	 */
	private long generateId() throws Exception {
		long LOWER_RANGE = 1000000;
		long UPPER_RANGE = 9999999;
		return LOWER_RANGE + (long)(new Random().nextDouble()*(UPPER_RANGE - LOWER_RANGE));
	}

}
