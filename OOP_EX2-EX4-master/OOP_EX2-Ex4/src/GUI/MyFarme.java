package GUI;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Algorithm.ShortestPathAlgo;
import Coords.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Ghost;
import GIS.Packman;
import GIS.Path;
import GIS.Player;
import Geom.Box;
import Geom.Point3D;

/**
 * This Class manages the graphical representation of the entire program.
 * the class is an implements of MouseListener is an extents of JFrame.
 * More: http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
 * @author Mimoun Shimon and Omer Paz
 *
 */
public class MyFarme extends JFrame implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public BufferedImage myImage;
	public BufferedImage packimage;
	public BufferedImage Fruitimage;
	public BufferedImage ghost;
	public BufferedImage box;
	public BufferedImage player;



	double radius = 1;
	int speed = 1;
	MenuBar menuBarOption = new MenuBar();
	public Map theMap = new Map();
	public  ArrayList<Packman> Packman_arr = new ArrayList<>();
	public  ArrayList<Fruit> Fruits_arr = new ArrayList<>();
	public  ArrayList<Box> Boxs_arr = new ArrayList<>();
	public  ArrayList<Ghost> Ghost_arr = new ArrayList<>();
	private Game myGame=new Game(Packman_arr, Fruits_arr,Boxs_arr,Ghost_arr);
	private int isGamer=0;// if is Gamer==1 --> Fruit :::: if is Gamer == -1 --> Packman 
	public boolean Start_game=false;
	public boolean drwaline = false;
	public ArrayList<Packman> ArrayTemp=new ArrayList<>();
	Path TheCloserPackman;


	public MyFarme() 
	{
		initGUI();		
		this.addMouseListener(this); 

	}

	private void initGUI() {


		try {	myImage = ImageIO.read(new File(theMap.getMapDiractory())); } catch (IOException e) { e.printStackTrace();	}	
		try {	packimage = ImageIO.read(new File("Pictures&Icones/packnew.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	Fruitimage = ImageIO.read(new File("Pictures&Icones/fruit.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	box = ImageIO.read(new File("Pictures&Icones/box.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	ghost = ImageIO.read(new File("Pictures&Icones/ghost.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	player = ImageIO.read(new File("Pictures&Icones/Player.png")); } catch (IOException e) { e.printStackTrace();	}





		Menu OptionMenu = new Menu("File"); 
		menuBarOption.add(OptionMenu);
		MenuItem runItem = new MenuItem("Run");
		MenuItem reload_item = new MenuItem("Reload");
		OptionMenu.add(runItem);
		OptionMenu.add(reload_item);
		MenuItem exit = new MenuItem("Exit");
		OptionMenu.add(exit);




		Menu AddMenu = new Menu("Add"); 
		menuBarOption.add(AddMenu);

		MenuItem Packman_Item = new MenuItem("Packman");
		MenuItem Fruit_item = new MenuItem("Fruit");	
		MenuItem Player_User_item = new MenuItem("Player_User");	

		MenuItem Ghost_item = new MenuItem("Ghost");
		AddMenu.add(Player_User_item);
		AddMenu.add(Packman_Item);
		AddMenu.add(Fruit_item);
		AddMenu.add(Ghost_item);

		Menu AddLimite = new Menu("Limit"); 
		MenuItem Box_item = new MenuItem("Box");
		menuBarOption.add(AddLimite);
		AddLimite.add(Box_item);



		Menu SetMenu = new Menu("Set"); 

		MenuItem setraduisAll = new MenuItem("Radius All");
		MenuItem setradius2Pack = new MenuItem("Radius To Packman");
		MenuItem setSpeedAll = new MenuItem("Speed All");
		MenuItem setSpeed2Pack = new MenuItem("Speed To Packman");
		MenuItem setWeightAll= new MenuItem("Weight All");
		MenuItem setWeight2Friut= new MenuItem("Weight to Friut");


		SetMenu.add(setraduisAll);
		SetMenu.add(setradius2Pack);
		SetMenu.add(setSpeedAll);
		SetMenu.add(setSpeed2Pack);
		SetMenu.add(setWeightAll);
		SetMenu.add(setWeight2Friut);


		menuBarOption.add(SetMenu);


		Menu Add_import=new Menu ("Import");
		MenuItem Csv_read = new MenuItem("Csv");
		Add_import.add(Csv_read);


		menuBarOption.add(Add_import);
		Menu Add_export=new Menu ("Export");
		menuBarOption.add(Add_export);
		MenuItem Csv_writing_csv = new MenuItem(" Csv ");
		MenuItem Csv_writing_kml = new MenuItem(" Kml ");
		Add_export.add(Csv_writing_csv);
		Add_export.add(Csv_writing_kml);

		this.setMenuBar(menuBarOption);


		//Turn on the buttons

		runItem.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				if(myGame.Packman_arr.size() >0 && myGame.Fruits_arr.size() > 0 ) {
					Start_game=true;
					drwaline = true;
					isGamer=2;

					for (int i = 0; i < myGame.Packman_arr.size(); i++) {
						ArrayTemp.add(new Packman(myGame.Packman_arr.get(i)));
						ArrayTemp.get(i).getPath().setPath(myGame.Packman_arr.get(i).getPath().TheCurrentPath());
					}

					packSmiulation();


				}
				Start_game = false;

			}
		});

		reload_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				radius = 1;
				speed = 1;
				new MenuBar();
				new Map();
				Packman_arr = new ArrayList<>();
				Fruits_arr = new ArrayList<>();
				Boxs_arr = new ArrayList<>();
				Ghost_arr = new ArrayList<>();
				myGame=new Game(Packman_arr, Fruits_arr,Boxs_arr,Ghost_arr);
				isGamer=0;
				Start_game=false;
				drwaline = false;
				ArrayTemp=new ArrayList<>();
				TheCloserPackman=null;
				repaint();
			}
		});




		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		Fruit_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = 1;

			}
		});



		Ghost_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = 3;
			}
		});



		Box_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				createdbox();
				repaint();

			}
		});


		Packman_Item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = -1;

			}
		});

