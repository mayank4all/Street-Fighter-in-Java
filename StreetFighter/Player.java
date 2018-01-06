package com.mayank.StreetFighter;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player implements GameConstants, State {
	
	public int x;
	public int y;
	public int w;
	public int h;
	Boolean isVisible;
	private int speed ;
	BufferedImage spriteSheet;
	BufferedImage spriteSheet2;
	BufferedImage spriteSheet3;
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
	private BufferedImage attackImageArr [];
	private BufferedImage dashImageArr [];
	public Player(){
		loadSpriteSheet();
		defaultImageArr = defaultImage();
		attackImageArr = attackImage();
		dashImageArr = dashImage();
		
		x = 100;
		h = 300;
		w = 150;
		speed = 7;
		state = WALK;
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
	
//	public Boolean getIsVisible() {
//		return isVisible;
//	}
//	public void setIsVisible(Boolean isVisible) {
//		this.isVisible = isVisible;
//	}
	
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
		x+=speed;
	}
	
	public void back() {
		x-=speed;
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
			state = WALK;
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
			state = WALK;
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
			spriteSheet = ImageIO.read(Player.class.getResource("Samurai_Walk.png"));
			System.out.println("Load Sprite Sheet...");
			spriteSheet2 = ImageIO.read(Player.class.getResource("Samurai2.png"));
			System.out.println("Load Sprite Sheet 2...");
			spriteSheet3 = ImageIO.read(Player.class.getResource("Samurai.png"));
			System.out.println("Load Sprite Sheet 3...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] defaultImage(){
		BufferedImage array[] = new BufferedImage[14];
		array[0]= spriteSheet.getSubimage(2,43,176,305);
		array[1]= spriteSheet.getSubimage(189,42,173,308);
		array[2]= spriteSheet.getSubimage(375,43,175,309);
		array[3]= spriteSheet.getSubimage(560,44,176,309);
		array[4]= spriteSheet.getSubimage(744,44,180,306);
		array[5]= spriteSheet.getSubimage(4,393,171,305);
		array[6]= spriteSheet.getSubimage(180,393,171,305);
		array[7]= spriteSheet.getSubimage(180,393,171,305);
		array[8]= spriteSheet.getSubimage(4,393,171,305);
		array[9]= spriteSheet.getSubimage(744,44,180,306);
		array[10]= spriteSheet.getSubimage(560,44,176,309);
		array[11]= spriteSheet.getSubimage(375,43,175,309);
		array[12]= spriteSheet.getSubimage(189,42,173,308);
		array[13]= spriteSheet.getSubimage(2,43,176,305);
		return array;
	}
	
	public BufferedImage[] dashImage() {
		BufferedImage array[] = new BufferedImage[10];
		array[0] = spriteSheet3.getSubimage(235,122,378,233);
		array[1] = spriteSheet3.getSubimage(621,122,510,230);
		array[2] = spriteSheet3.getSubimage(1146, 123, 500, 207);
		array[3] = spriteSheet3.getSubimage(7,461,472,237);
		array[4] = spriteSheet3.getSubimage(494, 465, 508, 232);
		array[5] = spriteSheet3.getSubimage(494, 465, 508, 232);
		array[6] = spriteSheet3.getSubimage(7,461,472,237);
		array[7] = spriteSheet3.getSubimage(1146, 123, 500, 207);
		array[8] = spriteSheet3.getSubimage(621,122,510,230);
		array[9] = spriteSheet3.getSubimage(235,122,378,233);

		return array;
	}
	
	public BufferedImage[] attackImage(){
		BufferedImage array[] = new BufferedImage[7];
		array[0]= spriteSheet2.getSubimage(3,5,145,388);
		array[1]= spriteSheet2.getSubimage(155,123,254,274);
		array[2]= spriteSheet2.getSubimage(408,129,248,271);
		array[3]= spriteSheet2.getSubimage(671,131,242,263);
		array[4]= spriteSheet2.getSubimage(671,131,242,263);
		array[5]= spriteSheet2.getSubimage(408,129,248,271);
		array[6]= spriteSheet2.getSubimage(3,5,145,388);

		return array;
	}
	
	public Rectangle Collision() {
		Rectangle r1 =  new Rectangle(x,y,w,h);
		return r1;
	}
	
	
public void diePlayer(Graphics g) {
		
		
		BufferedImage img[]= new BufferedImage[1];
		
		img[0]=spriteSheet.getSubimage(2,43,176,305);
		g.drawImage(img[0],x,y,w,h,null);
	}
	
	public void moveDiePlayer() {
		y+=speed;
	}
	
	public void drawPlayer(Graphics g){
		//g.drawImage(defaultImage(), x, y, w, h, null);
		if(state==WALK  ){
			walkEffect(g);
		}
		else
		if(state==ATTACK){	
			attackEffect(g);
		}
		else
			if(state==DASH) {
				dashEffect(g);
			}
	}

}