package ch.rmbi.melsgravitysnake;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity   {

    private GameBoard gameBoard;
    private SensorManager sensorManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = new GameBoard(this);
        setContentView(gameBoard);

        sensorManager = (SensorManager) getSystemService( SENSOR_SERVICE );

    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(
                gameBoard,
                sensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER ),
                sensorManager.SENSOR_DELAY_GAME );
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener( gameBoard );
    }
}