//		Player_User_item.addActionListener  (new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				isGamer = 2;
//				   Panel panel = new Panel();
//				   panel.addKeyListener(new KeyListener() {
//				        public void keyTyped(KeyEvent e) {
//
//				        }
//
//				   }
//				keyPressed(e);
//
//			}
//		});

		//
		Packman_Item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = -1;

			}
		});

		setraduisAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGame.Packman_arr.size() > 0) {
					String radius= JOptionPane.showInputDialog("Please input the Radius for all the Packmans: ");
					double input_radius = Double.parseDouble(radius);
					for (int i = 0; i < myGame.Packman_arr.size(); i++) {
						myGame.Packman_arr.get(i).setRadius(input_radius);
					}
				} else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}

			}
		});
		setradius2Pack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myGame.Packman_arr.size() > 0) {
					String i = JOptionPane.showInputDialog("Please input which packman you want to change (starting from 0)");
					int numberPack = Integer.parseInt(i);
					if(numberPack > myGame.Packman_arr.size()) {
						JOptionPane.showMessageDialog(null,"EROR: Cant find this Packman");

					}else {
						String radius= JOptionPane.showInputDialog("Please input the Radius for this Packman: ");
						double input_radius = Double.parseDouble(radius);
						myGame.Packman_arr.get(numberPack).setRadius(input_radius);	
					}
				}else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}
			}
		});


		setSpeed2Pack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myGame.Packman_arr.size() > 0) {
					String i = JOptionPane.showInputDialog("Please input which packman you want to change (starting from 0)");
					int numberPack = Integer.parseInt(i);
					if(numberPack > myGame.Packman_arr.size()) {
						JOptionPane.showMessageDialog(null,"EROR: Cant find this Packman");

					}else {
						String Speed= JOptionPane.showInputDialog("Please input the Speed for this Packman: ");
						double input_Speed = Double.parseDouble(Speed);
						myGame.Packman_arr.get(numberPack).setSpeed(input_Speed);	
					}
				}else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}
			}
		});
		setSpeedAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGame.Packman_arr.size() > 0) {
					String speed= JOptionPane.showInputDialog("Please input the Speed for all the Packmans: ");
					double input_speed = Double.parseDouble(speed);
					for (int i = 0; i < myGame.Packman_arr.size(); i++) {
						myGame.Packman_arr.get(i).setSpeed(input_speed);
					}
				} else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}

			}
		});



		Csv_read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Select an Csv File");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getSelectedFile().getPath());
					Game theGame = new Game(Packman_arr, Fruits_arr,Boxs_arr,Ghost_arr);
					theGame.setfile_directory(fileChooser.getSelectedFile().getPath());
					try {
						theGame.Csvread();
						myGame.Packman_arr = theGame.Packman_arr;
						myGame.Fruits_arr = theGame.Fruits_arr;
						myGame.Boxs_arr = theGame.Boxs_arr;
						myGame.Ghost_arr = theGame.Ghost_arr;
						myGame.file_directory = theGame.file_directory;
						isGamer = 2;

						repaint();

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		});





		Csv_writing_csv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Export to Csv File");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					System.out.println(selectedFile.getAbsolutePath());


					myGame.setfile_directory(selectedFile.getAbsolutePath());
					try {
						myGame.save2Csv();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}


			}

		});


		Csv_writing_kml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Export to KML File");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("kml","KML");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					System.out.println(selectedFile.getAbsolutePath());


					myGame.setfile_directory(selectedFile.getAbsolutePath());




					try {
						myGame.save_to_kml();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}


				}


			}

		});

	}
	private void  packSmiulation() {
		ArrayList<Packman> myPackmens = new ArrayList<>();



		ShortestPathAlgo algo = new ShortestPathAlgo(myGame);
		myPackmens = algo.algoMultiPackmans();

		for (int i = 0; i < myPackmens.size(); i++) {
			Path p = algo.algoSinglePackman(myPackmens.get(i));
			myPackmens.get(i).getPath().setPath(p.TheCurrentPath());
			myPackmens.get(i).getPath().setTheTotalTime(p.getTheTime());

		}




		for (Packman packman : myPackmens)	{


			Thread thread = new Thread() 
			{
				@Override
				public void run()
				{


					for (int i = 0; i < packman.getPath().TheCurrentPath().size(); i++) {
						
						if(Fruits_arr.size() == 0) {
							break;
						}


						for (int j = 0; j < packman.getPath().getTheTime(); j++) {
							Point3D ans = packman.getPath().theNextPoint(packman,packman.getPath().TheCurrentPath().get(i) , j);
							try {
								sleep(20);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							packman.setPackLocation(ans);

							repaint();
							if(packman.getPath().CalTime2Points(packman,packman.getPath().TheCurrentPath().get(i) ) <= 5) {
								System.out.println("I is : "+i);
								System.out.println("pack size: "+packman.getPath().TheCurrentPath().size());
								System.out.println("array size: "+myGame.Fruits_arr.size());
								System.out.println("HERE");
								myGame.Fruits_arr.remove(algo.getIndexFurit(packman.getPath().TheCurrentPath().get(i), packman.getPath().TheCurrentPath()));
								break;

							}

							try {
								sleep(30);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}
			};
			thread.start();


		}
	}





	public void paint(Graphics g) {

		Image scaledImage = myImage.getScaledInstance(this.getWidth(),this.getHeight(),myImage.SCALE_SMOOTH);
		g.drawImage(scaledImage, 8,50, getWidth()-17, getHeight()-60,null);


		Graphics2D g2 = (Graphics2D)g;

		g2.setStroke(new BasicStroke(3));

		double x1 = 0;
		double y1 = 0 ;
		double x2 = 0;
		double y2 = 0 ;


		if (isGamer!=0) {

			if(myGame.Fruits_arr.size() > 0) {
				for (int i=0; i<myGame.Fruits_arr.size(); i++) 	{
					x1=(int)(myGame.Fruits_arr.get(i).getFruitPoint().x()*getWidth());
					y1=(int)(myGame.Fruits_arr.get(i).getFruitPoint().y()*getHeight());	

					g.drawImage(Fruitimage, (int)x1-5, (int)y1-6,30, 30, null);
				}


			}

			for (int j=0; j<myGame.Packman_arr.size(); j++) {

				x1=(myGame.Packman_arr.get(j).getPoint().x()*getWidth());
				y1=(myGame.Packman_arr.get(j).getPoint().y()*getHeight());	


				g.drawImage(packimage, (int)x1-6,(int) y1-7,30, 30, null);

			}

			for (int j=0; j<myGame.Ghost_arr.size(); j++) {
				System.out.println((Boxs_arr.get(0).toString()));
				x1=(myGame.Ghost_arr.get(j).getPoint().x()*getWidth());
				y1=(myGame.Ghost_arr.get(j).getPoint().y()*getHeight());	

				g.drawImage(ghost, (int)x1,(int) y1,30, 30, null);

			}
			for (int j=0; j<myGame.Boxs_arr.size(); j++) {

				x1=(myGame.Boxs_arr.get(j).getP1().x()*getWidth());
				y1=(myGame.Boxs_arr.get(j).getP1().y()*getHeight());
				x2=(myGame.Boxs_arr.get(j).getP2().x()*getWidth());
				y2=(myGame.Boxs_arr.get(j).getP2().y()*getHeight());	
				double width = x2-x1;
				double height = y2-y1;
				g.drawImage(box, (int)x1,(int) y1,(int)width, (int)height, null);

			}

			if(myGame.Player_user!=null){
			x1=(myGame.Player_user.getPoint_player().x()*getWidth());
			y1=(myGame.Player_user.getPoint_player().y()*getHeight());	

			g.drawImage(player,(int)x1,(int) y1,30, 30,null);
			}
		}

	}





	@Override
	public void mouseClicked(MouseEvent arg) {

		double x_temp=arg.getX();
		x_temp=x_temp/getWidth();


		double y_temp=arg.getY();
		y_temp=y_temp/getHeight();
		Point3D point_return=new Point3D(x_temp, y_temp, 0);
		Point3D covertedfromPixel = theMap.Pixel2GPS(x_temp, y_temp);


		if (isGamer==(1))
		{	
			myGame.Fruits_arr.add(new Fruit(point_return,1));

			System.out.println("Fruit "+covertedfromPixel.toString());

			repaint();

		}else if (isGamer==(-1))
		{
			myGame.Packman_arr.add(new Packman(point_return, radius, speed));
			System.out.println("Packman "+covertedfromPixel.toString());

			repaint();
		}else if(isGamer==3)
		{
			myGame.Ghost_arr.add(new Ghost(point_return, radius, speed));
			System.out.println("Ghost "+covertedfromPixel.toString());
			repaint();
		}else if(isGamer==2)
		{
			myGame.Player_user=new Player(point_return, speed);
			System.out.println("Ghost "+covertedfromPixel.toString());
			repaint();
		}

	}

	public void createdbox() {
		double X1=0;
		double X2=0;
		double Y1=0;
		double Y2=0;


		do {
			String	x1=JOptionPane.showInputDialog("Enter X1 Pixel");
			X1=Double.parseDouble(x1);
		} while ((X1<0) ||(X1>this.getWidth()));

		do {
			String	y1=JOptionPane.showInputDialog("Enter Y1 Pixel");
			Y1=Double.parseDouble(y1);
		} while ((Y1<0) ||(Y1>this.getHeight()));

		do {
			String	x2=JOptionPane.showInputDialog("Enter X2 Pixel");
			X2=Double.parseDouble(x2);
		} while ((X2<0) ||(X2>this.getWidth())||(X2<X1));

		do {
			String	y2=JOptionPane.showInputDialog("Enter Y2 Pixel");	
			Y2=Double.parseDouble(y2);
		} while ((Y2<0) ||(Y2>this.getWidth())||(Y2<Y1));

		Boxs_arr.add(new Box(new Point3D(X1/getWidth(), Y1/getHeight()),
				new Point3D(X2/getWidth(), Y2/getHeight())));

	}
	
	
	
	
	
	  public void keyPressed(KeyEvent e) {
          int keyCode = e.getKeyCode();
          if(keyCode == KeyEvent.VK_DOWN) {
        	  double y=   ( myGame.Player_user.getPoint_player().y()*getHeight())-1;
              myGame.Player_user.setPoint_player(new Point3D(myGame.Player_user.getPoint_player().y(),y/getHeight()));
           
          }
          if(keyCode == KeyEvent.VK_UP) {
        	  double y=   ( myGame.Player_user.getPoint_player().y()*getHeight())+1;
              myGame.Player_user.setPoint_player(new Point3D(myGame.Player_user.getPoint_player().y(),y/getHeight()));
           
          }
          if(keyCode == KeyEvent.VK_RIGHT) {
        	  double x=   ( myGame.Player_user.getPoint_player().x()*getWidth())+1;
              myGame.Player_user.setPoint_player(new Point3D(x/getWidth(), myGame.Player_user.getPoint_player().y()));
           
          }
          if(keyCode == KeyEvent.VK_LEFT) {
        	  double x=   ( myGame.Player_user.getPoint_player().x()*getWidth())-1;
              myGame.Player_user.setPoint_player(new Point3D(x/getWidth(), myGame.Player_user.getPoint_player().y()));
           
          }
      }
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub		

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


}
