package app.pong;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public abstract class GameObject {
	int posX,posY,angle,height,width,boundX,boundY;
	int defPosX,defPosY,defAngle;
	Drawable image;
	
	protected static final int LEFT = -1;
	protected static final int RIGHT = 1;
	protected static final int NONE = 0;
	protected static final int TOP = -2;
	protected static final int BOTTOM = 2;
	
	public GameObject(int posX, int posY,int boundX, int boundY, int angle,Drawable image) {
		super();
		this.defPosX = this.posX = posX;
		this.defPosY = this.posY = posY;
		this.defAngle = this.angle = angle;
		this.image = image;
		this.height = image.getIntrinsicHeight();
		this.width = image.getIntrinsicWidth();
		this.boundX = boundX;
		this.boundY = boundY;
		AndroidPong.debug("Construct" + posX + posY);
	}	
	void reset(){
		setPosX(defPosX);
		setPosY(defPosY);
		setAngle(defAngle);		
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	abstract void move();
	void draw(Canvas canvas){
		canvas.save();
		canvas.translate(posX, posY);
		canvas.rotate(angle);
		image.setBounds(-width/2, -height/2, +width/2, +height/2);
		image.draw(canvas);
		canvas.restore();
	};	
	
	int isAtBoundsX(){
		if(posX-width/2<=0)
			return LEFT;
		if(posX+width/2>=boundX)
			return RIGHT;
		return NONE;
	}
	
	int isAtBoundsY(){
		if(posY-height/2<=0)
			return TOP;
		if(posY+height/2>=boundY)
			return BOTTOM;
		return NONE;
	}
}
