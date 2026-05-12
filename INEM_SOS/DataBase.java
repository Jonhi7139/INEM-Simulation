package INEM_SOS;
import java.io.Serializable;

public class DataBase implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String pass;
	private String wk;
	private String name2;
	private String name3;
	private double lon;
	private double lat;
	private int u;
	private boolean estat;
	private String t;
	private String name4;
	private double lon2;
	private double lat2;
	private boolean stat;
	private String name5;
	private double lon3;
	private double lat3;
	private int bed;
	private String name6;
	private String name7;
	private String hosp;
	private String amb;
	
	
public DataBase(String name, String email, String pass) {
	
		this.name = name;
		this.email = email;
		this.pass = pass;
	}
	
public DataBase(String wk, String name2, double lon, double lat, String name3, int u, boolean estat, String t, String hosp, String amb) {
		
		this.wk = wk;
		this.name2 = name2;
		this.lon = lon;
		this.lat = lat;
		this.name3 = name3;
		this.u = u;
		this.estat = estat;
		this.t=t;
		this.hosp = hosp;
		this.amb = amb;
	}

public DataBase(String name4, double lon2, double lat2, boolean stat) {
	
	this.name4 = name4;
	this.lon2 = lon2;
	this.lat2 = lat2;
	this.stat = stat;
}

public DataBase(String name5, double lon3, double lat3, int bed) {
	
	this.name5 = name5;
	this.lon3 = lon3;
	this.lat3 = lat3;
	this.bed = bed;
}

public DataBase(String name6, String name7) {
	
	this.name6 = name6;
	this.name7 = name7;
}

	public String toString() {
		
		return "Name: "+name+", Email: "+email+" Pass: "+pass;
	}
	
	public String getName() {
		
		return name;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public String getPass() {
		
		return pass;
	}
	
	public String getWk() {
		
		return wk;
	}
	
	public String getName2() {
		
		return name2;
	}
	
	public double getLon() {
		
		return lon;
	}
	
	public double getLat() {
		
		return lat;
	}
	
	public String getName3() {
		
		return name3;
	}
	
	public int getU() {
		
		return u;
	}
	
	public boolean getEstat() {
		
		return estat;
	}
	
	public void setEstat() {
		
		this.estat = !estat;
	}
	public String toString2() {
		
		return "Emergency: "+name2+"\n" 
				+ "Location: "+lon+","+lat+"\n"
				+ "Patient: "+name3+"\n"
				+ "User health number: "+u;
	}
	public String getT()
	{
		return(t);
	}
	
	public void setHosp(String hosp) {
		
		this.hosp = hosp;
	}
	
	public void setAmb(String amb) {
		
		this.amb = amb;
	}
	
	public String getHosp() {
		
		return hosp;
	}
	
	public String getAmb() {
		
		return amb;
	}
	
	public String getName4() {
		
		return name4;
	}
	
	public double getLon2() {
		
		return lon2;
	}
	
	public double getLat2() {
		
		return lat2;
	}
	
	public boolean getStat() {
		return stat;
	}
	
	public void setStat() {
		
		this.stat = !stat;
	}
	
	public String toString3() {
		
		return "Emergency: "+name4+"\n"
				+ "Location: "+lon2+","+lat2+"\n"
				+ "Patient: "+stat+"\n";
	}
	
	public String getName5() {
		
		return name5;
	}
	
	public double getLon3() {
		
		return lon3;
	}
	
	public double getLat3() {
		
		return lat3;
	}
	
	public int getBed() {
		return bed;
	}
	
	public void moreBed() {
		this.bed = bed+1;
	}
	
	public void lessBed() {
		this.bed = bed-1;
	}
	
	public String getName6() {
		
		return name6;
	}
	
	public String getName7() {
		
		return name7;
	}
	
	public static class Worker extends DataBase implements Serializable {

		private static final long serialVersionUID = 1L;

		public Worker(String name, String email, String pass) {
			super(name, email, pass); // Chama o construtor da classe base
		}

		@Override
		public String getName() {
			return super.getName();
		}

		@Override
		public String getEmail() {
			return super.getEmail();
		}

		@Override
		public String getPass() {
			return super.getPass();
		}
	}
	
	public static class Em extends DataBase implements Serializable {

		private static final long serialVersionUID = 1L;

		public Em(String wk, String name2, double lon, double lat, String name3, int u, boolean estat,String t, String hosp, String amb) {
			super(wk, name2, lon, lat, name3, u, estat,t,hosp,amb);
		}

		@Override
		public String getWk() {
			return super.getWk();
		}

		@Override
		public String getName2() {
			return super.getName2();
		}

		@Override
		public double getLon() {
			return super.getLon();
		}

		@Override
		public double getLat() {
			return super.getLat();
		}
		
		@Override
		public String getName3() {
			return super.getName3();
		}
		
		@Override
		public int getU() {
			return super.getU();
		}
		
		@Override
		public boolean getEstat() {
			return super.getEstat();
		}
		
		@Override
		public void setEstat() {
			super.setEstat();
		}
		
		@Override
		public String toString2() {
			return super.toString2();
		}

		
		@Override
		public String getT() {
			return super.getT();
		}
		@Override
		public String getHosp() {
			return super.getHosp();
		}
		@Override
		public String getAmb() {
			return super.getAmb();
		}
		@Override
		public void setHosp(String hosp) {
			super.setHosp(hosp);
		}
		@Override
		public void setAmb(String amb) {
			super.setAmb(amb);
		}
	}
	
	public static class Amb extends DataBase implements Serializable {

		private static final long serialVersionUID = 1L;

		public Amb(String name4, double lon2, double lat2,  boolean stat) {
			super(name4, lon2, lat2, stat);
		}

		@Override
		public String getName4() {
			return super.getName4();
		}

		@Override
		public double getLon2() {
			return super.getLon2();
		}

		@Override
		public double getLat2() {
			return super.getLat2();
		}
		
		@Override
		public boolean getStat() {
			return super.getStat();
		}
		
		@Override
		public String toString3() {
			return super.toString3();
		}
		
		@Override
		public void setStat() {
			super.setStat();
		}
	}
	
	public static class Hosp extends DataBase implements Serializable {

		private static final long serialVersionUID = 1L;

		public Hosp(String name5, double lon3, double lat3,  int bed) {
			super(name5, lon3, lat3, bed);
		}

		@Override
		public String getName5() {
			return super.getName5();
		}

		@Override
		public double getLon3() {
			return super.getLon3();
		}

		@Override
		public double getLat3() {
			return super.getLat3();
		}
		
		@Override
		public int getBed() {
			return super.getBed();
		}
		
		@Override
		public void moreBed() {
			super.moreBed();
		}
		
		@Override
		public void lessBed() {
			super.lessBed();
		}
	}
	
	public static class HAem extends DataBase implements Serializable {

		private static final long serialVersionUID = 1L;

		public HAem(String name6, String name7) {
			super(name6, name7);
		}

		@Override
		public String getName6() {
			return super.getName6();
		}

		@Override
		public String getName7() {
			return super.getName7();
		}
	}

		
}
