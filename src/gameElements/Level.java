package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import engine.Game;
import engine.Sounds;

public class Level {
	private Game game;
	private BufferedImage backGround;
	private BufferedImage foreGround;
	private ArrayList<Wall> walls;
	private ArrayList<Enemy> enemies;
	private ArrayList<Sprite> allElements;
	private ArrayList<Beam> beams;
	private ArrayList<Lava> lava;
	private ArrayList<MissilePowerUp> missilePowerUps;
	private ArrayList<Missile> missiles;
	private ArrayList<EnergyTankPowerUp> energyTanks;
	private ArrayList<EnergyDrop> energyDrops;
	private ArrayList<MissileDrop> missileDrops;
	private ArrayList<RinkaSpawner> rinkaSpawners;
	private ArrayList<Rinka> rinkas;
	private ArrayList<MusicChanger> musicChangers;
	private ArrayList<Wall> mbWalls;
	private ArrayList<MissileDoor> missileDoors;
	private MBGlass mbGlass;
	private MotherBrain motherBrain;
	private MorphBallPowerUp morphBall;
	private Elevator elevator;
	private Random rand;

	public Level(Game game){
		this.game = game;
		allElements = new ArrayList<Sprite>();
		enemies = new ArrayList<Enemy>();
		walls = new ArrayList<Wall>();
		beams = new ArrayList<Beam>();
		lava = new ArrayList<Lava>();
		missilePowerUps = new ArrayList<MissilePowerUp>();
		missiles = new ArrayList<Missile>();
		energyTanks = new ArrayList<EnergyTankPowerUp>();
		energyDrops = new ArrayList<EnergyDrop>();
		missileDrops = new ArrayList<MissileDrop>();
		rinkaSpawners = new ArrayList<RinkaSpawner>();
		rinkas = new ArrayList<Rinka>();
		musicChangers = new ArrayList<MusicChanger>();
		mbWalls = new ArrayList<Wall>();
		missileDoors = new ArrayList<MissileDoor>();
		rand = new Random();
	}

