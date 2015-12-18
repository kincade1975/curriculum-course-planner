package hr.teosoft.ccp.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.common.swingui.components.Labeled;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Each room has a capacity, expressed in terms of number of available seats.
 * All rooms are equally suitable for all courses (if large enough).
 */
@XStreamAlias("Room")
public class Room extends AbstractPersistable implements Labeled {

	private static final long serialVersionUID = -1873447099582467439L;

	private String code;
	private int capacity;
	private String type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getLabel() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}

}
