package com.mayank.StreetFighter;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Enemy implements GameConstants, EnemyState {
	
	public int x;
	public int y;
	public int w;
	public int h;
	Boolean isVisible;
	private int speed ;
	BufferedImage spriteSheet;
	private final int  GRAVITY  = 1;
	private int force ;
	private final int FLOOR = GAME_HEIGHT - 50;
	public static int state;
	
	
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	private BufferedImage attackImageArr [];
	private BufferedImage dashImageArr [];
	public Enemy(){
		loadSpriteSheet();
		defaultImageArr = defaultImage();
		attackImageArr = attackImage();
		dashImageArr = dashImage();
		
		x = 500;
		h = 300;
		w = 150;
		speed = 7;
		state = EWALK;
		y = FLOOR - h;
	}
	private boolean isJump;
	public void jump(){
		if(!isJump){
		force = -20;
		y = y + force;
		isJump = true;
		}
	}
	
	public void fall(){
		if(y<FLOOR-h){
			force = force + GRAVITY;
			y = y + force;
		}
		if(y>=FLOOR-h){
			isJump = false;
			y = FLOOR - h;
		}
	}
	public void walk(){
		x-=speed;
	}
	
	public void back() {
		x+=speed;
	}
	int walkPass = 0;
	int walkIndex = 0;
	BufferedImage defaultImageArr[] ;
	
	
	
	public void walkEffect(Graphics g){
	
		if(walkIndex>=defaultImageArr.length-1){
			walkIndex = 0;
			
		}
		else
		{
			g.drawImage(defaultImageArr[walkIndex], x, y, w, h, null);
			walkPass++;
			if(walkPass==6){
			
			walkIndex++;
			walkPass=0;
			}
			
			//GameUtils.delay(1);
		}
	}
	int attackIndex = 0;
	int attackPass = 0; 
	public void attackEffect(Graphics g){
		if(attackIndex>=attackImageArr.length-1){
			attackIndex = 0;
			walkIndex = 0;
			state = EWALK;
		}
		else
		{
			g.drawImage(attackImageArr[attackIndex], x, y, w, h, null);
			attackPass++;
			if(attackPass==4){
			
				attackIndex++;
				attackPass=0;
			}
			
			//GameUtils.delay(1);
		}
	}
	
	int dashIndex = 0;
	int dashPass = 0; 
	public void dashEffect(Graphics g){
		if(dashIndex>=dashImageArr.length-1){
			dashIndex = 0;
			walkIndex = 0;
			state = EWALK;
		}
		else
		{
			g.drawImage(dashImageArr[dashIndex], x, y, w, h, null);
			dashPass++;
			if(dashPass==4){
			
				dashIndex++;
				dashPass=0;
			}
			
			//GameUtils.delay(1);
		}
	}
	
	private void loadSpriteSheet(){
		try {
			spriteSheet = ImageIO.read(Player.class.getResource("enemy.gif"));
			System.out.println("Load Enemy Sprite Sheet...");


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] defaultImage(){
		BufferedImage array[] = new BufferedImage[7];
		array[0]= spriteSheet.getSubimage(466,83,57,77);
		array[1]= spriteSheet.getSubimage(413,80,55,79);
		array[2]= spriteSheet.getSubimage(302,82,51,74);
		array[3]= spriteSheet.getSubimage(254,83,47,73);
		array[4]= spriteSheet.getSubimage(183,81,70,75);
		array[5]= spriteSheet.getSubimage(112,68,72,91);
		array[6]= spriteSheet.getSubimage(112,68,72,91);
		return array;
	}
	
	public BufferedImage[] dashImage() {
		BufferedImage array[] = new BufferedImage[5];
		array[0] = spriteSheet.getSubimage(449,441,70,84);
		array[1] = spriteSheet.getSubimage(341,441,110,84);
		array[2] = spriteSheet.getSubimage(273,455,73,70);
		array[3] = spriteSheet.getSubimage(163,447,111,79);
		
		return array;
	}
	
	public BufferedImage[] attackImage(){
		BufferedImage array[] = new BufferedImage[4];
		array[0]= spriteSheet.getSubimage(445,730,76,87);
		array[1]= spriteSheet.getSubimage(91,733,75,80);
		array[2]= spriteSheet.getSubimage(368,732,73,81);
		array[3]= spriteSheet.getSubimage(310,734,56,80);		
		return array;
	}
	
	public Rectangle Collision() {
		Rectangle r2 = new Rectangle(x,y,w,h);
		return r2;
	}
	
public void dieEnemy(Graphics g) {
		
		BufferedImage img[]= new BufferedImage[1];
		
		img[0]=spriteSheet.getSubimage(466,83,57,77);
		
		g.drawImage(img[0],x,y,w,h,null);
	}
	
	public void moveDieEnemy() {
		
		
		x+=speed;
	}
	
	public void drawEnemy(Graphics g){
		//g.drawImage(defaultImage(), x, y, w, h, null);
		if(state==EWALK  ){
			walkEffect(g);
		}
		else
		if(state==EATTACK){	
			attackEffect(g);
		}
		else
			if(state==EDASH) {
				dashEffect(g);
			}
	}

}
