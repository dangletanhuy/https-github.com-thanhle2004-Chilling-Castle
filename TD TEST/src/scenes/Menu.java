package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.Game;
import objects.MusicBackGround;
import objects.SoundEffect;
import ui.MyButton;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

	private MyButton bSettings, bStage2 , bPlaying, bTowers;
	private BufferedImage  background, bPlayImg, bTowersImg, bSettingImg;

	private ArrayList<BufferedImage> sprites = new ArrayList<>();

	private MyButton bPlay;

	private Settings settings;

	private MusicBackGround musicBackground;
	public SoundEffect menuSoundEffect;
	
	public Menu(Game game) {
		super(game);
		musicBackground = new MusicBackGround();
		menuSoundEffect = new SoundEffect();
		settings = new Settings(game, null);

		importImg();
		initButtons();

		//play background music after 2s
		new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			musicBackground.playMusic(0);
		}).start();

	}

	
	private void importImg() {
		InputStream bg = getClass().getResourceAsStream("/BackgroundLobby.png");
		InputStream bplay = getClass().getResourceAsStream("/bPlay.png");
		InputStream btowers = getClass().getResourceAsStream("/bTowers.png");
		InputStream bsetting = getClass().getResourceAsStream("/bSetting.png");

		try {
			background = ImageIO.read(bg);
			bPlayImg = ImageIO.read(bplay);
			bTowersImg = ImageIO.read(btowers);
			bSettingImg = ImageIO.read(bsetting);		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void initButtons() {

		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150;
		int yOffset = 90;


		bStage2 = new MyButton("Stage2", x, y + yOffset, w, h, bPlayImg);
		bPlaying = new MyButton("Play", x, y+ yOffset * 2, w, h, bPlayImg);
		bSettings = new MyButton("Settings", x, y + yOffset * 3, w, h, bSettingImg);
		bTowers = new MyButton("Towers", x, y + yOffset * 4, w, h, bTowersImg);

	}

	@Override
	public void render(Graphics g) {
		drawBackground(g);
		drawButtons(g);

	}

	private void drawButtons(Graphics g) {

		bStage2.draw(g);
		bSettings.draw(g);
		bTowers.draw(g);
		bPlaying.draw(g);

	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bPlaying.getBounds().contains(x, y))
			SetGameState(STAGES);
		else if (bStage2.getBounds().contains(x, y))
			SetGameState(STAGE2);
		else if (bSettings.getBounds().contains(x, y))
			SetGameState(SETTINGS);
		else if (bTowers.getBounds().contains(x, y))
			SetGameState(TOWER);
	
	}

	@Override
	public void mouseMoved(int x, int y) {
		bPlaying.setMouseOver(false);
		bSettings.setMouseOver(false);
		bTowers.setMouseOver(false);

		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMouseOver(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMouseOver(true);
		else if (bTowers.getBounds().contains(x, y))
			bTowers.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {		
		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMousePressed(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMousePressed(true);
		else if (bTowers.getBounds().contains(x, y))
			bTowers.setMousePressed(true);
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bPlaying.resetBooleans();
		bSettings.resetBooleans();
		bTowers.resetBooleans();


	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}
	
	private void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, game);
	}

	public void setMusicBackgroundStop() {
		musicBackground.stop();
	}

	public void setMusicBackgroundPlay() {
		musicBackground.play();
	}

	public void setMusicVolume(float i) {
		musicBackground.setVolume(i);
	}

}