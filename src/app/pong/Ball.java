package app.pong;

import android.graphics.drawable.Drawable;

public class Ball extends GameObject {
	int velX,velY, pauseFrames=0;
	//int speedOptions[2]={0};

	public Ball(int posX, int posY, int boundX, int boundY,int angle, Drawable image) {
		super(posX, posY, boundX, boundY, angle, image);
		// TODO Auto-generated constructor stub
		reset();
	}
	
	void reset(){
		super.reset();
		pauseFrames = 25;
		velX =3;
		velY =3;		
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		if(pauseFrames!=0){
			pauseFrames--;
			return;
		}
			
		if(isAtBoundsX()!=NONE)
			velX=-velX;
		if(isAtBoundsY()!=NONE){
			reset();
			return;
		}
			//velY=-velY;
		
		posX+=velX;
		posY+=velY;
	}

}
