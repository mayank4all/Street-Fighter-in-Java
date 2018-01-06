package com.mayank.StreetFighter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements GameConstants,State,EnemyState, ActionListener{
	private Timer timer;
	private Player player;
	private Enemy enemy;
	Life life= new Life();
	Life enemyLife= new Life();
	private Image background;
	
	
	private void bindEvents(){
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){				
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					player.walk();
					player.setState(Player.WALK);
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT) {
					player.back();
				}
				if(e.getKeyCode()==KeyEvent.VK_SPACE){
					player.jump();
				}
				if(e.getKeyCode()==KeyEvent.VK_A){
					player.setState(Player.ATTACK);
				}
				if(e.getKeyCode()==KeyEvent.VK_D) {
					player.setState(Player.DASH);
				}
				if(e.getKeyCode()==KeyEvent.VK_J){
					enemy.walk();
					enemy.setState(Enemy.EWALK);
				}
				if(e.getKeyCode()==KeyEvent.VK_L) {
					enemy.back();
				}
				if(e.getKeyCode()==KeyEvent.VK_O) {
					enemy.setState(Enemy.EATTACK);
				}
				if(e.getKeyCode()==KeyEvent.VK_K) {
					enemy.setState(Enemy.EDASH);
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e){
				
			}
			});

	}
	
public Boolean isEnemyCollision(Player player, Enemy enemy) {
	Rectangle r1 =player.Collision();
	Rectangle r2 =enemy.Collision();
	if(r1.intersects(r2)) {
//		System.out.println("Collision occured");
		return true;
	}
	else {
//		System.out.println("Collision not occured...");
		return false;
	}
		
		
	}

		public void checkEnemyCollision(Graphics g) {
			
			if(isEnemyCollision(player,enemy)) {
				
		//		enemyLife.bossPuchEffect(g);
				
				if(Player.state==ATTACK) {
					
					enemyLife.decreaseEnemyLife();
				}
				
				if(player.state==DASH) {
					
					enemyLife.decreaseEnemyLife();
								
				}
				
				
				
			}
			
		}
	
	
	
	
	
	public void DrawGameOver(Graphics g) {
		
		g.setColor(Color.RED);
		g.setFont(new Font("Ariel",Font.BOLD,50));
		g.drawString("GAME OVER !!",GAME_WIDTH/2-100,GAME_HEIGHT/2);
	}

	
	public void drawWin(Graphics g) {
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("Ariel",Font.BOLD,50));
		g.drawString("YOU WIN !!", GAME_WIDTH/2-100, GAME_HEIGHT/2);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		player.fall();
		enemy.fall();
		
	}
	
	public void drawBackGround(Graphics g) {
		g.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
	}
	
	public void loadImages() {
		
		background = new ImageIcon(Board.class.getResource(BACKGROUND_IMAGE)).getImage();
	}
	
	public Board() {
		player = new Player();
		enemy = new Enemy();
//		Collision collision = new Collision();
		loadImages();
		setSize(GAME_WIDTH,GAME_HEIGHT);
		setFocusable(true);
		bindEvents();
		gameLoop();
	}
	
//	public boolean checkCollision() {
//		if 
//	}
	
	
	public boolean IsCollision(Player player,Enemy enemy) {
		Rectangle r1 =player.Collision();
		Rectangle r2 =enemy.Collision();
		if(r1.intersects(r2)) {
//			System.out.println("Collision occured");
			return true;
		}
		else {
//			System.out.println("Collision not occured...");
			return false;
		}
		
		}	
	
	public void checkCollision(Graphics g) {
		if(IsCollision(player,enemy)) {
			
			if(Player.state==ATTACK || Player.state==DASH) {
				life.decreaseEnemyLife();
				
			}
			
			else if(Enemy.state==EATTACK || Enemy.state==EDASH) {
				life.decreasePlayerLife();
			}
			
		}
	}
	
	
	public void gameLoop() {
		
		
		timer = new Timer(GAME_SPEED,this);
		timer.start();
		//checkCollision();
	}
	
	public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawBackGround(g);
//			System.out.println("Paint Call");
			player.drawPlayer(g);
			enemy.drawEnemy(g);
			checkCollision(g);
			life.drawPlayerLife(g);
			enemyLife.drawEnemyLife(g);
			checkEnemyCollision(g);
			
			
			if(life.w<=0) {
				
				player.diePlayer(g);
				player.moveDiePlayer();
				
				DrawGameOver(g);
				timer.stop();
				
				if(player.y>=GAME_HEIGHT) {
					
					
					timer.stop();
				}
			}
			
			if(enemyLife.w2<=0) {
				
				enemy.dieEnemy(g);
				enemy.moveDieEnemy();
				drawWin(g);
				timer.stop();
				
			}
	}
}

	