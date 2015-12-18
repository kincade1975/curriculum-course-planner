package hr.teosoft.ccp.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A curriculum is a group of courses such that any pair of courses in the group
 * have students in common. Based on curriculum, we have the conflicts between courses
 * and other soft constraints
 */
@XStreamAlias("Curriculum")
public class Curriculum extends AbstractPersistable {

	private static final long serialVersionUID = 114990873716185750L;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}

}
