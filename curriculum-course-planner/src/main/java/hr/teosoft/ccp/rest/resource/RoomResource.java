package hr.teosoft.ccp.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class RoomResource {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("code")
	private String code;

	@JsonProperty("type")
	private String type;

	@JsonProperty("capacity")
	private Integer capacity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "RoomResource [id=" + id + ", code=" + code + ", type=" + type + ", capacity=" + capacity + "]";
	}

}
