package simbio.se.sau.model.location;

import simbio.se.sau.exceptions.location.AbstractLocationException;

/**
 * Created by Ademar Oliveira <ademar111190@gmail.com> on 4/30/14.
 */
public interface GeoLocationModel {

    public void couldNotGetGeoLocation(AbstractLocationException because);

}
