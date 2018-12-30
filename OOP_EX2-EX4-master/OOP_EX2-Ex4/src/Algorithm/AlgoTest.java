package Algorithm;

import java.util.ArrayList;

import Coords.Map;
import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Ghost;
import GIS.Packman;
import GIS.Player;
import Geom.Box_temp;
import Geom.Point3D;

public class AlgoTest {
	private ArrayList<Fruit> fruits = new ArrayList<>(); // Arraylist of fruit
	private ArrayList<Packman> Packmans = new ArrayList<>();//Arraylist of Packman 
	private ArrayList<Box_temp> boxs = new ArrayList<>();//Arraylist of boxs 
	private ArrayList<Ghost> ghosts = new ArrayList<>();//Arraylist of ghosts 
	private ArrayList<Point3D> ans;
	private ArrayList<Box_temp> newBoxs;
	private MyCoords m;
	private Player player = new Player(new Point3D(0,0,0),1,1);
	private Map theMap = new Map();// create a Map object
	int verif=0;


	/**
	 * Contractor of ShortestPathAlgo Who receives Game Object
	 * @param theGame Object Game receiv 
	 */
	public AlgoTest(Game theGame) {	

		ArrayList<Fruit> clone = new ArrayList<Fruit>(theGame.Fruits_arr.size());  for (Fruit item : theGame.Fruits_arr) clone.add(item);
		this.fruits = clone;	//Create a new fruit for not to overwrite Game data later
		this.Packmans = theGame.Packman_arr;
		this.player = theGame.Player_user;
		this.boxs = theGame.Boxs_arr;
		this.ghosts = theGame.Ghost_arr;
		this.m = new MyCoords();
		this.ans = new ArrayList<>();
		this.ans = addingTo1List(this.Packmans,this.fruits);
	this.newBoxs = boxList(boxs);
	
	




	}


	public ArrayList<Box_temp> getBoxs() {
		return boxs;
	}


	public void setBoxs(ArrayList<Box_temp> boxs) {
		this.boxs = boxs;
	}


	public double update_Game(Player p) {

		double dir;

		Point3D theClose = TheCloserFurit(p,ans);
		//		if(checkBox(p)) {
		//			System.out.println("in the fun");
		//			
		//			return dir;
		//		}
		for (int i = 0; i < newBoxs.size(); i++) {
			System.out.println(newBoxs.get(i).toString());
		}

		dir = m.myDir(theClose,p.getPoint_player());



		return dir;
	}


	private ArrayList<Box_temp> boxList(ArrayList<Box_temp> boxs) {
		ArrayList<Box_temp> ansBoxs = new ArrayList<>();
	
			for (int i = 0; i < boxs.size(); i++) {
				Point3D boxGPS_1 = theMap.Pixel2GPS(boxs.get(i).getP1().x(), boxs.get(i).getP1().y());
				Point3D boxGPS_2 = theMap.Pixel2GPS(boxs.get(i).getP2().x(), boxs.get(i).getP2().y());
				Box_temp b = new Box_temp(boxGPS_1,boxGPS_2);

				
				ansBoxs.add(b);
			
		}
		return ansBoxs;



	}

	private ArrayList<Point3D> addingTo1List(ArrayList<Packman> Packmans,ArrayList<Fruit> fruits) {


		ArrayList<Point3D> ans = new ArrayList<>();

		for (int i = 0; i < Packmans.size(); i++) {
			Point3D pGPS = theMap.Pixel2GPS(Packmans.get(i).getPoint().x(), Packmans.get(i).getPoint().y());
			ans.add(pGPS);
		}

		for (int i = 0; i < fruits.size(); i++) {
			Point3D fGPS = theMap.Pixel2GPS(fruits.get(i).getFruitPoint().x(), fruits.get(i).getFruitPoint().y());
			ans.add(fGPS);
		}


		return ans;
	}


	public Point3D TheCloserFurit(Player M,ArrayList<Point3D> fruits_packs) {



		double FastTime = CalTime2Points(M,fruits_packs.get(0));
		Point3D theMostCloser = fruits_packs.get(0);
		double tempTime = 0;

		for (int i = 1; i < fruits_packs.size(); i++) {
			tempTime = CalTime2Points(M, fruits_packs.get(i));

			if(tempTime < FastTime)	{
				FastTime = tempTime;
				theMostCloser = fruits_packs.get(i);
			}	
		}

		return theMostCloser;
	}


	public double CalTime2Points(Player M , Point3D point) {
		if (m.distance3d(M.getPoint_player(), point) < M.getRadius()) {

			return 0;
		}
		else {	
			return (m.distance3d(M.getPoint_player(), point)-M.getRadius())/M.getSpeed();

		}
	}

	// the points in pixels now..



	public boolean checkBox(Player m) {

		for (int i = 0; i < newBoxs.size(); i++) {
			if(newBoxs.get(i).inBox(m.getPoint_player())) {
				System.out.println("here!");
				return true;
			}
		}
		return false;

	}
}









