package simbio.se.sau.adapters;

import java.util.ArrayList;

import simbio.se.sau.API;
import simbio.se.sau.model.IAbstractModel;

/**
 * A interface to use the {@link AbstractAdapter} class.
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:52:06
 * @since {@link API#Version_1_0_0}
 */
public interface IAbstractAdapter<T extends IAbstractModel> {

	/**
	 * this method need return the models used by {@link AbstractAdapter} class.
	 * 
	 * @return the models needed to {@link AbstractAdapter}.
	 * @since {@link API#Version_1_0_0}
	 */
	public ArrayList<T> getAdapterModels();

}
