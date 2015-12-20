package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Day", description="We are given a number of teaching days in the week (typically 5 or 6). Each day is split in a fixed number of time-slots, which is equal for all days. A period is a pair composed of a day and a time-slot. The total number of scheduling periods is the product of the days times the day time-slots.")
public class DayResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the day set and used internally by the planner.")
	private Long id;

	@JsonProperty("dayIndex")
	@ApiObjectField(name="dayIndex", required=true, description="Identifies day of the week (0 = Monday, 1 = Tuesday, 2 = Wednesday, 3 = Thursday, 4 = Friday, 5 = Saturday, 6 = Sunday).")
	private Integer dayIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(Integer dayIndex) {
		this.dayIndex = dayIndex;
	}

	@Override
	public String toString() {
		return "DayResource [id=" + id + ", dayIndex=" + dayIndex + "]";
	}

}
