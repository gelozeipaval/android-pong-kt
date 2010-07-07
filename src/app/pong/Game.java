package app.pong;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class Game extends SurfaceView implements Runnable, Callback {

	private SurfaceHolder mSurfaceHolder;
	private boolean mRun;
	private Ball ball;
	private Thread t;
	private int fps,timePerFrame;
	private Bitmap mBackgroundImage;
	private Resources res;
	private int mCanvasWidth;
	private int mCanvasHeight;
	static public Paddle player,opponent;
	static final int padding = 30;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode){
		case KeyEvent.KEYCODE_VOLUME_DOWN:	player.rotateCCW();break;
		case KeyEvent.KEYCODE_VOLUME_UP:	player.rotateCW();break;
		default:AndroidPong.debug("Not a key we care about"+keyCode);return false;
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN||keyCode==KeyEvent.KEYCODE_VOLUME_UP ){
			player.resetRotation();
			return true;
		}
		return false;
	}

	public Game(Context context, AttributeSet attrs) {
		super(context, attrs);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
        mRun = true;
        setFocusable(true); // make sure we get key events
        t = new Thread(this);
        fps = 25;
        timePerFrame = 1000/fps;
        res = context.getResources();
        mBackgroundImage = BitmapFactory.decodeResource(res,R.drawable.pongtable);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis(),timeLeft;
		while (mRun) {
			//implement fixed fps
			start = System.currentTimeMillis();
			timedRun();
			timeLeft=start+timePerFrame - System.currentTimeMillis();
			try {
				if(timeLeft>0)
					Thread.sleep(timeLeft);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void timedRun(){		
        Canvas c = null;
        try {
            c = mSurfaceHolder.lockCanvas(null);
            synchronized (mSurfaceHolder) {
                //if (mMode == STATE_RUNNING) updatePhysics();
            	//AI think
            	collisionDetection();
            	player.move();
            	opponent.move();
            	ball.move();
                doDraw(c);
            }
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (c != null) {
                mSurfaceHolder.unlockCanvasAndPost(c);
            }
        }        		
	}

	private void collisionDetection() {
		// TODO Auto-generated method stub
		if(collideAABB(ball,player)&&ball.velY>0)	ball.velY=-ball.velY;
		if(collideAABB(ball,opponent)&&ball.velY<0)	ball.velY=-ball.velY;
		
	}

	private boolean collideAABB(Ball one, Paddle two) {
		// TODO Auto-generated method stub
		int point3[]={two.getPosX()-two.getWidth()/2,two.getPosY()-two.getHeight()/2};
		int point4[]={two.getPosX()+two.getWidth()/2,two.getPosY()+two.getHeight()/2};
		float ballPt[]={one.getPosX(),one.getPosY()};
		
		if(two.angle!=0){
			Matrix m=new Matrix ();
			m.setRotate(-two.getAngle(), two.getPosX(), two.getPosY());
			m.mapPoints(ballPt);
		}

		int point1[]={(int) (ballPt[0]-one.getWidth()/2),(int) (ballPt[1]-one.getHeight()/2)};
		int point2[]={(int) (ballPt[0]+one.getWidth()/2),(int) (ballPt[1]+one.getHeight()/2)};
		
		if((point4[1]>point2[1]&&point2[1]>point3[1])||(point3[1]<point1[1]&&point1[1]<point4[1]))
			if((point4[0]>point2[0]&&point2[0]>point3[0])||(point3[0]<point1[0]&&point1[0]<point4[0]))
				return true;
		return false;
	}

	private void doDraw(Canvas c) {
		// TODO Auto-generated method stub
		c.drawBitmap(mBackgroundImage, 0, 0, null);
		player.draw(c);
		opponent.draw(c);
		ball.draw(c);		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		 synchronized (mSurfaceHolder) {
             mCanvasWidth = width;
             mCanvasHeight = height;

             // don't forget to resize the background image
             mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage, width, height, true);
         }
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		start();
		AndroidPong.debug("Created");
	}

	private void start() {
		// TODO Auto-generated method stub
		mCanvasWidth = getWidth();
        mCanvasHeight = getHeight();
        ball = new Ball(mCanvasWidth/2, mCanvasHeight/2,mCanvasWidth,mCanvasHeight, 0, res.getDrawable(R.drawable.pongball));
        player=new Paddle(mCanvasWidth/2, mCanvasHeight-padding,mCanvasWidth,mCanvasHeight, 0, res.getDrawable(R.drawable.paddle));
        opponent=new Paddle(mCanvasWidth/2, padding,mCanvasWidth,mCanvasHeight, 0, res.getDrawable(R.drawable.paddle));
        t.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mRun = false;
	}

}
