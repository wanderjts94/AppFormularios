package pe.edu.idat.appformularios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import pe.edu.idat.appformularios.databinding.ActivityListadoBinding
import pe.edu.idat.appformularios.databinding.ActivityRegistroBinding
import android.R

class ListadoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityListadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listapersona= intent.getSerializableExtra("listapersonas")
        as ArrayList<String>
        val adapter= ArrayAdapter(applicationContext,R.layout.simple_list_item_1,listapersona)
        binding.lvpersonas.adapter=adapter
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}