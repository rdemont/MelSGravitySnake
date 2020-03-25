package ch.rmbi.melsgravitysnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;

public class GameBoard extends View implements SensorEventListener {

    private Snake snake;
    private Foods foods;
    private long lastMove ;
    //private int lastDirection = Snake.MOVE_TOP ;
    //private int nextDirection ;

    private int accuracy = 2;

    private static final int SHAKE_THRESHOLD = 600;


    public GameBoard(Context context) {
        super(context);



        this.lastMove = System.currentTimeMillis();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        reStart();

    }

    private void reStart(){

        foods = new Foods(10);

        int x = getWidth()  / 2;
        int y = getHeight() / 2;
        snake = new Snake(x,y,getWidth(),getHeight());
        snake.addPart();
        snake.addPart();
        snake.addPart();
        snake.addPart();
        snake.addPart();
        snake.addPart();
        snake.addPart();
        snake.addPart();

    }
    public void moveSnake()
    {
        if (snake==null)
            snake = new Snake(getWidth()/2,0);


        snake.moveSnake();
        int snakeX = snake.currentX();
        int snakeY =  snake.currentY();

        if (Helper.instance().isClose(snakeX,0,21)
                ||Helper.instance().isClose(snakeY,0,21)
                ||Helper.instance().isClose(snakeX,getWidth(),21)
                ||Helper.instance().isClose(snakeY,getHeight(),21)){

            reStart();

        }

        if(foods.hasFood(snakeX, snakeY))
        {
            foods.removeFood(snakeX, snakeY);
            snake.addPart();
        }


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



        if (lastMove + 1000 < System.currentTimeMillis()) {
            if (y < 0-this.accuracy )
                snake.setNextMove(Snake.MOVE_TOP);
            if (y > 0+this.accuracy )
                snake.setNextMove(Snake.MOVE_BOTTOM);
            if (x < 0-this.accuracy )
                snake.setNextMove(Snake.MOVE_RIGHT);
            if (x > 0+this.accuracy )
                snake.setNextMove(Snake.MOVE_LEFT);


            lastMove = System.currentTimeMillis();

            moveSnake();

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();


        snake.drawSnake(canvas);
        foods.drawFoods(canvas);
        //canvas.drawCircle(this.currentX,this.currentY,this.radius,pSnakePart);
    }
}
