package ch.rmbi.melsgravitysnake;

public class Helper {
    private static Helper _instance = null ;
    private Helper()
    {

    }

    public static Helper instance()
    {
        if (_instance == null)
            _instance = new Helper();
        return _instance;
    }
    public boolean isClose(int x1,int x2,int accuracy){
        return x2 < x1+accuracy && x2 >x1-accuracy;
    }

    public boolean isClose(int x1, int y1, int x2, int y2,int accuracy)
    {
        return isClose(x1,x2,accuracy) && isClose(y1,y2,accuracy);

    }
}
