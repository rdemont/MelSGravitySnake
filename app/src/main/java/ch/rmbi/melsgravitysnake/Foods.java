package ch.rmbi.melsgravitysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Foods {
    private List<Food> foodList = new LinkedList<>();
    private int maxX = 0;
    private int maxY = 0;
    private int maxFood = 0;
    private int accuracy = 20;


    public Foods(int amount)
    {
        this.maxFood = amount;

    }
    public boolean hasFood(int x, int y)
    {

        for(Food c : foodList){
            if (isClose(x,y,c.currentX,c.currentY))
                return true;
        }
        return false;
    }

    private boolean isClose(int x1, int y1, int x2, int y2)
    {
        return Helper.instance().isClose(x1,y1,x2,y2,accuracy);
    }

    public void removeFood(int x, int y)
    {
        Food fRemove = null ;


        for(Food c : foodList){
            if (isClose(x,y,c.currentX,c.currentY))

                fRemove = c ;
        }
        if (fRemove != null) {
            foodList.remove(fRemove);
        }

    }

    public void drawFoods(Canvas canvas)
    {
        maxX =  canvas.getWidth()-21;
        maxY =  canvas.getHeight()-21;

        if (foodList.size() < this.maxFood) {
            for (int i = foodList.size(); i < this.maxFood; i++) {
                foodList.add(getRandomFood());
            }
        }

        for(Food c : foodList){
            c.drawFood(canvas);
            //if(c.x==x && c.y==y) return false;
        }
    }

    private Food getRandomFood()
    {
        Random random = new Random();
        int x = random.nextInt(this.maxX);
        int y = random.nextInt(this.maxY);

        return new Food(x,y);
    }

    private class Food{
        private int currentX = 0;
        private int currentY = 0;
        private int radius = 10;
        private Paint pFood ;

        public Food(int x, int y)
        {
            this.currentX = x;
            this.currentY = y;

            pFood = new Paint();
            pFood.setColor(Color.BLUE);
            pFood.setStyle(Paint.Style.STROKE);
            pFood.setStrokeWidth(10);
        }

        public void drawFood(Canvas canvas)
        {
            canvas.drawCircle(this.currentX,this.currentY,this.radius,pFood);
        }
    }
}
