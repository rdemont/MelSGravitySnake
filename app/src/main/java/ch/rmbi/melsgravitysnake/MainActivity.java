package ch.rmbi.melsgravitysnake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GameBoard gameBoard;
    private TextView tvScore ;
    private Button bStart ;
    private SensorManager sensorManager = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        tvScore = (TextView)findViewById(R.id.tvScore);
        tvScore.setText("0");

        bStart = (Button) findViewById(R.id.bStart);
        bStart.setOnClickListener(this);
        bStart.setText("Start");

        gameBoard = (GameBoard)findViewById(R.id.vGame);
        gameBoard.setTextView(tvScore);
        gameBoard.setButton(bStart);
        //gameBoard = new GameBoard(this);
        //setContentView(gameBoard);

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

    @Override
    public void onClick(View v) {
        if (gameBoard.getIsStarting()) {
            gameBoard.stopGame();
        }else {
            gameBoard.startGame();
        }
    }
}
