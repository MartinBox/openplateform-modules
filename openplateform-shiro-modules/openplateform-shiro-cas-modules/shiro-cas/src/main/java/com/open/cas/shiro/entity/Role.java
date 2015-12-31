/**
 * @created 2014-2-9 11:48:20
 */
package com.open.cas.shiro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_role_info")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7538916153948080090L;

	/**
	 * ID
	 */
	private Long id;

	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String descr;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(String code, String name, String descr) {
		this.code = code;
		this.name = name;
		this.descr = descr;
	}

	public Role(Long id, String code, String name, String descr) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.descr = descr;
	}

	/**       
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	/**       
	*  编码
	*/
	@Column(name = "ROLE_CODE", nullable = false)
	public String getCode() {
		return this.code;
	}

	/**       
	 * 名称
	 */
	@Column(name = "ROLE_NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	/**       
	 * 描述
	 */
	@Column(name = "DESCR")
	public String getDescr() {
		return this.descr;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}