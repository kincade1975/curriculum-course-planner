package hr.teosoft.ccp.persistence;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;

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
import hr.teosoft.ccp.rest.service.PlannerService;

public class CurriculumCourseImporter extends AbstractTxtSolutionImporter {

	private static final String INPUT_FILE_SUFFIX = "ctt";

	public static void main(String[] args) {
		new CurriculumCourseImporter().convertAll();
	}

	public CurriculumCourseImporter() {
		super(new CurriculumCourseDao());
	}

	@Override
	public String getInputFileSuffix() {
		return INPUT_FILE_SUFFIX;
	}

	@Override
	public TxtInputBuilder createTxtInputBuilder() {
		return new CurriculumCourseInputBuilder();
	}

	public static class CurriculumCourseInputBuilder extends TxtInputBuilder {

		@SuppressWarnings("rawtypes")
		@Override
		public Solution readSolution() throws IOException {
			if (Boolean.TRUE) {
				return new PlannerService().getSolution();
			} else {
				CourseSchedule schedule = new CourseSchedule();
				schedule.setId(0L);
				// Name: ToyExample
				schedule.setName(readStringValue("Name:"));
				// Courses: 4
				int courseListSize = readIntegerValue("Courses:");
				// Rooms: 2
				int roomListSize = readIntegerValue("Rooms:");
				// Days: 5
				int dayListSize = readIntegerValue("Days:");
				// Periods_per_day: 4
				int timeslotListSize = readIntegerValue("Periods_per_day:");
				// Curricula: 2
				int curriculumListSize = readIntegerValue("Curricula:");
				// Constraints: 8
				int unavailablePeriodPenaltyListSize = readIntegerValue("Constraints:");

				Map<String, Course> courseMap = readCourseListAndTeacherList(schedule, courseListSize);
				readRoomList(schedule, roomListSize);
				Map<List<Integer>, Period> periodMap = createPeriodListAndDayListAndTimeslotList(schedule, dayListSize, timeslotListSize);
				readCurriculumList(schedule, courseMap, curriculumListSize);
				readUnavailablePeriodPenaltyList(schedule, courseMap, periodMap, unavailablePeriodPenaltyListSize);
				readEmptyLine();
				readConstantLine("END\\.");
				createLectureList(schedule);

				int possibleForOneLectureSize = schedule.getPeriodList().size() * schedule.getRoomList().size();
				BigInteger possibleSolutionSize = BigInteger.valueOf(possibleForOneLectureSize).pow(
						schedule.getLectureList().size());
				logger.info("CourseSchedule {} has {} teachers, {} curricula, {} courses, {} lectures," +
						" {} periods, {} rooms and {} unavailable period constraints with a search space of {}.",
						getInputId(),
						schedule.getTeacherList().size(),
						schedule.getCurriculumList().size(),
						schedule.getCourseList().size(),
						schedule.getLectureList().size(),
						schedule.getPeriodList().size(),
						schedule.getRoomList().size(),
						schedule.getUnavailablePeriodPenaltyList().size(),
						getFlooredPossibleSolutionSize(possibleSolutionSize));
				return schedule;
			}
		}

