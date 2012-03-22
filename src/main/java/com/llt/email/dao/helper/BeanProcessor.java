package com.llt.email.dao.helper;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.llt.email.annotation.Column;

/**
 * <p>
 * <code>BeanProcessor</code> matches column names to bean property names and
 * converts <code>ResultSet</code> columns into objects for those bean
 * properties.
 * </p>
 * 
 * @see BasicRowProcessor
 * 
 */
@Component("beanProcessor")
public class BeanProcessor {

	/**
	 * Set a bean's primitive properties to these defaults when SQL NULL is
	 * returned. These are the same as the defaults that ResultSet get* methods
	 * return in the event of a NULL column.
	 */
	private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();

	static {
		primitiveDefaults.put(Integer.TYPE, 0);
		primitiveDefaults.put(Short.TYPE, ((short) 0));
		primitiveDefaults.put(Byte.TYPE, ((byte) 0));
		primitiveDefaults.put(Float.TYPE, (float) (0));
		primitiveDefaults.put(Double.TYPE, (double) (0));
		primitiveDefaults.put(Long.TYPE, (0L));
		primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
		primitiveDefaults.put(Character.TYPE, '\u0000');
	}

	/**
	 * Factory method that returns a new instance of the given Class. This is
	 * called at the start of the bean creation process and may be overridden to
	 * provide custom behavior like returning a cached bean instance.
	 * 
	 * @param <T>
	 *            The type of object to create
	 * @param c
	 *            The Class to create an object from.
	 * @return A newly created object of the Class.
	 * @throws SQLException
	 *             if creation failed.
	 */
	private <T> T newInstance(Class<T> c) throws SQLException {
		try {
			return c.newInstance();

		} catch (InstantiationException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());
		}
	}

	/**
	 * Convert a <code>ResultSet</code> row into a JavaBean. This implementation
	 * uses reflection and <code>BeanInfo</code> classes to match column names
	 * to bean property names. Properties are matched to columns based on
	 * several factors: <br/>
	 * <ol>
	 * <li>
	 * The class has a writable property with the annotation @Column matching
	 * with the column name in the resultset object.</li>
	 * 
	 * <li>
	 * The column type can be converted to the property's set method parameter
	 * type with a ResultSet.get* method. If the conversion fails (ie. the
	 * property was an int and the column was a Timestamp) an SQLException is
	 * thrown.</li>
	 * </ol>
	 * 
	 * <p>
	 * Primitive bean properties are set to their defaults when SQL NULL is
	 * returned from the <code>ResultSet</code>. Numeric fields are set to 0 and
	 * booleans are set to false. Object bean properties are set to
	 * <code>null</code> when SQL NULL is returned. This is the same behavior as
	 * the <code>ResultSet</code> get* methods.
	 * </p>
	 * 
	 * @param <T>
	 *            The type of bean to create
	 * @param rs
	 *            ResultSet that supplies the bean data
	 * @param type
	 *            Class from which to create the bean instance
	 * @throws SQLException
	 *             if a database access error occurs
	 * @return the newly created bean
	 */
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
		T bean = this.newInstance(type);

		Field[] fields = type.getDeclaredFields();

		for (Field aField : fields) {
			MultiKey cacheKey = new MultiKey(type, aField.getName());
			Column col = columnAnnotationCache.get(cacheKey);
			if (col == null) {
				col = aField.getAnnotation(Column.class);
				if (col == null) {
					col = NOT_A_COLUMN;
				}
				columnAnnotationCache.put(cacheKey, col);
			}
			if (col == NOT_A_COLUMN) {
				continue;
			}
			String colName = col.name();
			Class<?> fieldType = aField.getType();

			Object value = this.processColumn(rs, colName, fieldType);

			if (fieldType != null && value == null && fieldType.isPrimitive()) {
				value = primitiveDefaults.get(fieldType);
			}

			if (null != value) {
				try {
					PropertyDescriptor prop = PropertyUtils
							.getPropertyDescriptor(bean, aField.getName());

					this.callSetter(bean, prop, value);
				} catch (Exception e) {
					throw new SQLException("Cannot set " + aField.getName()
							+ ": " + e.getMessage());
				}
			}
		} // end of for

		return bean;
	}

	/**
	 * Calls the setter method on the target object for the given property. If
	 * no setter method exists for the property, this method does nothing.
	 * 
	 * @param target
	 *            The object to set the property on.
	 * @param prop
	 *            The property to set.
	 * @param value
	 *            The value to pass into the setter.
	 * @throws SQLException
	 *             if an error occurs setting the property.
	 */
	private void callSetter(Object target, PropertyDescriptor prop, Object value)
			throws SQLException {

		Method setter = prop.getWriteMethod();

		if (setter == null) {
			return;
		}

		Class<?>[] params = setter.getParameterTypes();
		try {
			// convert types for some popular ones
			if (value != null) {
				if (value instanceof java.util.Date) {
					if (params[0].getName().equals("java.sql.Date")) {
						value = new java.sql.Date(((java.util.Date) value)
								.getTime());
					} else if (params[0].getName().equals("java.sql.Time")) {
						value = new java.sql.Time(((java.util.Date) value)
								.getTime());
					} 
				} else if (value instanceof java.lang.String) {
					value = StringUtils.trimTrailingWhitespace((String) value);
				}
			}

			// Don't call setter if the value object isn't the right type
			if (this.isCompatibleType(value, params[0])) {
				setter.invoke(target, new Object[] { value });
			} else {
				throw new SQLException("Cannot set " + prop.getName()
						+ ": incompatible types.");
			}
		} catch (IllegalArgumentException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": "
					+ e.getMessage());

		} catch (InvocationTargetException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": "
					+ e.getMessage());
		}
	}

	/**
	 * Convert a <code>ResultSet</code> column into an object. Simple
	 * implementations could just call <code>rs.getObject(index)</code> while
	 * more complex implementations could perform type manipulation to match the
	 * column's type to the bean property type.
	 * 
	 * <p>
	 * This implementation calls the appropriate <code>ResultSet</code> getter
	 * method for the given property type to perform the type conversion. If the
	 * property type doesn't match one of the supported <code>ResultSet</code>
	 * types, <code>getObject</code> is called.
	 * </p>
	 * 
	 * @param rs
	 *            The <code>ResultSet</code> currently being processed. It is
	 *            positioned on a valid row before being passed into this
	 *            method.
	 * 
	 * @param colName
	 *            The current column name.
	 * 
	 * @param propType
	 *            The bean property type that this column needs to be converted
	 *            into.
	 * 
	 * @throws SQLException
	 *             if a database access error occurs
	 * 
	 * @return The object from the <code>ResultSet</code> for the given column
	 *         name after optional type processing or <code>null</code> if the
	 *         column value was SQL NULL.
	 */
	private Object processColumn(ResultSet rs, String colName, Class<?> propType)
			throws SQLException {

		if (!propType.isPrimitive() && rs.getObject(colName) == null) {
			return null;
		}

		if (!propType.isPrimitive() && rs.getObject(colName) == null) {
			return null;
		}

		if (propType.equals(String.class)) {
			return rs.getString(colName);

		} else if (propType.equals(BigInteger.class)) {
			Long colValue = (rs.getLong(colName));

			return BigInteger.valueOf(colValue.longValue());
		} else if (propType.equals(Integer.TYPE)
				|| propType.equals(Integer.class)) {
			return (rs.getInt(colName));

		} else if (propType.equals(Boolean.TYPE)
				|| propType.equals(Boolean.class)) {
			return (rs.getBoolean(colName));

		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return (rs.getLong(colName));

		} else if (propType.equals(Double.TYPE)
				|| propType.equals(Double.class)) {
			return (rs.getDouble(colName));

		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return (rs.getFloat(colName));

		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return (rs.getShort(colName));

		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return (rs.getByte(colName));

		} else if (propType.equals(Timestamp.class)) {
			return rs.getTimestamp(colName);

		} else {
			return rs.getObject(colName);
		}
	}

	/**
	 * ResultSet.getObject() returns an Integer object for an INT column. The
	 * setter method for the property might take an Integer or a primitive int.
	 * This method returns true if the value can be successfully passed into the
	 * setter method. Remember, Method.invoke() handles the unwrapping of
	 * Integer into an int.
	 * 
	 * @param value
	 *            The value to be passed into the setter method.
	 * @param type
	 *            The setter's parameter type.
	 * @return boolean True if the value is compatible.
	 */
	private boolean isCompatibleType(Object value, Class<?> type) {
		// Do object check first, then primitives
		if (value == null || type.isInstance(value)) {
			return true;

		} else if (type.equals(Integer.TYPE) && Integer.class.isInstance(value)) {
			return true;

		} else if (type.equals(Long.TYPE) && Long.class.isInstance(value)) {
			return true;

		} else if (type.equals(Double.TYPE) && Double.class.isInstance(value)) {
			return true;

		} else if (type.equals(Float.TYPE) && Float.class.isInstance(value)) {
			return true;

		} else if (type.equals(Short.TYPE) && Short.class.isInstance(value)) {
			return true;

		} else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value)) {
			return true;

		} else if (type.equals(Character.TYPE)
				&& Character.class.isInstance(value)) {
			return true;

		} else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value)) {
			return true;

		} else {
			return false;
		}

	}

	private static final Column NOT_A_COLUMN = new Column() {
		public String name() {
			return "NOT_A_COLUMN";
		}

		public Class<? extends Annotation> annotationType() {
			return Column.class;
		}

		public String toString() {
			return "NOT_A_COLUMN";
		}
	};
	private static final Map<MultiKey, Column> columnAnnotationCache = new ConcurrentHashMap<MultiKey, Column>();
}
