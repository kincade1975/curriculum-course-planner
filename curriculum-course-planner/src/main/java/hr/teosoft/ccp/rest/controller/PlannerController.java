package hr.teosoft.ccp.rest.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiVerb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.teosoft.ccp.rest.resource.CourseScheduleResource;
import hr.teosoft.ccp.rest.service.PlannerService;

@RestController
@ComponentScan({ "hr.teosoft.ccp" })
@RequestMapping(value = { "/api/v1/planner" })
@Api(name="Planner", description="ReST service for planning curriculum courses")
@ApiVersion(since="1.0.0")
public class PlannerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerController.class);

	@Autowired
	private PlannerService plannerService;

	@RequestMapping(method = RequestMethod.POST)
	@ApiMethod(verb=ApiVerb.POST, description="Solve planning problem.", consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ApiResponseObject CourseScheduleResource solve(@ApiBodyObject @RequestBody CourseScheduleResource resource) {
		try {
			return plannerService.solve(resource);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
