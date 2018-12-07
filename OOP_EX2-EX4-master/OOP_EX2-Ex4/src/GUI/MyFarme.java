package GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Coords.Map;
import GIS.Furit;
import GIS.Packman;
import Geom.Point3D;


public class MyFarme extends JFrame implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Map theMap = new Map();
	public BufferedImage myImage;
	public ArrayList<Furit>Fruits=new ArrayList<>();
	public ArrayList<Packman>Packmans=new ArrayList<>();
	private int x = -1;//check that there is a point click
	private int y = -1;//check that there is a point click
	private int isGamer=0;// if is Gamer==1 --> Fruit :::: if is Gamer == -1 --> Packman 




	public MyFarme() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}

	private void initGUI() {


		try {	myImage = ImageIO.read(new File("Ariel1.png")); } catch (IOException e) { e.printStackTrace();	}	

		MenuBar menuBarOption = new MenuBar();

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
		MenuItem Furit_item = new MenuItem("Furit");
		AddMenu.add(Packman_Item);
		AddMenu.add(Furit_item);


		Menu Add_import=new Menu ("Import");
		MenuItem Csv_read = new MenuItem("Import Csv ");
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

			}
		});



		reload_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				theMap = new Map();
				Fruits=new ArrayList<>();
				Packmans=new ArrayList<>();
				x = -1;
				y = -1;
				isGamer=0;
				new MyFarme();
				repaint();
			}
		});




		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		Furit_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = 1;
			}
		});

		Packman_Item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = -1;

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
				}


			}

		});








	}


	public void paint(Graphics g)
	{

		Image scaledImage = myImage.getScaledInstance(this.getWidth(),this.getHeight(),myImage.SCALE_SMOOTH);
		g.drawImage(scaledImage, 0, 0, null);

		if(x!=-1 && y!=-1)
		{
			if (isGamer!=0)
			{

				for (int i=0; i<Fruits.size(); i++) 
				{
					int r_fruits = 10;
					int  x_temp_fruits=(int)(Fruits.get(i).getFuritPoint().x()*getWidth());
					int  y_temp_fruits=(int)(Fruits.get(i).getFuritPoint().y()*getHeight());	

					g.setColor(Color.red);
					g.fillOval(x_temp_fruits ,y_temp_fruits, r_fruits, r_fruits);
				}
				for (int j=0; j<Packmans.size(); j++) 
				{

					int r_Packmans= 30;
					int  x_temp_Packmans=(int)(Packmans.get(j).x()*getWidth());
					int  y_temp_Packmans=(int)(Packmans.get(j).y()*getHeight());	

					g.setColor(Color.yellow);
					g.fillOval(x_temp_Packmans, y_temp_Packmans, r_Packmans, r_Packmans);
				}
			}
		}
	}



	public void mouseClicked(MouseEvent arg) {

		double x_temp=arg.getX();
		x_temp=x_temp/getWidth();


		double y_temp=arg.getY();
		y_temp=y_temp/getHeight();
		Point3D point_return=new Point3D(x_temp, y_temp, 0);

		if (isGamer==(1))
		{	
			Fruits.add(new Furit(point_return));
			x = arg.getX();
			y = arg.getY();
			repaint();

		}else if (isGamer==(-1))
		{
			Packmans.add(new Packman(point_return, 1, 1));
			x = arg.getX();
			y = arg.getY();
			repaint();
		}

	}

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
