package app.pong;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class AndroidPong extends Activity implements OnClickListener{
	static String tag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tag =getResources().getString(R.string.app_name);
		fullScreen();
		splashScreen();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	void startGame(){
		setContentView(R.layout.game);
		this.findViewById(R.id.Button01).setOnClickListener(this);
		this.findViewById(R.id.Button02).setOnClickListener(this);
	}
	
	void splashScreen(){
		setContentView(R.layout.splash);
		this.findViewById(R.id.ImageView01).setOnClickListener(this);		
	}
	
	void gameMenu(){
		
	}
	
	void fullScreen(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN );
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Button01:		Game.player.moveLeft();break;
		case R.id.Button02:		Game.player.moveRight();break;
		case R.id.ImageView01:	startGame();break;
		default: 
		}
	}	
	public static void debug(String debug){
		Log.d(tag,debug);
	}
}
