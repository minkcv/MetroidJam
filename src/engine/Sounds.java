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
}
