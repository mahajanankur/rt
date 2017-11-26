/**
 * 
 */
package com.sb.rt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ankur.mahajan
 *
 */
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "epc", unique = true, nullable = false)
	private String epc;

	@Column(name = "sku", nullable = false, length = 100)
	private String sku;

	@Column(name = "sku_name", nullable = false, length = 100)
	private String skuName;

	@Column(name = "item_name", nullable = false, length = 100)
	private String itemName;

	@Column(name = "image", nullable = false, length = 100)
	private String image;

	@Column(name = "size", nullable = false, length = 100)
	private String size;

	@Column(name = "color", nullable = false, length = 100)
	private String color;

	@Column(name = "style_code", nullable = false)
	private int styleCode;

	@Column(name = "zone", nullable = false, length = 100)
	private String zone;

	@Column(name = "description", columnDefinition = "TEXT", nullable = true)
	private String description;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the epc
	 */
	public String getEpc() {
		return epc;
	}

	/**
	 * @param epc the epc to set
	 */
	public void setEpc(String epc) {
		this.epc = epc;
	}

	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * @return the skuName
	 */
	public String getSkuName() {
		return skuName;
	}

	/**
	 * @param skuName the skuName to set
	 */
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the styleCode
	 */
	public int getStyleCode() {
		return styleCode;
	}

	/**
	 * @param styleCode the styleCode to set
	 */
	public void setStyleCode(int styleCode) {
		this.styleCode = styleCode;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + id + ", epc=" + epc + ", sku=" + sku + ", skuName=" + skuName + ", itemName=" + itemName + ", image=" + image + ", size=" + size + ", color=" + color + ", styleCode=" + styleCode + ", zone=" + zone + ", description=" + description + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((epc == null) ? 0 : epc.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (epc == null) {
			if (other.epc != null)
				return false;
		}
		else if (!epc.equals(other.epc))
			return false;
		return true;
	}

}
