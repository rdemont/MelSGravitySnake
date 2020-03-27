package ch.rmbi.melsgravitysnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameBoard extends View implements SensorEventListener {

    private Snake snake;
    private Foods foods;
    private long lastMove ;
    private int speed ;
    private static int MAXSPEED = 500;
    private boolean isStarting = false ;
    private TextView tvScore ;
    private int score = 0 ;
    private Button bStart ;
    private int maxX;
    private int maxY;
    //private int lastDirection = Snake.MOVE_TOP ;
    //private int nextDirection ;

    private int accuracy = 2;

    private static final int SHAKE_THRESHOLD = 600;

    public GameBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GameBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public GameBoard(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context)
    {
        this.lastMove = System.currentTimeMillis();


    }

    private void placeGame(Canvas canvas)
    {

        if (!isStarting) {
            int x = this.getWidth() / 2;
            int y = this.getHeight() / 2;

            this.speed = MAXSPEED;
            this.score =0 ;
            foods = new Foods(10);
            snake = new Snake();
            snake.initSnake(x, y, this.getWidth(), this.getHeight());
            snake.addPart();
            snake.addPart();
            snake.addPart();
            snake.addPart();
            snake.addPart();
            snake.addPart();
            snake.addPart();
            snake.addPart();
        }
        //this.invalidate();
    }
    public void setTextView(TextView tv)
    {
        tvScore = tv ;
    }

    public void setButton(Button b)
    {
        bStart = b ;
    }

    public boolean getIsStarting()
    {
        return isStarting;
    }

    public void startGame()
    {
        isStarting = true ;
        bStart.setText("Stop");
    }

    public void stopGame()
    {
        isStarting = false ;
        bStart.setText("Pause");
    }
    private void gameLost()
    {
        stopGame();
        bStart.setText("new start");
    }

    public void moveSnake()
    {
        snake.moveSnake();
        int snakeX = snake.currentX();
        int snakeY =  snake.currentY();

        if (Helper.instance().isClose(snakeX,0,21)
                ||Helper.instance().isClose(snakeY,0,21)
                ||Helper.instance().isClose(snakeX,this.getWidth(),21)
                ||Helper.instance().isClose(snakeY,this.getHeight(),21)){

            this.gameLost();
            //tvScore.setText(tvScore.getText()+"--PERDU--");

        }

        if(foods.hasFood(snakeX, snakeY))
        {
            foods.removeFood(snakeX, snakeY);
            snake.addPart();
            score++;

        }

        tvScore.setText(" Score: "+String.valueOf(score)+" / Step:" + String.valueOf(MAXSPEED-speed));
        this.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x =  event.values[0];
        float y =  event.values[1];
        float z =  event.values[2];

        if (isStarting) {
            if (lastMove + speed < System.currentTimeMillis()) {
                if (y < 0 - this.accuracy)
                    snake.setNextMove(Snake.MOVE_TOP);
                if (y > 0 + this.accuracy)
                    snake.setNextMove(Snake.MOVE_BOTTOM);
                if (x < 0 - this.accuracy)
                    snake.setNextMove(Snake.MOVE_RIGHT);
                if (x > 0 + this.accuracy)
                    snake.setNextMove(Snake.MOVE_LEFT);


                lastMove = System.currentTimeMillis();

                moveSnake();

                speed--;
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        placeGame(canvas);

        snake.drawSnake(canvas);
        foods.drawFoods(canvas);
        //canvas.drawCircle(this.currentX,this.currentY,this.radius,pSnakePart);
    }
}
