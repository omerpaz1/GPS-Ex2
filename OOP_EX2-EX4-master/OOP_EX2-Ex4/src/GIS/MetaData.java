package GIS;

<<<<<<< HEAD
import java.util.Date;

import Geom.Point3D;
=======
>>>>>>> refs/remotes/origin/master

import com.sun.jmx.snmp.Timestamp;

import Geom.Point3D;
public class MetaData implements Meta_data {

	@Override
	public long getUTC() {
		long time = new Date().getTime();
		return time;

	}
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public Point3D get_Orientation() {
		return null;
	}

}
