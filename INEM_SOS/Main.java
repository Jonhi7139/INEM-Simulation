package INEM_SOS;
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import INEM_SOS.DataBase.Em;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Main {
	
	public static void main(String [] args) {
		
		try {
            // Criação inicial do ADMIN apenas se o arquivo não existir
			File file = new File("workers.dat");
			Map<String, DataBase> wn;
			Map<String, DataBase> hn;

			if (!file.exists()) {
				
			    wn = new HashMap<>();
			    wn.put("ADMIN", new DataBase.Worker("ADMIN", "admin@inem.pt", "#X1234"));
			    saveWorkers(wn);
			    
			} 
			else {
				
			    wn = loadWorkers();
			    
			    if (!wn.containsKey("ADMIN")) {
			        wn.put("ADMIN", new DataBase.Worker("ADMIN", "admin@inem.pt", "#X1234"));
			        saveWorkers(wn);
			    }
			}
            
            File file2 = new File("hosp.dat");
            
            List<String> Hospitals = new ArrayList<>();
            
            Hospitals.add("1 Centro Hospitalar e Universitário de Coimbra - Polo Hospital Geral Covões,8.413,40.218");
            Hospitals.add("2 Centro Hospitalar e Universitário de Coimbra - Polo Hospitais da Universidade de Coimbra,-8.2468,40.2111");
            Hospitals.add("3 Hospital da Luz Coimbra,-8.4106,40.2036");
            Hospitals.add("4 Hospital CUF Coimbra,-8.4156,40.2229");
            
            if (!file2.exists()) {
            	
            	hn = new HashMap<>();
            	int s = 1;
                
                for (String i : Hospitals) {
                	
                	String[] hps = i.split(",");
					String hname = hps[0];
					double hlon = Double.parseDouble(hps[1].trim());
					double hlat = Double.parseDouble(hps[2].trim());
					
					hn.put(Integer.toString(s), new DataBase.Hosp(hname, hlon, hlat, 2));
	                s = s + 1;
	                saveHosp(hn);
                }
            }
            else {
            	
            	hn = loadHosp();
            }
            
        } catch (Exception exx) {
            exx.printStackTrace();
        }
        
		Scanner sc = new Scanner(System.in);
		
		int op;
		String name = null;
		String email;
		String pass;
		String name2 = null;
		String loc;
		String name3 = null;
		String u=null;
		String name4 = null;
		double lat2;
		double lon2;
		boolean stat = true;
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM HH:mm");
		String t = now.format(formatter);
		
		boolean on = true;
		boolean adm = true;
		boolean work = true;
		
		while (on) {

			menu1();
			
			if (sc.hasNextInt()) {
				
				op = sc.nextInt();
				sc.nextLine();
				
				if (op == 0) {
					
					System.out.println("Name: ");
					name = sc.nextLine();
					
					System.out.println("Password: ");
					pass = sc.nextLine();
					
					try {
						
						Map<String, DataBase> wn = loadWorkers();
						
			            DataBase db = wn.get(name);

			            if (db instanceof DataBase.Worker w) {
			                
			                if (w.getName().equals("ADMIN") && pass.equals(w.getPass())) {
			                	
			                	adm = false;
			                }
			                else if (pass.equals(w.getPass())){
			                	
			                	work = false;
			                }
			            } 
			            else {
			            	
			                System.out.println("User not found.");
			            }
			        } catch (Exception e) {
			        	
			            e.printStackTrace();
			        }
				}
				else if (op == 1) {
					
					on = false;
				}
				else {
					
					System.out.println("Insert a valid option!");
				}
			}
			else {
				
				System.out.println("Insert a valid option!");
				sc.next();
			}
			
			while (!adm) {
				
				menu2();
				
				try {
					
					Map<String, DataBase> wn = loadWorkers();
					Map<String, DataBase> an = loadAmb();
					Map<String, DataBase> ogn = loadOgem();
					Map<String, DataBase> pdn = loadPem();
					Map<String, DataBase> cn = loadCem();
					
					if (sc.hasNextInt()) {
						
						op = sc.nextInt();
						sc.nextLine();
						
						if (op == 1) {
								
							boolean crl = true;
							
							while (crl) {
								
								System.out.println("Name (or 0 to exit): ");
								name = sc.nextLine();
								
								if (name.equals("0")) {
									
									break;
								}
								
								System.out.println("Email (Use your INEM email): ");
								email = sc.nextLine();
								
								if (!email.contains("@inem.pt")) {

		                        	System.out.println("Invalid email! (It has to contain '@inem.pt')");
									continue;
								}
								
								System.out.println("Define your password: ");
								pass = sc.nextLine();

		                            
		                        if (wn.containsKey(name)) {
		                            	
		                        	System.out.println("User already created!");
		                        	continue;
		                        }
		                        else {

		                        	wn.put(name, new DataBase.Worker(name, email, pass));
		                            saveWorkers(wn);
	                                System.out.println("User added successfully!");
	                                crl = false;
	                            }								
							}
						}
						else if (op == 2) {
							
							System.out.println("User to be removed (or 0 to exit): ");						
							String namer = sc.nextLine();
							
							
							if (wn.containsKey(namer)) {
								if (namer.equals("ADMIN")) {
									
									System.out.println("Invalid option!");
								}
								else {
									
									wn.remove(namer);
									saveWorkers(wn);
									System.out.println("User removed!");
								}
							}
							else {
								
								if (!namer.equals("0")) {

									System.out.println("User not found.");	
								}
							}
						}
						else if (op == 3) {
							
							for (Map.Entry<String, DataBase> aem : ogn.entrySet()) {
								
								DataBase dogn = aem.getValue();
								System.out.println("Worker: "+ dogn.getWk() +"Emergency: " + dogn.getName2() +"\n"+"Location: " + dogn.getLon() + ", " + dogn.getLat() +"\n"+ "Patient: " + dogn.getName3() +"\n"+ "Health number: " + dogn.getU() +"\n"+ "Status: On going!"+"\n"+"Hospital:"+dogn.getHosp()+"\n"+"Ambulance"+dogn.getAmb()+"\n"+"\n");
				            }
							for (Map.Entry<String, DataBase> aem : pdn.entrySet()) {
								
								DataBase dpdn = aem.getValue();
								System.out.println("Worker: "+ dpdn.getWk() +"Emergency: " + dpdn.getName2() +"\n"+ "Location: " + dpdn.getLon() + ", " + dpdn.getLat() +"\n"+ "Patient: " + dpdn.getName3() +"\n"+ "Health number: " + dpdn.getU() +"\n"+ "Status: Pending!"+"\n"+"Hospital:"+ dpdn.getHosp() +"\n"+"Ambulance"+ dpdn.getAmb()+"\n"+"\n");
				            }
							for (Map.Entry<String, DataBase> aem : cn.entrySet()) {
								
								DataBase dcn = aem.getValue();
								System.out.println("Worker: "+ dcn.getWk() +", Emergency: " + dcn.getName2() + ", Location: " + dcn.getLon() + ", " + dcn.getLat() + ", Patient: " + dcn.getName3() + ", Health number: " + dcn.getU() + ", Status: Concluded!"+"\n"+"Hospital:"+ dcn.getHosp()+"\n"+"Ambulance"+dcn.getAmb()+"\n"+"\n");
				            }
						}
						else if (op == 4) {
							
							boolean aab = true;
							
							while (aab) {

								System.out.println("Ambulance plate (or 0 to exit): ");
								name4 = sc.nextLine();
								
								String [] n = name4.split("-");
								
								if (n.length == 3) {
									
									//Limmites da zona urbana de Coimbra
									double minLat2 = 40.17;
							        double maxLat2 = 40.23;
							        double minLon2 = -8.47;
							        double maxLon2 = -8.39;

									Random ra = new Random();
									lon2 = minLon2 + (maxLon2 - minLon2)*ra.nextDouble();
									lat2 = minLat2 + (maxLat2 - minLat2)*ra.nextDouble();
			                            
			                        if (an.containsKey(name4)) {
			                            	
			                           	System.out.println("Ambulance already registered!");
			                           	continue;
			                        }
			                        else {

			                            an.put(name4, new DataBase.Amb(name4, lon2, lat2, stat));
			                            saveAmb(an);

			                            System.out.println("Ambulance "+ name4 +" added successfully!");
			                            aab = false;
			                        }
								}
								
								else
								{
									if (!name4.equals("0")) {

										System.out.println("Invalid plate (follow the model: XX-XX-XX)!");
										continue;
									}
									else {
										
										aab = false;
									}
								}	
							}							
						}
						else if (op == 5) {
							
							System.out.println("Ambulance to be removed (or 0 to exite): ");						
							String namera = sc.nextLine();
							
							
							if (an.containsKey(namera)) {
									
								an.remove(namera);
								saveAmb(an);
								System.out.println("Ambulance removed!");
							}
							else {
								
								if (!namera.equals("0")) {
									
									System.out.println("Ambulance not found.");
								}
							}
						}
						else if (op == 6) {
							
							listOfAmbs();
						}
						else if (op == 7) {
							
							listOfHosps();
						}
						else if (op == 8) {
							
							adm = true;
							on = true;						
						}					
					}
					else {
						
						System.out.println("Insert a valid option!");
					}
				}
				catch (Exception exx) {
                	
                    exx.printStackTrace();
                }
			}
			
			while (!work) {
				
				int wd = 49;
				int padd = (wd - 2 - (name.length() + 7)) / 2;
				
				String uBorder = "╔" + "═".repeat(wd - 2) + "╗";
				String t3 = "║" + " ".repeat(Math.max(0, padd))+ "INEM - " + name + " ".repeat(Math.max(0, wd - 2 - padd - (name.length() + 7))) + "║";
				
				System.out.println(uBorder);
				System.out.println(t3);
				
				menu3();
				
				try {
					
					Map<String, DataBase> an = loadAmb();
					Map<String, DataBase> hn = loadHosp();
					Map<String, DataBase> ogn = loadOgem();
					Map<String, DataBase> pdn = loadPem();
					Map<String, DataBase> wn = loadWorkers();	
					Map<String, DataBase> cn = loadCem();	
					Map<String, DataBase> han = loadHAem();		
					
					
					if (sc.hasNextInt()) {
						
						op = sc.nextInt();
						sc.nextLine();
						
						if (op == 1) {
							boolean crem=true;
							boolean estat = true;
							while(crem)
							{
								System.out.println("Emergency description (or 0 to exit): ");
								name2 = sc.nextLine();
								
								if (name2.equals("0")) {
									
									break;
								}
								
								String[] pt = name2.trim().split("\\s+");
								if (pt.length > 0) {
							            String first = pt[0];

							            try {
							                int value = Integer.parseInt(first);
							                System.out.println("Invalid Emergency!");
							                continue;
							            } catch (NumberFormatException e) {
							            }
								}
							            
									System.out.println("Location: lon,lat");
							        loc = sc.nextLine();
									String[] l = loc.split(",");
									double lon = 0;
							        double lat = 0;
									
									if (l.length == 2) {
										
										try {
									        
											lon = Double.parseDouble(l[0].trim());
											lat = Double.parseDouble(l[1].trim());							        	
								        } catch (NumberFormatException e) {
								        	
								        	System.out.println("Invalid Emergency!");
								        	continue;
							            }										
									}
									else
									{
										System.out.println("Invalid Emergency!");
										continue;
									}
									
									System.out.println("Patient name: ");
									name3 = sc.nextLine();
									

									String[] pt2 = name3.trim().split("\\s+");
									if (pt2.length > 0) {
								            String first = pt2[0];

								            try {
								                int value = Integer.parseInt(first);
								                System.out.println("Invalid name!");
								                continue;
								            } catch (NumberFormatException e) {
								            }
									}
									
									System.out.println("User health number: ");
									u = sc.nextLine();
									String[] pt3 = u.trim().split("\\s+");
									if (pt3.length > 0) {
								            String first = pt3[0];

								            try {
								                int value = Integer.parseInt(first);

								            } catch (NumberFormatException e) {
								                System.out.println("Invalid number!");
								                continue;
								            }
									}

									if (u.length() != 6) {
										
										System.out.println("Invalid number!");
										continue;
									}
									
									if (cn.containsKey(name3)||ogn.containsKey(name3)||pdn.containsKey(name3)) {
		                            	
			                           	System.out.println("Emergency already created!");
			                        }
			                        else {
			                                
			                            DataBase.Amb na = null;
			        						
			        					double anearst = Double.MAX_VALUE;
			        						
			        					for (Map.Entry<String, DataBase> avl : an.entrySet()) {
			        							
			        						DataBase adb = avl.getValue();
			        							
			        						if (adb instanceof DataBase.Amb a) {
			        							if(a.getStat()) {
			        									
			        								double adx = distance(lat, lon, a.getLat2(), a.getLon2());
			        									
			        								if (adx < anearst) {
			        										
			        									anearst = adx;
			        									na = a;
			        								}
			        							}
			        						}
			        					}
			        						
			        					DataBase.Hosp nh = null;
			        						
			        					double hnearst = Double.MAX_VALUE;
			        						
			        					for (Map.Entry<String, DataBase> hvl : hn.entrySet()) {
			        							
			        						DataBase hdb = hvl.getValue();
			        							
			        						if (hdb instanceof DataBase.Hosp h) {
			        							if(h.getBed() > 0) {
			        								
			        								double hdx = distance(lat, lon, h.getLat3(), h.getLon3());
			        								
			        								if (hdx < hnearst) {
			        									
			        									hnearst = hdx;
			        									nh = h;
			        								}
			        							}
			        						}
			        					}
			        					
			        					DataBase e = new DataBase.Em(name, name2, lon, lat, name3, Integer.parseInt(u), estat,t,null,null);
			        						
			        					if (nh == null && na != null) {
			        						
			        						System.out.println("No hospital beds availeble!");

			        						e.setEstat();
			                                pdn.put(u, e);
			                                savePem(pdn);
			        							
			        					}
			        					else if (nh != null && na == null) {
			        						
			        						System.out.println("No ambulances availeble!");

			        						e.setEstat();
			                                pdn.put(u, e);
			                                savePem(pdn);
			        					}
			        					else if (na != null && nh != null) {

				        					DataBase hae = new DataBase.HAem(nh.getName5(), na.getName4());
			        						e.setHosp(nh.getName5());
			        						e.setAmb(na.getName4());
				        					
			        						nh.lessBed();
			        						hn.remove(nh.getName5());
			        						hn.put(nh.getName5(), nh);
			        						saveHosp(hn);
			        						na.setStat();
			        						an.remove(na.getName4());
			        						an.put(na.getName4(),na);
			        						saveAmb(an);	        							
			        						ogn.put(u, e);
			        						saveOgem(ogn);
			        						han.put(u, hae);
			        						saveHAem(han);
			        					}
			        					else {
			        						System.out.println("No hospital beds nor ambulances availavle!");

			        						e.setEstat();
			                                pdn.put(name, e);
			                                savePem(pdn);
			        					}
			                        }
	                        crem=false;

							}
							
	                    }
						else if (op == 2) {
							
							boolean reso = true;
							
							listOfOgem(name);
							
								while(reso) {
									System.out.println("Choose an emergency (use the health number or 0 to EXIT): ");
									String rogn = sc.nextLine();
									
									String[] pt4 = rogn.trim().split("\\s+");
									if (pt4.length > 0) {
								            String first = pt4[0];

								            try {
								                int value = Integer.parseInt(first);

								            } catch (NumberFormatException e) {
								                System.out.println("Invalid Emergency!");
								                continue;
								            }
									}
									
									DataBase o = null;
									DataBase ho = null;
									DataBase ha = null;
									
									if (ogn.containsKey(rogn)) {
									
										o = ogn.get(rogn);
										ho = han.get(rogn);
										ogn.remove(rogn);
										saveOgem(ogn);
										cn.put(rogn, o);
										saveCem(cn);
										
										ha = an.get(ho.getName7());
										ha.setStat();
										an.remove(ho.getName7());
										an.put(ho.getName7(), ha);
										saveAmb(an);
										
										reso = false;
									}
									else {
										
										if (rogn.equals("0")) {
											reso = false;
										}
										else {
											
											System.out.println("Emergency not found!");
											continue;	
										}
									}
									
								}

							}
						else if (op == 3) {
							
							int av=0;
							int hv=0;
							boolean resp = true;
							
							while (resp) {
								
								for (Map.Entry<String, DataBase> entry : an.entrySet()) 
								{
									DataBase avl=entry.getValue();
									if(avl.getStat())
									{
										av=av+1;
									}
									
								}
								for (Map.Entry<String, DataBase> entry : hn.entrySet()) 
								{
									DataBase hvl= entry.getValue();
									if(!(hvl.getBed()==0))
									{
										hv=hv+1;
									}
								}
								if (!(av==0)&&!(hv==0))
								{
									
									listOfPem(name);
									System.out.println("Choose which emergency would you like to resolve (use the health numberor 0 to EXIT): ");
									String pen = sc.nextLine();
									
									String[] pt5 = pen.trim().split("\\s+");
									if (pt5.length > 0) {
								            String first = pt5[0];

								            try {
								                int value = Integer.parseInt(first);

								            } catch (NumberFormatException e) {
								                System.out.println("Invalid Emergency!");
								                continue;
								            }
									}
									
									
									if(pdn.containsKey(pen))
									{
										
		                                DataBase e = pdn.get(pen);
			                            DataBase.Amb na = null;
			        						
			        					double anearst = Double.MAX_VALUE;
			        						
			        					for (Map.Entry<String, DataBase> avl : an.entrySet()) {
			        							
			        						DataBase adb = avl.getValue();
			        							
			        						if (adb instanceof DataBase.Amb a) {
			        							if(a.getStat()) {
			        									
			        								double adx = distance(e.getLat(), e.getLon(), a.getLat2(), a.getLon2());
			        									
			        								if (adx < anearst) {
			        										
			        									anearst = adx;
			        									na = a;
			        								}
			        							}
			        						}
			        					}
			        						
			        					DataBase.Hosp nh = null;
			        						
			        					double hnearst = Double.MAX_VALUE;
			        						
			        					for (Map.Entry<String, DataBase> hvl : hn.entrySet()) {
			        							
			        						DataBase hdb = hvl.getValue();
			        							
			        						if (hdb instanceof DataBase.Hosp h) {
			        							if(h.getBed() > 0) {
			        								
			        								double hdx = distance(e.getLat(), e.getLon(), h.getLat3(), h.getLon3());
			        								
			        								if (hdx < hnearst) {
			        									
			        									hnearst = hdx;
			        									nh = h;
			        								}
			        							}
			        						}
			        					}
			        						
			        					DataBase hae = new DataBase.HAem(nh.getName5(), na.getName4());
			        					e.setHosp(nh.getName5());
			        					e.setAmb(na.getName4());
			        					e.setEstat();
			        					nh.lessBed();
			        					hn.remove(nh.getName5());
			        					hn.put(nh.getName5(), nh);
			        					saveHosp(hn);
			        					na.setStat();
			        					an.remove(na.getName4());
			        					an.put(na.getName4(),na);
			        					saveAmb(an);	        							
			        					ogn.put(pen, e);
			        					saveOgem(ogn);
			        					han.put(pen, hae);
			        					saveHAem(han);
										pdn.remove(pen);
										savePem(pdn);									
										
									}
									else
									{
										if (pen.equals("0")) {
											
											resp = false;
										}
										else
										{

											System.out.println("Emergency not found!");
											continue;
										}
									}
									resp=false;
									
								}
								else if(av==0)
								{
									System.out.println("There is no free ambulance!");
									resp = false;
								}
								else if(hv==0)
								{
									System.out.println("There are no free beds!");
									resp = false;
								}
							}
						}
						else if (op == 4) {
							if(ogn.isEmpty())
							{
								listOfCem(name);
							}
							else if(cn.isEmpty())
							{
								listOfOgem(name);
							}
							else
							{
								listOfCem(name);
								listOfOgem(name);
							}
						}
						else if (op == 5) {
							if(!pdn.isEmpty())
							{
								listOfPem(name);
							}
						}
						else if (op == 6) {
							
							boolean sb = true;
							listOfHosps();
							
							while(sb) {
								
								System.out.println("Chose a hospital: ");						
								String bh = sc.nextLine();
								
								String[] pt6 = bh.trim().split("\\s+");
								if (pt6.length == 0) 

							            try {
							                int value = Integer.parseInt(pt6[0]);

							            } catch (NumberFormatException e) {
							                System.out.println("Invalid option!");
							                continue;
							            }
								
								if (hn.containsKey(bh)) {

									DataBase hb = hn.get(bh);
									hb.moreBed();
									hn.remove(bh);
									hn.put(bh,hb);
									saveHosp(hn);
									sb = false;
								}
								else {
									
									if (bh.equals("0")) {
										sb = false;
									}
									else
									{

										System.out.println("Hospital not found!");
										continue;
									}
								}	
							}							
						}
						else if (op == 7) {
							
							work = true;
							on = true;						
						}
					else {
						
						System.out.println("Insert a valid option!");
					
					}
					
				}
			} 
				catch (Exception exx) {
                	
                    exx.printStackTrace();
                }
			}			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Map<String, DataBase> loadWorkers() { //Método para ler o arquivo de usuários
        try {
            FileInputStream fileIn = new FileInputStream("workers.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Map<String, DataBase> wn = (Map<String, DataBase>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return wn;
        } catch (Exception exx) {
            return new HashMap<>(); // Se o arquivo ainda não existe
        }
    }

    public static void saveWorkers(Map<String, DataBase> wn) { //Método para salvar no arquivo
        try {
            FileOutputStream fileOut = new FileOutputStream("workers.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(wn);
            objectOut.close();
            fileOut.close();
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
	public static Map<String, DataBase> loadAmb() { //Método para ler o arquivo de usuários
        try {
            FileInputStream fileIn = new FileInputStream("amb.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Map<String, DataBase> an = (Map<String, DataBase>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return an;
        } catch (Exception exx) {
            return new HashMap<>(); // Se o arquivo ainda não existe
        }
    }

    public static void saveAmb(Map<String, DataBase> an) { //Método para salvar no arquivo
        try {
            FileOutputStream fileOut = new FileOutputStream("amb.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(an);
            objectOut.close();
            fileOut.close();
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
	public static Map<String, DataBase> loadHosp() { //Método para ler o arquivo de usuários
        try {
            FileInputStream fileIn = new FileInputStream("hosp.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Map<String, DataBase> hn = (Map<String, DataBase>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return hn;
        } catch (Exception exx) {
            return new HashMap<>(); // Se o arquivo ainda não existe
        }
    }

    public static void saveHosp(Map<String, DataBase> hn) { //Método para salvar no arquivo
        try {
            FileOutputStream fileOut = new FileOutputStream("hosp.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(hn);
            objectOut.close();
            fileOut.close();
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
	public static Map<String, DataBase> loadOgem() { //Método para ler o arquivo de usuários
        try {
            FileInputStream fileIn = new FileInputStream("ongoin.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Map<String, DataBase> ogn = (Map<String, DataBase>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return ogn;
        } catch (Exception exx) {
            return new HashMap<>(); // Se o arquivo ainda não existe
        }
    }

    public static void saveOgem(Map<String, DataBase> ogn) {
        try {
            FileOutputStream fileOut = new FileOutputStream("ongoin.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(ogn);
            objectOut.close();
            fileOut.close();
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
	public static Map<String, DataBase> loadPem() { //Método para ler o arquivo de usuários
        try {
            FileInputStream fileIn = new FileInputStream("pending.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Map<String, DataBase> pdn = (Map<String, DataBase>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return pdn;
        } catch (Exception exx) {
            return new HashMap<>(); // Se o arquivo ainda não existe
        }
    }

    public static void savePem(Map<String, DataBase> pdn) {
        try {
            FileOutputStream fileOut = new FileOutputStream("pending.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(pdn);
            objectOut.close();
            fileOut.close();
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
	public static Map<String, DataBase> loadCem() { //Método para ler o arquivo de usuários
        try {
            FileInputStream fileIn = new FileInputStream("concluded.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Map<String, DataBase> cn = (Map<String, DataBase>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return cn;
        } catch (Exception exx) {
            return new HashMap<>(); // Se o arquivo ainda não existe
        }
    }

    public static void saveCem(Map<String, DataBase> cn) { //Método para salvar no arquivo
        try {
            FileOutputStream fileOut = new FileOutputStream("concluded.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cn);
            objectOut.close();
            fileOut.close();
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
	public static Map<String, DataBase> loadHAem() { //Método para ler o arquivo de usuários
        try {
            FileInputStream fileIn = new FileInputStream("haem.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Map<String, DataBase> han = (Map<String, DataBase>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return han;
        } catch (Exception exx) {
            return new HashMap<>(); // Se o arquivo ainda não existe
        }
    }

    public static void saveHAem(Map<String, DataBase> han) { //Método para salvar no arquivo
        try {
            FileOutputStream fileOut = new FileOutputStream("haem.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(han);
            objectOut.close();
            fileOut.close();
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raio da Terra em km

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distância em km
    }
	
	public static void menu1() {
		
	    int width = 33;
	    
	    String[] lines = {
	        "  INEM  ",
	        "",
	        " [0] LOGIN  ",
	        " [1] EXIT   ",
	        "",
	    };
	    
	    String uBorder = "╔" + "═".repeat(width - 2) + "╗";	    
	    String bBorder = "╚" + "═".repeat(width - 2) + "╝";
	    
	    System.out.println(uBorder);
	    for (String line : lines) {
	    	
	    	int padd = (width - 2 - line.length()) / 2;
	    		
		        String fLine = "║" + " ".repeat(Math.max(0, padd)) + line + " ".repeat(Math.max(0, width - 2 - padd - line.length())) + "║";
		        System.out.println(fLine);	    		
	    }
	    System.out.println(bBorder);
	}
	
	public static void menu2() {
		
	    int width = 33;
	    
	    String[] lines = {
	    	"  INEM - ADMIN  ",
	        "",
	        "  [1] Add worker            ",
	        "  [2] Remove worker         ",
	        "  [3] List of occurrences   ",
	        "  [4] Add ambulance         ",
	        "  [5] Remove ambulance      ",
	        "  [6] List of ambulance     ",
	        "  [7] List of hospitals     ",
	        "  [8] EXIT                  ",
	        "",
	    }; 
	    
	    String uBorder = "╔" + "═".repeat(width - 2) + "╗";	    
	    String bBorder = "╚" + "═".repeat(width - 2) + "╝";
	    
	    System.out.println(uBorder);
	    for (String line : lines) {
	    	
	    	int padd = (width - 2 - line.length()) / 2;
	    		
		        String fLine = "║" + " ".repeat(Math.max(0, padd)) + line + " ".repeat(Math.max(0, width - 2 - padd - line.length())) + "║";
		        System.out.println(fLine);	    		
	    }
	    System.out.println(bBorder);
	}
	
	public static void menu3() {
		
	    int width = 49;
	    
	    String[] lines = {
	        "",
	        "  [1] Add emergency                        ",
	        "  [2] Resolve ongoing emergency            ",
	        "  [3] Resolve pending emergency            ",
	        "  [4] List of ongoing/concluded emergencies",
	        "  [5] List of pending emergencies          ",
	        "  [6] Add Hospital bed                     ",
	        "  [7] EXIT                                 ",
	        "",
	    }; 
	        
	    String bBorder = "╚" + "═".repeat(width - 2) + "╝";
	    
	    for (String line : lines) {
	    	
	    	int padd = (width - 2 - line.length()) / 2;
	    		
		        String fLine = "║" + " ".repeat(Math.max(0, padd)) + line + " ".repeat(Math.max(0, width - 2 - padd - line.length())) + "║";
		        System.out.println(fLine);	    		
	    }
	    System.out.println(bBorder);
	}
	
	public static void listOfWorkers() {
	    try (ObjectInputStream lw = new ObjectInputStream(new FileInputStream("workers.dat"))) {
	        HashMap<String, DataBase.Worker> workers = (HashMap<String, DataBase.Worker>) lw.readObject();
	        
	        if (workers.isEmpty()) {
	            System.out.println("Nenhum trabalhador encontrado.");
	        } else {
	            System.out.println("Lista de Trabalhadores:");
	            for (Map.Entry<String, DataBase.Worker> entry : workers.entrySet()) {
	                DataBase.Worker w = entry.getValue();
	                System.out.println("Nome: " + w.getName() + ", Email: " + w.getEmail() + ", Senha: " + w.getPass());
	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Erro ao ler o ficheiro workers.dat: " + e.getMessage());
	    }
	}
	
	public static void listOfAmbs() {
	    try (ObjectInputStream la = new ObjectInputStream(new FileInputStream("amb.dat"))) {
	        HashMap<String, DataBase.Amb> ambs = (HashMap<String, DataBase.Amb>) la.readObject();
	        
	        if (ambs.isEmpty()) {
	            System.out.println("Nenhuma ambulância encontrada.");
	        } else {
	            System.out.println("Lista de Ambulância:");
	            for (Map.Entry<String, DataBase.Amb> entry : ambs.entrySet()) {
	                DataBase.Amb a = entry.getValue();
	                String astat = null;
	                if (a.getStat()) {
	                	
	                	astat = "Free";
	                }
	                else {
	                	
	                	astat = "Freen't";	                	
	                }
	                System.out.println("Plate: " + a.getName4() + ", Location: " + a.getLon2() + ", " + a.getLat2() + ", Stat: " + astat);
	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Erro ao ler o ficheiro workers.dat: " + e.getMessage());
	    }
	}
	
	public static void listOfHosps() {
	    try (ObjectInputStream lh = new ObjectInputStream(new FileInputStream("hosp.dat"))) {
	        HashMap<String, DataBase.Hosp> hosp = (HashMap<String, DataBase.Hosp>) lh.readObject();
	        
	        if (hosp.isEmpty()) {
	            System.out.println("Nenhum hospitais encontrado.");
	        } else {
	            System.out.println("Lista de Trabalhadores:");
	            for (Map.Entry<String, DataBase.Hosp> entry : hosp.entrySet()) {
	                DataBase.Hosp h = entry.getValue();
	                System.out.println("Nome: " + h.getName5() + ", Location: " + h.getLon3() + ", " + h.getLat3() + ", Beds: " + h.getBed());
	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Erro ao ler o ficheiro workers.dat: " + e.getMessage());
	    }
	}
	
	public static void listOfOgem(String name) {
	    try (ObjectInputStream logn = new ObjectInputStream(new FileInputStream("ongoin.dat"))) {
	        HashMap<String, DataBase.Em> ong = (HashMap<String, DataBase.Em>) logn.readObject();
	        
	        if (ong.isEmpty()) {
	        } else {
	            for (Map.Entry<String, DataBase.Em> entry : ong.entrySet()) {
	                DataBase.Em e = entry.getValue();
	                String estat = null;
	                if (e.getWk().equals(name)) {

		                if (e.getEstat()) {
		                	
		                	estat = "On going!";
		                }
		                System.out.println("Worker: "+ e.getWk()+"\n" +"Emergency: " + e.getName2() +"\n"+ "Location: " + e.getLon() + ", " + e.getLat() +"\n"+ "Patient: " + e.getName3() +"\n"+ "Health number: " + e.getU() +"\n"+ "Data: " +e.getT()+"\n"+"Status: " + estat+"\n"+"Hospital: " + e.getHosp()+"\n"+"Ambulance: " + e.getAmb()+"\n"+"\n");
	            }
	        }
	      }
	            
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Erro ao ler o ficheiro workers.dat: " + e.getMessage());
	    }
	} 
	
	public static void listOfPem(String name) {
	    try (ObjectInputStream lpdn = new ObjectInputStream(new FileInputStream("pending.dat"))) {
	        HashMap<String, DataBase.Em> pede = (HashMap<String, DataBase.Em>) lpdn.readObject();
	        
	        if (pede.isEmpty()) {
	        } else {
	            for (Map.Entry<String, DataBase.Em> entry : pede.entrySet()) {
	                DataBase.Em e = entry.getValue();
	                String estat = null;
	                if (e.getWk().equals(name)) {
	                	

		                if (!e.getEstat()) {
		                	
		                	estat = "Pending";
		                }
		                System.out.println("Worker: "+ e.getWk()+"\n" +"Emergency: " + e.getName2() +"\n"+ "Location: " + e.getLon() + ", " + e.getLat() +"\n"+ "Patient: " + e.getName3() +"\n"+ "Health number: " + e.getU() +"\n"+ "Data: " +e.getT()+"\n"+" Status: " + estat+"\n"+"\n");
	                }

	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Erro ao ler o ficheiro workers.dat: " + e.getMessage());
	    }
	}
	
	public static void listOfCem(String name) {
	    try (ObjectInputStream lc = new ObjectInputStream(new FileInputStream("concluded.dat"))) {
	        HashMap<String, DataBase.Em> conc = (HashMap<String, DataBase.Em>) lc.readObject();
	        
	        if (conc.isEmpty()) {
	        } else {
	            for (Map.Entry<String, DataBase.Em> entry : conc.entrySet()) {
	                DataBase.Em e = entry.getValue();
	                String estat = null;
	                if (e.getWk().equals(name)) {

		                if (e.getEstat()) {
		                	
		                	estat = "Concluded";
		                }
		                System.out.println("Worker: "+ e.getWk()+"\n" +"Emergency: " + e.getName2() +"\n"+ "Location: " + e.getLon() + ", " + e.getLat() +"\n"+ "Patient: " + e.getName3() +"\n"+ "Health number: " + e.getU() +"\n"+ "Data: " +e.getT()+"\n"+"Status: " + estat+"\n"+"Hospital: " + e.getHosp()+"\n"+"Status: " + e.getAmb()+"\n"+"\n");	                }
	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Erro ao ler o ficheiro workers.dat: " + e.getMessage());
	    }
	}
}
