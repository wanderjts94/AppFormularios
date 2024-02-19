package pe.edu.idat.appformularios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pe.edu.idat.appformularios.databinding.ActivityListadoBinding
import pe.edu.idat.appformularios.databinding.ActivityRegistroBinding

class ListadoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityListadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}