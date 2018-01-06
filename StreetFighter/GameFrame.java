package com.mayank.StreetFighter;

import java.awt.Toolkit;
import javax.swing.JFrame;

import jaco.mp3.player.MP3Player;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements GameConstants {
	
	private void playMusic() {
		MP3Player mp3 = new MP3Player(GameFrame.class.getResource("opening.mp3"));
		mp3.play();
	}
	
	
	public GameFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(GAME_TITLE);
		
		Board board = new Board();
		add(board);
		
		setVisible(true);
		playMusic();
		Toolkit.getDefaultToolkit().sync();
	}		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameFrame frame = new GameFrame();
	}

}
