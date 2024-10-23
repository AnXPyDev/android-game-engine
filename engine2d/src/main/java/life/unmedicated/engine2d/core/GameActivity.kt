package life.unmedicated.engine2d.core

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

abstract class GameActivity : AppCompatActivity() {

    lateinit private var engine: Engine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        engine = Engine(this)
        setupEngine(engine)

        createGame(engine)

        setContentView(engine.displayManager.displayView)
        engine.start()
    }

    abstract fun createGame(engineContext: EngineContext)
    abstract fun setupEngine(engine: Engine)
}