		private Map<String, Course> readCourseListAndTeacherList(
				CourseSchedule schedule, int courseListSize) throws IOException {
			Map<String, Course> courseMap = new HashMap<String, Course>(courseListSize);
			Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
			List<Course> courseList = new ArrayList<Course>(courseListSize);
			readEmptyLine();
			readConstantLine("COURSES:");
			for (int i = 0; i < courseListSize; i++) {
				Course course = new Course();
				course.setId((long) i);
				// Courses: <CourseID> <Teacher> <# Lectures> <MinWorkingDays> <# Students> <Type> <PreferredDays> <PreferredTimeslotIndex>
				String line = bufferedReader.readLine();
				String[] lineTokens = splitBySpacesOrTabs(line, 8);
				course.setCode(lineTokens[0]);
				course.setTeacher(findOrCreateTeacher(teacherMap, lineTokens[1]));
				course.setLectureSize(Integer.parseInt(lineTokens[2]));
				course.setMinWorkingDaySize(Integer.parseInt(lineTokens[3]));
				course.setCurriculumList(new ArrayList<Curriculum>());
				course.setStudentSize(Integer.parseInt(lineTokens[4]));
				course.setType(lineTokens[5]);

				if (!lineTokens[6].equalsIgnoreCase("-1")) {
					List<Integer> preferredDays = new ArrayList<>();
					String[] array = lineTokens[6].split("\\|");
					for (String str : array) {
						preferredDays.add(Integer.parseInt(str));
					}
					course.setPreferredDays(preferredDays);
				}

				if (!lineTokens[7].equalsIgnoreCase("-1")) {
					List<Integer> preferredTimeslots = new ArrayList<>();
					String[] array = lineTokens[7].split("\\|");
					for (String str : array) {
						preferredTimeslots.add(Integer.parseInt(str));
					}
					course.setPreferredTimeslots(preferredTimeslots);
				}

				courseList.add(course);
				courseMap.put(course.getCode(), course);
			}
			schedule.setCourseList(courseList);
			List<Teacher> teacherList = new ArrayList<Teacher>(teacherMap.values());
			schedule.setTeacherList(teacherList);
			return courseMap;
		}

		private Teacher findOrCreateTeacher(Map<String, Teacher> teacherMap, String code) {
			Teacher teacher = teacherMap.get(code);
			if (teacher == null) {
				teacher = new Teacher();
				int id = teacherMap.size();
				teacher.setId((long) id);
				teacher.setCode(code);
				teacherMap.put(code, teacher);
			}
			return teacher;
		}

		private void readRoomList(CourseSchedule schedule, int roomListSize)
				throws IOException {
			readEmptyLine();
			readConstantLine("ROOMS:");
			List<Room> roomList = new ArrayList<Room>(roomListSize);
			for (int i = 0; i < roomListSize; i++) {
				Room room = new Room();
				room.setId((long) i);
				// Rooms: <RoomID> <Capacity> <Type>
				String line = bufferedReader.readLine();
				String[] lineTokens = splitBySpacesOrTabs(line, 3);
				room.setCode(lineTokens[0]);
				room.setCapacity(Integer.parseInt(lineTokens[1]));
				room.setType(lineTokens[2]);
				roomList.add(room);
			}
			schedule.setRoomList(roomList);
		}

		private Map<List<Integer>, Period> createPeriodListAndDayListAndTimeslotList(
				CourseSchedule schedule, int dayListSize, int timeslotListSize) throws IOException {
			int periodListSize = dayListSize * timeslotListSize;
			Map<List<Integer>, Period> periodMap = new HashMap<List<Integer>, Period>(periodListSize);
			List<Day> dayList = new ArrayList<Day>(dayListSize);
			for (int i = 0; i < dayListSize; i++) {
				Day day = new Day();
				day.setId((long) i);
				day.setDayIndex(i);
				day.setPeriodList(new ArrayList<Period>(timeslotListSize));
				dayList.add(day);
			}
			schedule.setDayList(dayList);
			List<Timeslot> timeslotList = new ArrayList<Timeslot>(timeslotListSize);
			for (int i = 0; i < timeslotListSize; i++) {
				Timeslot timeslot = new Timeslot();
				timeslot.setId((long) i);
				timeslot.setTimeslotIndex(i);
				timeslotList.add(timeslot);
			}
			schedule.setTimeslotList(timeslotList);
			List<Period> periodList = new ArrayList<Period>(periodListSize);
			for (int i = 0; i < dayListSize; i++) {
				Day day = dayList.get(i);
				for (int j = 0; j < timeslotListSize; j++) {
					Period period = new Period();
					period.setId((long) (i * timeslotListSize + j));
					period.setDay(day);
					period.setTimeslot(timeslotList.get(j));
					periodList.add(period);
					periodMap.put(Arrays.asList(i, j), period);
					day.getPeriodList().add(period);
				}
			}
			schedule.setPeriodList(periodList);
			return periodMap;
		}

