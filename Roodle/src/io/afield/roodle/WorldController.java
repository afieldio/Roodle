package io.afield.roodle;

import io.afield.roodle.Assets.Assets;
import io.afield.roodle.GameObjects.AbstractGameObject;
import io.afield.roodle.GameObjects.Background;
import io.afield.roodle.GameObjects.Heart;
import io.afield.roodle.GameObjects.Obstacle;
import io.afield.roodle.GameObjects.Roodle;
import io.afield.roodle.GameObjects.Star;
import io.afield.roodle.Screens.EndScreen;
import io.afield.roodle.Screens.MenuScreen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;

public class WorldController extends InputAdapter{
	private static final String TAG = WorldController.class.getName();

	public int gameSpeed;
	public CameraHelper cameraHelper;
	public TextureAtlas atlas;
	public int lives;


	//used for spawning objects
	public Timer timer, starTimer, obstTimer, gameTimer;

	public Background bG1, bG2, bG3;
	public Stage stage;

	public String stringScore, stringUser;

	//todo
	public Array<Background> backgrounds;
	public Roodle rP;
	public Heart heart;
	public Array<Heart> hearts;
	public Star star;
	public Array<Star> stars;
	public Vector2 pos;
	public Array<Obstacle> obsts;
	public Obstacle obst;


	//Gui Stuff
	public TextButton leftBtn, fireBtn, rightBtn;
	public TextButtonStyle btnStyle;
	public Skin skin;
	public TextureRegion buttonUp, btn_down;

	//collision detection
	public Rectangle r1, r2;

	public Preferences prefs;

	private ProgressBar pBar;


	//Http Stuff!
	public HttpRequest httpRequest;
	String url, httpMethod, requestContent = null;





	private Game game;

	public WorldController(Game game){
		this.game = game;
		init();
	}

	private void init(){
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);

		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		initLevel();
		hearts = new Array<Heart>();
		stars = new Array<Star>();
		obsts = new Array<Obstacle>();


		/*
		 * Also need to create obstacles and enemies
		 */

		//to do...need to loop the backgrounds in an array rather than positioning them as they are now
		backgrounds = new Array<Background>();


	}


	private void initLevel(){
		gameSpeed = 100;



		bG1 = new Background();
		bG2 = new Background();
		bG3 = new Background();

		bG1.position.x = -Constants.VIEWPORT_WIDTH/2;
		bG1.position.y = -Constants.VIEWPORT_HEIGHT/2;
		bG1.dimension.x = Constants.VIEWPORT_WIDTH;
		bG1.dimension.y = Constants.VIEWPORT_HEIGHT;

		bG2.position.x = -Constants.VIEWPORT_WIDTH/2;
		bG2.position.y = bG1.position.y + bG1.dimension.y;
		bG2.dimension.x = Constants.VIEWPORT_WIDTH;
		bG2.dimension.y = Constants.VIEWPORT_HEIGHT;

		bG3.position.x = -Constants.VIEWPORT_WIDTH/2;
		bG3.position.y = bG2.position.y+ bG2.dimension.y;
		bG3.dimension.x = Constants.VIEWPORT_WIDTH;
		bG3.dimension.y = Constants.VIEWPORT_HEIGHT;

		rP = new Roodle();
		rP.setScore(0);
		rP.position.x = -rP.dimension.x/2;
		rP.position.y = -Constants.VIEWPORT_HEIGHT/3;

		timer = new Timer(5.0f);
		starTimer = new Timer(1.0f);
		obstTimer = new Timer(0.5f);
		gameTimer = new Timer(1.0f);


		createGameControls();

	}



	public void update(float deltaTime){

		handleDebugInput(deltaTime);
		handleRoodle(deltaTime);
		scroll(deltaTime);
		testCollisions();

		handleTimer(deltaTime);
		handleHearts(deltaTime);
		handleStars(deltaTime);
		handleObstacles(deltaTime);

		if(isGameOver()){
			EndMenu();
		}

	}






	public void handleTimer(float deltaTime){
		timer.update(deltaTime);
		if(timer.hasTimeElapsed()){
			timer.reset();
		}

		starTimer.update(deltaTime);
		if(starTimer.hasTimeElapsed()){
			starTimer.reset();
		}

		obstTimer.update(deltaTime);
		if(obstTimer.hasTimeElapsed()){
			obstTimer.reset();
		}
		
		gameTimer.update(deltaTime);
		if(gameTimer.hasTimeElapsed()){
			gameTimer.reset();
			gameSpeed+=1;
		}

	}

	private boolean isGameOver(){
		if(lives < 1){

			//sendScore();

			return true;
		}
		return false;
	}

