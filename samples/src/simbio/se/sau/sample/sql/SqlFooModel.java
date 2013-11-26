/**
 * 
 */
package simbio.se.sau.sample.sql;

import simbio.se.sau.model.IAbstractModel;

/**
 * The {@link IAbstractModel} implementation is necessary to be used on listview but not sql
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 5:22:52 PM
 */
public class SqlFooModel implements IAbstractModel {

	private long time;
	private String text;

	public SqlFooModel(String text) {
		this.text = text;
	}

	public SqlFooModel() {
	}

	public String getText() {
		return text;
	}

	public long getTime() {
		return time;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
