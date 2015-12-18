package hr.teosoft.ccp.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DayResource {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("dayIndex")
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
