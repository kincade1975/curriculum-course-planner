package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Timeslot", description="We are given a number of teaching days in the week (typically 5 or 6). Each day is split in a fixed number of time-slots, which is equal for all days. A period is a pair composed of a day and a time-slot. The total number of scheduling periods is the product of the days times the day time-slots.")
public class TimeslotResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the timeslot set and used internally by the planner.")
	private Long id;

	@JsonProperty("timeslotIndex")
	@ApiObjectField(name="timeslotIndex", required=true, description="Identifies timeslot of the day (0 = 07:15, 1 = 08:00, 2 = 08:45, 3 = 09:30, 4 = 10:30, 5 = 11:15, 6 = 12:15, 7 = 13:00, 8 = 14:00, 9 = 14:45, 10 = 15:45, 11 = 16:30, 12 = 17:30, 13 = 18:15, 14 = 19:15).")
	private Integer timeslotIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTimeslotIndex() {
		return timeslotIndex;
	}

	public void setTimeslotIndex(Integer timeslotIndex) {
		this.timeslotIndex = timeslotIndex;
	}

	@Override
	public String toString() {
		return "TimeslotResource [id=" + id + ", timeslotIndex=" + timeslotIndex + "]";
	}

}
