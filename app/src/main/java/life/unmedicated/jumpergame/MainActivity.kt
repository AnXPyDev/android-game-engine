package life.unmedicated.jumpergame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import life.unmedicated.engine2d.core.Engine
import life.unmedicated.engine2d.input.TouchInput

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val engine = Engine(this)

        TouchInput(engine)

        val game = JumperGame(engine)

        setContentView(engine.displayManager.displayView)

        engine.start()
    }
}