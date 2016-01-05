package com.open.cas.shiro.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class DialectForInkfish extends MySQLDialect {

	public DialectForInkfish() {
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}
}