//	 Sends the user to the end screen sending the score and the user
//	The user is not currently active but could be included
	
	
	private void EndMenu() {
		game.setScreen(new EndScreen(game, rP.getScore()));

	}
	
	/*
	 * Practicing the http transfer from here initially...this seemed to work but I wanted to
	 * send it on the press of the submit. This way it would also allow a text input to send
	 * the users name. I tried the screne2d textfield widgets but failed to get them working
	 * properly. 
	 * 
	 * 
	 * NEED TO IMPLEMENT HttpResponseListener to get this working.
	 * 
	 */

//	private void sendScore(){
//		
//		stringScore = Integer.toString(rP.getScore());
//		stringUser = "AdamTest";
//
//		url = "http://localhost:8888/?page=insertScore&score="+stringScore+"&user="+stringUser;
//		
//
//		httpMethod = Net.HttpMethods.GET;
//
//		httpRequest = new HttpRequest(httpMethod);
//		httpRequest.setUrl(url);
//		httpRequest.setContent(stringScore);
//		httpRequest.setContent(requestContent);
//		
//		System.out.println(httpRequest.getContent());
//		
//		
//		Gdx.net.sendHttpRequest(httpRequest, WorldController.this);
//	}
//
//	@Override
//	public void handleHttpResponse(HttpResponse httpResponse) {
//		
//		final int statusCode = httpResponse.getStatus().getStatusCode();
//		
//		System.out.println("http respons code: " + statusCode);
//		if (statusCode != 200) {
//			game.setScreen(new EndScreen(game, rP.getScore()));
//		}
//		
//	}
//
//	@Override
//	public void failed(Throwable t) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void cancelled() {
//		// TODO Auto-generated method stub
//
//	}




	/*
	 * Game controls - Not used at the moment....
	 * 
	 */

	private void createGameControls() {
		if(stage == null)
			stage = new Stage();
		stage.clear();

		btnStyle = new TextButtonStyle();
		skin = new Skin();

		btnStyle.font = Assets.instance.fonts.theFont;


		leftBtn = new TextButton("<<", btnStyle);

		leftBtn.addListener(new InputListener(){

		});
		fireBtn = new TextButton("fire", btnStyle);
		fireBtn.center();
		fireBtn.setWidth(Constants.VIEWPORT_GUI_WIDTH/3); 

		rightBtn = new TextButton(">>", btnStyle);
		rightBtn.center();
		rightBtn.setWidth(Constants.VIEWPORT_GUI_WIDTH/3); 


		stage.addActor(leftBtn);
		stage.addActor(fireBtn);
		stage.addActor(rightBtn);

	}

	/*
	 * 
	 * Objects and Handlers
	 * 
	 * Need to try and get this working to minimize the code required.
	 * Could potentially make a spawning class to handle this as well
	 * 
	 * 
	 * 
	 */


	/*
	 * Common Functions
	 */

	public AbstractGameObject spawnObject(AbstractGameObject obj) {return obj;}



	public Vector2 positionObject(){
		Vector2 pos = new Vector2();
		pos.x = MathUtils.random(-2.5f, 2.5f);
		pos.y = Constants.VIEWPORT_HEIGHT+ 0.5f;

		return pos;
	}




	/*
	 * Hearts
	 */
	public void handleHearts(float deltaTime){
		float secs = 1; 

		if(timer.remaining < secs){
			spawnHeart();

			float randomFloat = MathUtils.random(0, 15);

			timer.reset(randomFloat);

		}


		for(Heart heart : hearts){
			if(heart.position.x > -Constants.VIEWPORT_HEIGHT){
				heart.velocity.y = deltaTime*-gameSpeed;
			}else if (heart.position.x < -Constants.VIEWPORT_HEIGHT){
				hearts.removeValue(heart, false);
			}
			heart.update(deltaTime);
		}
	}

	public Heart spawnHeart(){


		Heart heart = new Heart();
		pos = positionObject();
		heart.position.set(pos);

		//		if(timer.remaining == MathUtils.random(0, 1)*Gdx.graphics.getDeltaTime()){
		hearts.add(heart);
		//System.out.println("Heart");
		//		}

		return heart;
	}

	/*
	 * Stars
	 */

	public void handleStars(float deltaTime){

		float secs = 1;

		if(starTimer.remaining <= secs){
			spawnStar();

			System.err.println("star");

			float randomFloat = MathUtils.random(0, 5);

			starTimer.reset(randomFloat);

		}

		for(Star star : stars){
			if(star.position.x > -Constants.VIEWPORT_HEIGHT){
				star.velocity.y = deltaTime*-gameSpeed;
			}else if (star.position.x < -Constants.VIEWPORT_HEIGHT){
				stars.removeValue(star, false);
			}
			star.update(deltaTime);
		}
	}


	public Star spawnStar(){
		Star star = new Star();
		pos = positionObject();
		star.position.set(pos);
		stars.add(star);
		return star;
	}


	/*
	 * Obstacles
	 */
	
	public void handleObstacles(float deltaTime){
	
		float secs = 0.5f;
	
		if(obstTimer.remaining <= secs){
			spawnObstacle();
			System.err.println("obstacle");
	
			float randomFloat = MathUtils.random(1, 1);
	
			obstTimer.reset(randomFloat);
		}
	
		for(Obstacle obst : obsts){
			if(obst.position.x > -Constants.VIEWPORT_HEIGHT){
				obst.velocity.y = deltaTime*-gameSpeed;
			}else if (obst.position.x < -Constants.VIEWPORT_HEIGHT){
				obsts.removeValue(obst, false);
			}
			obst.update(deltaTime);
		}
	}

	public Obstacle spawnObstacle(){
		Obstacle obst = new Obstacle();
		pos = positionObject();
		obst.position.set(pos);
		obsts.add(obst);
		return obst;
	}





	/*
	 * Background
	 */

	private void scroll (float deltaTime){
		moveBackground(deltaTime);
	}


	private void moveBackground(float deltaTime) {
		bG1.velocity.y = deltaTime*-gameSpeed;
		bG2.velocity.y = deltaTime*-gameSpeed;
		bG3.velocity.y = deltaTime*-gameSpeed;



		if(bG1.position.y < -Constants.VIEWPORT_HEIGHT*1.5){
			//bG1.velocity.y = deltaTime*speed;
			bG1.position.y += 3* bG1.dimension.y;
			//System.out.println("BG1 Y: " + bG1.position.y);
		}

		if(bG2.position.y < -Constants.VIEWPORT_HEIGHT*1.5){
			//bG1.velocity.y = deltaTime*speed;
			bG2.position.y += 3* bG2.dimension.y;
			//System.out.println("BG2 Y: " + bG2.position.y);
		}

		if(bG3.position.y < -Constants.VIEWPORT_HEIGHT*1.5){
			//bG1.velocity.y = deltaTime*speed;
			bG3.position.y += 3* bG3.dimension.y;
			//System.out.println("BG3 Y: " + bG3.position.y);
		}

		bG1.update(deltaTime);
		bG2.update(deltaTime);
		bG3.update(deltaTime);



	}

	private void handleRoodle(float deltaTime){

		//if (Gdx.app.getType() != ApplicationType.Desktop) return;
		checkRoodlePosition();

		if(Gdx.input.isKeyPressed(Keys.A)){
			moveRoodle(-20, 0, deltaTime);
			rP.rotation=45;
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			moveRoodle(20, 0, deltaTime);
			rP.rotation=-45;
		}else{
			rP.rotation = 0;
		}


		if(Gdx.app.getType() == ApplicationType.Android ){
			if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
				float accelX = -Gdx.input.getAccelerometerX();

				float roodleSpeed = 20.0f;
				//This could potentially be used but for the time being i just want left and right
				//float accelY = -Gdx.input.getAccelerometerY();
				moveRoodle((accelX * roodleSpeed), 0, deltaTime);
			}
		}

		//boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
	}

	private void checkRoodlePosition(){
		if(rP.bounds.x > stage.getWidth()){

		}
	}

	private void moveRoodle(float x, float y, float deltaTime){

		if(x<0){
			rP.velocity.x = deltaTime*10*x;
			rP.velocity.y = deltaTime*10*y;

			//System.out.println("Key press: " + Keys.A);
		}else if(x>0){
			rP.velocity.x = deltaTime*10*x;
			rP.velocity.y = deltaTime*10*y;
		}

		rP.update(deltaTime);
	}

	private void testCollisions(){
		r1 = new Rectangle();
		r2 = new Rectangle();

		r1.set(rP.position.x, rP.position.y, rP.bounds.width, rP.bounds.height);

		for( Heart heart : hearts){
			r2.set(heart.position.x, heart.position.y, heart.bounds.width, heart.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionWithHeart(heart);
		}

		for ( Star star : stars){
			r2.set(star.position.x, star.position.y, star.bounds.width, star.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionWithStar(star);
		}

		for (Obstacle obst : obsts){
			r2.set(obst.position.x, obst.position.y, obst.bounds.width, obst.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionWithObst(obst);
		}
	}

	private void onCollisionWithObst(Obstacle obst){
		obsts.removeValue(obst, true);
		lives -= 1;
		System.out.println("death");
	}

	private void onCollisionWithHeart(Heart heart){
		hearts.removeValue(heart, true);
		rP.setScore(heart.getScore());
		System.out.println("Heart collision");
	}

	private void onCollisionWithStar(Star star){
		stars.removeValue(star, true);
		rP.setScore(star.getScore());
		System.out.println("Star collision");
	}







	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;


		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
				camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
				0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
				0);
		if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
				-camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			cameraHelper.setPosition(0, 0);
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=
				camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(
				-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}

	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}



	@Override
	public boolean keyUp(int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}else if(keycode == Keys.ESCAPE || keycode == Keys.BACK){
			game.setScreen(new MenuScreen(game));;
		}

		return false;
	}





}
