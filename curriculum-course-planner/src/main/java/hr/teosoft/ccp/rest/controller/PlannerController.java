package hr.teosoft.ccp.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.teosoft.ccp.rest.resource.CourseScheduleResource;
import hr.teosoft.ccp.rest.service.PlannerService;

@RestController
@ComponentScan({ "hr.teosoft.ccp" })
@RequestMapping(value = { "/api/v1/planner" })
public class PlannerController {

	static final Logger LOGGER = LoggerFactory.getLogger(PlannerController.class);

	@Autowired
	private PlannerService plannerService;

	@RequestMapping(method = RequestMethod.POST)
	public CourseScheduleResource solve(@RequestBody CourseScheduleResource resource) {
		try {
			return plannerService.solve(resource);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
