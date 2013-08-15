package simbio.se.sau.view.list;

import java.util.ArrayList;

import simbio.se.sau.model.IAbstractModel;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:52:06
 * 
 */
public interface IAbstractAdapter {

	public <T extends IAbstractModel> ArrayList<T> getAdapterModels();

}
