package engine;

import java.io.IOException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Sounds {
	private static Audio start;
	private static Audio title;
	private static Audio jump;
	private static Audio shoot;
	private static Audio obtainPowerUp;
	private static Audio enterMorphBall;
	private static Audio footSteps;
	private static boolean loopingFootSteps;
	private static Audio enemyHit;
	private static Audio enemyDie;
	private static Audio playerHit;
	private static Audio inLava;
	private static Audio smallPickup;
	private static Audio gameMusic1;
	private static float gameMusic1Position;
	private static boolean gameMusic1Paused;
	private static Audio gameMusic2;
	private static float gameMusic2Position;
	private static boolean gameMusic2Paused;
	private static Audio gameOverMusic;
	private static Audio endMusic;
	private static Audio mbrainDie;
	private static Audio escapeSequence;
	private static Audio lastMusic;
	private static Audio lowEnergy;
	private static Audio doorOpen;

	public static void loadSounds(){
		try {
			start = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/start.wav"));
			title = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/mt-title.wav"));
			jump = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/jump.wav"));
			shoot = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/shoot.wav"));
			obtainPowerUp = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/power-up-obtain.wav"));
			enterMorphBall = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/morph-ball-enter.wav"));
			footSteps = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/footstep_together.wav"));
			enemyHit = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/enemy_hit.wav"));
			enemyDie = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/enemy_die.wav"));
			playerHit = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/hurt.wav"));
			inLava = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/lava.wav"));
			smallPickup = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/hp-pickup.wav"));
			gameMusic1 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/met-ingame.wav"));
			gameMusic2 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/met-lair.wav"));
			endMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/metroid-ending.wav"));
			mbrainDie = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/mbrain-die.wav"));
			escapeSequence = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/metroid-escapeSeq.wav"));
			gameOverMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/die.wav"));
			lowEnergy = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/lowhealth-1beep.wav"));
			doorOpen = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/sounds/door-open.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void playTitle(){
		title.playAsMusic(1.0f, 1.0f, true);
	}
	public static void stopTitle(){
		title.stop();
	}
	public static void playStart(){
		start.playAsMusic(1.0f, 1.0f, false);
	}
	public static boolean startFinished(){
		return ! start.isPlaying();
	}
	public static void playJump(){
		jump.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playShoot(){
		shoot.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playObtainPowerUp(){
		obtainPowerUp.playAsMusic(1.0f, 1.0f, false);
	}
	public static void playEnterMorphBall(){
		enterMorphBall.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playEnemyHit(){
		enemyHit.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playEnemyDie(){
		enemyDie.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playPlayerHit(){
		playerHit.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playInLava(){
		inLava.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playSmallPickup(){
		smallPickup.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public static void playFootSteps(){
		if(! loopingFootSteps){
			footSteps.playAsSoundEffect(1.0f, 1.0f, true);
			loopingFootSteps = true;
		}
	}
	public static void stopFootSteps(){
		footSteps.stop();
		loopingFootSteps = false;
	}
	public static void playGameMusic1(){
		gameMusic1.playAsMusic(1.0f, 1.0f, true);
		if(gameMusic1Paused)
			gameMusic1.setPosition(gameMusic1Position);
	}
	public static void pauseGameMusic1(){
		gameMusic1Position = gameMusic1.getPosition();
		gameMusic1Paused = true;
		gameMusic1.stop();
	}
	public static void playGameMusic2(){
		gameMusic2.playAsMusic(1.0f, 1.0f, true);
		if(gameMusic2Paused)
			gameMusic1.setPosition(gameMusic2Position);
	}
	public static void pauseGameMusic2(){
		gameMusic2Position = gameMusic2.getPosition();
		gameMusic2Paused = true;
		gameMusic2.stop();
	}
	public static void playEndMusic(){
		endMusic.playAsMusic(1.0f, 1.0f, true);
	}
	public static void playMotherBrainDie(){
		mbrainDie.playAsMusic(1.0f, 1.0f, false);
	}
	public static boolean finishedPlayMotherBrainDie(){
		return ! mbrainDie.isPlaying();
	}
	public static void playEscapeSequence(){
		escapeSequence.playAsMusic(1.0f, 1.0f, true);
	}
	public static void stopEscapeSequence(){
		escapeSequence.stop();
	}
	public static void resumeGameMusic(){
		if(lastMusic == gameMusic1)
			playGameMusic1();
		else if(lastMusic == gameMusic2)
			playGameMusic2();
	}
	public static void pauseGameMusic(){
		if(gameMusic1.isPlaying()){
			pauseGameMusic1();
			lastMusic = gameMusic1;
		}
		else if(gameMusic2.isPlaying()){
			pauseGameMusic2();
			lastMusic = gameMusic2;
		}
	}
	public static void playGameOver(){
		gameOverMusic.playAsMusic(1.0f, 1.0f, false);
	}
	public static void playLowEnergy(){
		lowEnergy.playAsSoundEffect(1.0f, 1.0f, true);
	}
	public static boolean playingLowEnergy(){
		return lowEnergy.isPlaying();
	}
	public static void stopLowEnergy(){
		lowEnergy.stop();
	}
	public static void playDoorOpen(){
		doorOpen.playAsSoundEffect(1.0f, 1.0f, false);
	}
}
