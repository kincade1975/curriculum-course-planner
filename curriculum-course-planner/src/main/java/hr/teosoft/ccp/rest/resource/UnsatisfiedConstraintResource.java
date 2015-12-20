package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="UnsatisfiedConstraint", description="Unsatisfied constraint.", show = true)
public class UnsatisfiedConstraintResource {

	@JsonProperty("constraintName")
	@ApiObjectField(name="constraintName", required=true, description="Name of the unsatisfied contraint.")
	private String constraintName;

	@JsonProperty("weightTotal")
	@ApiObjectField(name="weightTotal", required=true, description="Total weight of the unsatisfied contraint.")
	private Number weightTotal;

	@JsonProperty("details")
	@ApiObjectField(name="details", required=true, description="Details of the unsatisfied contraint.")
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
		return "UnsatisfiedConstraintResource [constraintName=" + constraintName + ", weightTotal=" + weightTotal + ", details=" + details + "]";
	}

}
