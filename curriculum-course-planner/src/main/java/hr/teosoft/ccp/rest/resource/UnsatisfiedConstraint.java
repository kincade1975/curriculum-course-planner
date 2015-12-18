package hr.teosoft.ccp.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class UnsatisfiedConstraint {

	@JsonProperty("constraintName")
	private String constraintName;

	@JsonProperty("weightTotal")
	private Number weightTotal;

	@JsonProperty("details")
	private String details;

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public Number getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(Number weightTotal) {
		this.weightTotal = weightTotal;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "UnsatisfiedConstraint [constraintName=" + constraintName + ", weightTotal=" + weightTotal + ", details=" + details + "]";
	}

}