	public void loadBackGround(int levelNumber){
		try{
			backGround = ImageIO.read(getClass().getResource("/resources/backgrounds/back"  + levelNumber + ".png"));
			foreGround = ImageIO.read(getClass().getResource("/resources/backgrounds/fore"  + levelNumber + ".png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void update(){
		if(elevator.bounding.intersects(game.getPlayer().bounding)){
			game.winGame();
		}
		if(morphBall != null){
			morphBall.update();
			if(game.getPlayer().bounding.intersects(morphBall.bounding)){
				game.collectItem(morphBall);
				allElements.remove(morphBall);
				morphBall = null;
			}
		}

		for(EnergyDrop e : energyDrops){
			e.update();
			if(game.getPlayer().bounding.intersects(e.bounding)){
				e.setToRemove(true);
				game.getPlayer().gainEnergy(10);
			}
		}
		for(MissileDrop m : missileDrops){
			m.update();
			if(game.getPlayer().bounding.intersects(m.bounding)){
				m.setToRemove(true);
				game.getPlayer().gainMissiles(3);
			}
		}
		for(EnergyDrop e : energyDrops){
			if(e.toRemove){
				energyDrops.remove(e);
				allElements.remove(e);
				break;
			}
		}
		for(MissileDrop m : missileDrops){
			if(m.toRemove){
				missileDrops.remove(m);
				allElements.remove(m);
				break;
			}
		}

		for(MissilePowerUp m : missilePowerUps){
			m.update();
			if(game.getPlayer().bounding.intersects(m.bounding)){
				game.collectItem(m);
				missilePowerUps.remove(m);
				allElements.remove(m);
				break;
			}
		}

		for(EnergyTankPowerUp e : energyTanks){
			e.update();
			if(game.getPlayer().bounding.intersects(e.bounding)){
				game.collectItem(e);
				energyTanks.remove(e);
				allElements.remove(e);
				break;
			}
		}

		for(Missile m : missiles){
			m.update();
			for(Enemy e : enemies){
				if(m.bounding.intersects(e.bounding)){
					e.hit(3);
					m.setToRemove(true);
				}
			}
			for(Wall w : walls){
				if(m.bounding.intersects(w.bounding)){
					if(w instanceof MissileDoor){
						Sounds.playDoorOpen();
						w.setToRemove(true);
					}
					m.setToRemove(true);
				}
			}
		}

		for(MissileDoor m : missileDoors){
			if(m.isToRemove()){
				removeMissileDoor(m);
				break;
			}
		}

		for(Missile m : missiles){
			if(m.toRemove){
				missiles.remove(m);
				allElements.remove(m);
				break;
			}
		}

		for(Beam b : beams){
			b.update();
			for(Enemy e : enemies){
				if(b.bounding.intersects(e.bounding)){
					if(! (e instanceof MotherBrain))
						e.hit(1);
					b.setToRemove(true);
				}
			}
			for(Wall w : walls){
				if(b.bounding.intersects(w.bounding)){
					b.setToRemove(true);
				}
			}
		}
		for(RinkaSpawner r : rinkaSpawners){
			r.update();
		}
		for(Enemy e : enemies){
			if(e.isOnScreen()){
				e.update(game.getPlayer(), walls);
			}
		}

		for(Beam b : beams){
			if(b.isToRemove()){
				beams.remove(b);
				allElements.remove(b);
				break;
			}
		}
		for(Enemy e : enemies){
			if(e.isToRemove()){
				if(!(e instanceof MBGlass) && !(e instanceof MotherBrain) && !(e instanceof Spikes)){
					double energyMissile = rand.nextDouble();
					if(game.getPlayer().hasMissiles() && energyMissile < 0.5){
						MissileDrop mi = new MissileDrop(e.xPosition, e.yPosition);
						missileDrops.add(mi);
						allElements.add(mi);
					}
					else{
						EnergyDrop en = new EnergyDrop(e.xPosition, e.yPosition);
						energyDrops.add(en);
						allElements.add(en);
					}
				}
				if(e instanceof MotherBrain){
					removeMBWalls();
					Sounds.pauseGameMusic();
					Sounds.playEscapeSequence();
					game.startTimer();
				}
				enemies.remove(e);
				allElements.remove(e);
				break;
			}
		}

		for(MusicChanger m : musicChangers){
			if(m.bounding.intersects(game.getPlayer().bounding)){
				if(m.getMusic() == 0){
					if(game.getPlayer().getYVelocity() > 0){
						Sounds.pauseGameMusic1();
						Sounds.playGameMusic2();
					}
					else if(game.getPlayer().getYVelocity() < 0){
						Sounds.pauseGameMusic2();
						Sounds.playGameMusic1();
					}
				}
				else if(m.getMusic() == 1){
					if(game.getPlayer().getYVelocity() > 0){
						Sounds.pauseGameMusic1();
						Sounds.playGameMusic3();
					}
					else if(game.getPlayer().getYVelocity() < 0){
						Sounds.pauseGameMusic3();
						Sounds.playGameMusic1();
					}
				}
			}
		}
		if(motherBrain.isOnScreen())
			motherBrain.update(game.getPlayer(), walls);
	}

	public BufferedImage getBackGround(){
		return backGround;
	}
	public BufferedImage getForeGround(){
		return foreGround;
	}

	public void addWall(Wall w){
		walls.add(w);
		allElements.add(w);
	}

	public void addEnemy(Enemy e){
		enemies.add(e);
		allElements.add(e);
	}
	public void addLava(Lava l){
		lava.add(l);
		allElements.add(l);
	}
	public void addMissilePowerUp(MissilePowerUp m){
		missilePowerUps.add(m);
		allElements.add(m);
	}
	public void setMorphBall(MorphBallPowerUp m){
		morphBall = m;
		allElements.add(m);
	}

	public MorphBallPowerUp getMorphBall(){
		return morphBall;
	}

	public ArrayList<Wall> getWalls(){
		return walls;
	}
	public ArrayList<MissilePowerUp> getMissilePowerUps(){
		return missilePowerUps;
	}
	public void addMissile(Missile m){
		missiles.add(m);
		allElements.add(m);
	}
	public ArrayList<Missile> getMissiles(){
		return missiles;
	}
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	public void addEnergyTank(EnergyTankPowerUp e){
		energyTanks.add(e);
		allElements.add(e);
	}
	public ArrayList<EnergyTankPowerUp> getEnergyTanks(){
		return energyTanks;
	}
	public void addBeam(Beam b){
		beams.add(b);
		allElements.add(b);
	}
	public ArrayList<Beam> getBeams(){
		return beams;
	}
	public ArrayList<EnergyDrop> getEnergyDrops(){
		return energyDrops;
	}
	public ArrayList<MissileDrop> getMissileDrops(){
		return missileDrops;
	}
	public ArrayList<Lava> getLava(){
		return lava;
	}
	public ArrayList<Sprite> getAllElements(){
		return allElements;
	}
	public ArrayList<RinkaSpawner> getRinkaSpawners(){
		return rinkaSpawners;
	}
	public void addRinkaSpawner(RinkaSpawner r){
		rinkaSpawners.add(r);
		allElements.add(r);
		walls.add(r);
	}
	public void addRinka(Rinka r){
		rinkas.add(r);
		allElements.add(r);
		enemies.add(r);
	}
	public void removeRinka(Rinka r){
		rinkas.remove(r);
		allElements.remove(r);
		enemies.remove(r);
		if(r.isOnScreen()){
			double energyMissile = rand.nextDouble();
			if(game.getPlayer().hasMissiles() && energyMissile < 0.3){
				MissileDrop mi = new MissileDrop(r.xPosition, r.yPosition);
				missileDrops.add(mi);
				allElements.add(mi);
			}
			else if(energyMissile < 0.6){
				EnergyDrop en = new EnergyDrop(r.xPosition, r.yPosition);
				energyDrops.add(en);
				allElements.add(en);
			}
		}
	}
	public ArrayList<Rinka> getRinkas(){
		return rinkas;
	}
	public void addMusicChanger(MusicChanger m){
		allElements.add(m);
		musicChangers.add(m);
	}
	public void addMBWall(Wall mbw){
		walls.add(mbw);
		mbWalls.add(mbw);
		allElements.add(mbw);
	}
	public void removeMBWalls(){
		for(Wall w : mbWalls){
			walls.remove(w);
			allElements.remove(w);
		}
	}
	public void addMBGlass(MBGlass m){
		mbGlass = m;
		enemies.add(m);
		allElements.add(m);
	}
	public void addMotherBrain(MotherBrain mb){
		motherBrain = mb;
		enemies.add(mb);
		allElements.add(mb);
	}
	public void addElevator(Elevator e){
		elevator = e;
		allElements.add(e);
	}
	public Elevator getElevator(){
		return elevator;
	}
	public void removeSpikes(){
		for(Enemy e : enemies){
			if(e instanceof Spikes)
				e.setToRemove(true);
		}
	}
	public void addMissileDoor(MissileDoor m){
		missileDoors.add(m);
		allElements.add(m);
		walls.add(m);
	}
	public void removeMissileDoor(MissileDoor m){
		missileDoors.remove(m);
		allElements.remove(m);
		walls.remove(m);
	}
	public ArrayList<MissileDoor> getMissileDoors(){
		return missileDoors;
	}
}