		private void readCurriculumList(CourseSchedule schedule,
				Map<String, Course> courseMap, int curriculumListSize) throws IOException {
			readEmptyLine();
			readConstantLine("CURRICULA:");
			List<Curriculum> curriculumList = new ArrayList<Curriculum>(curriculumListSize);
			for (int i = 0; i < curriculumListSize; i++) {
				Curriculum curriculum = new Curriculum();
				curriculum.setId((long) i);
				// Curricula: <CurriculumID> <# Courses> <MemberID> ... <MemberID>
				String line = bufferedReader.readLine();
				String[] lineTokens = splitBySpacesOrTabs(line);
				if (lineTokens.length < 2) {
					throw new IllegalArgumentException("Read line (" + line
							+ ") is expected to contain at least 2 tokens.");
				}
				curriculum.setCode(lineTokens[0]);
				int coursesInCurriculum = Integer.parseInt(lineTokens[1]);
				if (lineTokens.length != (coursesInCurriculum + 2)) {
					throw new IllegalArgumentException("Read line (" + line + ") is expected to contain "
							+ (coursesInCurriculum + 2) + " tokens.");
				}
				for (int j = 2; j < lineTokens.length; j++) {
					Course course = courseMap.get(lineTokens[j]);
					if (course == null) {
						throw new IllegalArgumentException("Read line (" + line + ") uses an unexisting course("
								+ lineTokens[j] + ").");
					}
					course.getCurriculumList().add(curriculum);
				}
				curriculumList.add(curriculum);
			}
			schedule.setCurriculumList(curriculumList);
		}

		private void readUnavailablePeriodPenaltyList(CourseSchedule schedule, Map<String, Course> courseMap,
				Map<List<Integer>, Period> periodMap, int unavailablePeriodPenaltyListSize)
						throws IOException {
			readEmptyLine();
			readConstantLine("UNAVAILABILITY_CONSTRAINTS:");
			List<UnavailablePeriodPenalty> penaltyList = new ArrayList<UnavailablePeriodPenalty>(
					unavailablePeriodPenaltyListSize);
			for (int i = 0; i < unavailablePeriodPenaltyListSize; i++) {
				UnavailablePeriodPenalty penalty = new UnavailablePeriodPenalty();
				penalty.setId((long) i);
				// Unavailability_Constraints: <CourseID> <Day> <Day_Period>
				String line = bufferedReader.readLine();
				String[] lineTokens = splitBySpacesOrTabs(line, 3);
				penalty.setCourse(courseMap.get(lineTokens[0]));
				int dayIndex = Integer.parseInt(lineTokens[1]);
				int timeslotIndex = Integer.parseInt(lineTokens[2]);
				Period period = periodMap.get(Arrays.asList(dayIndex, timeslotIndex));
				if (period == null) {
					throw new IllegalArgumentException("Read line (" + line + ") uses an unexisting period("
							+ dayIndex + " " + timeslotIndex + ").");
				}
				penalty.setPeriod(period);
				penaltyList.add(penalty);
			}
			schedule.setUnavailablePeriodPenaltyList(penaltyList);
		}

		private void createLectureList(CourseSchedule schedule) {
			List<Course> courseList = schedule.getCourseList();
			List<Lecture> lectureList = new ArrayList<Lecture>(courseList.size());
			long id = 0L;
			for (Course course : courseList) {
				for (int i = 0; i < course.getLectureSize(); i++) {
					Lecture lecture = new Lecture();
					lecture.setId(id);
					id++;
					lecture.setCourse(course);
					lecture.setLectureIndexInCourse(i);
					lecture.setLocked(false);
					// Notice that we leave the PlanningVariable properties on null
					lectureList.add(lecture);
				}
			}
			schedule.setLectureList(lectureList);
		}

	}

}
