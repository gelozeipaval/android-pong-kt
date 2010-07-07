package app.pong;

import android.graphics.drawable.Drawable;

public class Paddle extends GameObject {
	int moveDir;
	int moveSpeed;

	public Paddle(int posX, int posY, int boundX, int boundY, int angle,
			Drawable image) {
		super(posX, posY, boundX, boundY, angle, image);
		// TODO Auto-generated constructor stub
		moveSpeed = 5;
		moveDir=NONE;
	}

	@Override
	void move() {
		switch(moveDir){
		case LEFT	:if(isAtBoundsX()!=LEFT)posX-=moveSpeed;break;
		case RIGHT	:if(isAtBoundsX()!=RIGHT)posX+=moveSpeed;break;
		case NONE	:break;
		}
	}
	void moveLeft(){
		moveDir = LEFT;
	}
	void moveRight(){
		moveDir=RIGHT;
	}
	void stopMotion(){
		moveDir=NONE;
	}

	public void rotateCCW() {
		// TODO Auto-generated method stub
		angle = -45;
		AndroidPong.debug("CCW");
	}

	public void rotateCW() {
		// TODO Auto-generated method stub
		angle = 45;
		AndroidPong.debug("CW");
	}

	public void resetRotation() {
		// TODO Auto-generated method stub
		angle = 0;
		AndroidPong.debug("N");
	}

}
