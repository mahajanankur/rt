/**
 * 
 */
package com.sb.rt.dto;

import java.util.List;

/**
 * @author ankur.mahajan
 *
 */
public class DtoRequestProduct {

	private List<String> epcs;

	private List<String> colors;

	private List<String> sizes;

	private String fittingRoomId;

	/**
	 * @return the epcs
	 */
	public List<String> getEpcs() {
		return epcs;
	}

	/**
	 * @param epcs the epcs to set
	 */
	public void setEpcs(List<String> epcs) {
		this.epcs = epcs;
	}

	/**
	 * @return the colors
	 */
	public List<String> getColors() {
		return colors;
	}

	/**
	 * @param colors the colors to set
	 */
	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	/**
	 * @return the sizes
	 */
	public List<String> getSizes() {
		return sizes;
	}

	/**
	 * @param sizes the sizes to set
	 */
	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	/**
	 * @return the fittingRoomId
	 */
	public String getFittingRoomId() {
		return fittingRoomId;
	}

	/**
	 * @param fittingRoomId the fittingRoomId to set
	 */
	public void setFittingRoomId(String fittingRoomId) {
		this.fittingRoomId = fittingRoomId;
	}

}
