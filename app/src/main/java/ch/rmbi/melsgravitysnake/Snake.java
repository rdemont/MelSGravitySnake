package ch.rmbi.melsgravitysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;

public class Snake {

    private List<SnakePart> snakePart = new LinkedList<>();
    public static final int MOVE_TOP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_BOTTOM = 4;

    private int nextMove = this.MOVE_TOP;
    private int maxX =0;
    private int maxY =0;

    public Snake()
    {

    }

    public void initSnake(int x, int y,int maxX, int maxY){
        snakePart.add(new SnakePart(x,y));
        snakePart.get(0).setHead();
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void drawSnake(Canvas canvas)
    {
        for(SnakePart c : snakePart){
            c.drawSnakePart(canvas);
            //if(c.x==x && c.y==y) return false;
        }
    }

    public int currentX()
    {
        return snakePart.get(0).currentX;
    }
    public int currentY()
    {
        return snakePart.get(0).currentY;
    }

    public void setNextMove(int move)
    {
        if (isPossibleMoveOption(move))
        {
            this.nextMove = move ;
        }
    }
    public boolean  isPossibleMoveOption(int move)
    {
        switch (move){
            case Snake.MOVE_TOP :
                return this.nextMove != Snake.MOVE_BOTTOM;
            case Snake.MOVE_LEFT:
                return this.nextMove != Snake.MOVE_RIGHT;
            case Snake.MOVE_RIGHT :
                return this.nextMove != Snake.MOVE_LEFT;
            case Snake.MOVE_BOTTOM:
                return this.nextMove != Snake.MOVE_TOP;
        }
        return false ;
    }

    public void moveSnake()
    {
        SnakePart sFirst =  snakePart.get(0);
        SnakePart sLast = snakePart.get(snakePart.size()-1);

        switch (this.nextMove) {
            case MOVE_TOP :
                if (sFirst.currentY-(sFirst.radius*2) > 0)
                {
                    sLast.currentY = sFirst.currentY;
                    sLast.currentX = sFirst.currentX;
                    sFirst.currentY = sFirst.currentY-(sFirst.radius*2);
                    snakePart.remove(sLast);
                    snakePart.add(1,sLast);
                }
                break;
            case MOVE_LEFT :
                if (sFirst.currentX-(sFirst.radius*2) > 0)
                {
                    sLast.currentY = sFirst.currentY;
                    sLast.currentX = sFirst.currentX;
                    sFirst.currentX = sFirst.currentX-(sFirst.radius*2);
                    snakePart.remove(sLast);
                    snakePart.add(1,sLast);
                }
                break;
            case MOVE_RIGHT :
                if (sFirst.currentX+(sFirst.radius*2) < maxX)
                {
                    sLast.currentY = sFirst.currentY;
                    sLast.currentX = sFirst.currentX;
                    sFirst.currentX = sFirst.currentX+(sFirst.radius*2);
                    snakePart.remove(sLast);
                    snakePart.add(1,sLast);
                }
                break;
            case MOVE_BOTTOM :
                if (sFirst.currentY+(sFirst.radius*2) < maxY)
                {
                    sLast.currentY = sFirst.currentY;
                    sLast.currentX = sFirst.currentX;
                    sFirst.currentY = sFirst.currentY+(sFirst.radius*2);
                    snakePart.remove(sLast);
                    snakePart.add(1,sLast);
                }
                break;
        }

    }

    public void addPart()
    {
        SnakePart sLast = snakePart.get(snakePart.size()-1);

        int x = sLast.currentX;
        int y = sLast.currentY;
        int radius = sLast.radius;

        snakePart.add(new SnakePart(x,y+(radius*2)));
    }


    public class SnakePart{

        private int currentX;
        private int currentY;
        private int imageWidth;
        private int imageHeight;
        private Paint pSnakePart;
        private int radius;




        public SnakePart(int x, int y)
        {

            pSnakePart = new Paint();
            pSnakePart.setColor(Color.GREEN);
            pSnakePart.setStyle(Paint.Style.STROKE);
            pSnakePart.setStrokeWidth(10);

            this.radius = 10;
            this.imageWidth = 10;
            this.imageHeight = 10;

            this.currentX = x;
            this.currentY = y;
        }

        public void setHead()
        {
            pSnakePart.setColor(Color.RED);
            pSnakePart.setStyle(Paint.Style.FILL_AND_STROKE);

        }


        public void  drawSnakePart(Canvas canvas)
        {
            canvas.drawCircle(this.currentX,this.currentY,this.radius,pSnakePart);
        }
    }


}
