/**
 * 
 */
package com.open.cas.shiro;

import java.io.Serializable;

/**
 * @author erick
 *
 */
public interface SecurityUser<PK extends Serializable> {

	public boolean isDisabled();

	public String getLoginName();

	public String getName();

	public Integer getOprStatus();

	public PK getId();

	public String getPassWord();

}
