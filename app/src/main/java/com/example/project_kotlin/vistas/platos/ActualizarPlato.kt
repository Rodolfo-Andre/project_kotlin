package com.example.project_kotlin.vistas.platos

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.PlatoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Plato
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.appcompat.app.AlertDialog
import com.example.project_kotlin.entidades.CategoriaPlato

import java.io.ByteArrayOutputStream

class ActualizarPlato:AppCompatActivity() {
    private lateinit var tvCodPlatos:TextView
    private lateinit var edtNamePlato:EditText
    private lateinit var edtPrePlato:EditText
    private lateinit var ImagenPlatos:ImageView
    private  lateinit var spCatplato:Spinner
    private lateinit var btnEditar:Button
    private lateinit var btnCancelar:Button
    private lateinit var btnEliminar:Button
    private lateinit var btnImagenPlato:Button

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var PlatosDao: PlatoDao
    private var imageData1: ByteArray? = null
    private lateinit var imageData2:ByteArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.platoeditar)
        PlatosDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).platoDao()

        tvCodPlatos=findViewById(R.id.tvCodPlatos)
        edtNamePlato= findViewById(R.id.edtNamePlato)
        edtPrePlato = findViewById(R.id.edtPrePlato)
        ImagenPlatos = findViewById(R.id.imageplatoEditar)
        spCatplato= findViewById(R.id.spCatplato)

        btnEditar= findViewById(R.id.btnEditar)
        btnCancelar= findViewById(R.id.btnCancel)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnImagenPlato = findViewById(R.id.btnSubirImagens)

        cargarCategoria()

        btnEditar.setOnClickListener{actualizar()}
        btnEliminar.setOnClickListener{Eliminar()}
        btnCancelar.setOnClickListener{volver()}

        btnImagenPlato.setOnClickListener {
            // Lanzar la selección de imágenes desde la galería
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }


        //Cargar dato
        val plato = intent.getSerializableExtra("plato") as Plato
        tvCodPlatos.text = plato.id
        edtNamePlato.setText(plato.nombrePlato)
        edtPrePlato.setText(plato.precioPlato.toString())
        val imageData = plato.nombreImagen
        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        ImagenPlatos.setImageBitmap(bitmap)
        //para ver la secuencia del codigo
        val numberPattern = Regex("\\d+")
        val numberMatch = numberPattern.find(plato.categoriaPlato.id)
        val number = numberMatch?.value?.toIntOrNull()
        if (number != null) {
            spCatplato.setSelection(number - 1)
        }

        //imagen cargada para actualizar
        imageData2 = plato.nombreImagen
    }

    fun volver(){
        var intent= Intent(this, DatosPlatos::class.java)
        startActivity(intent)
    }

    @SuppressLint("SuspiciousIndentation")
    fun Eliminar() {
        val codPlato = tvCodPlatos.text.toString()
        val mensaje: AlertDialog.Builder = AlertDialog.Builder(this)
        mensaje.setTitle("Sistema comandas")
        mensaje.setMessage("¿Seguro de eliminar?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton("Aceptar") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                val plato = PlatosDao.obtenerPorId(codPlato)
                    PlatosDao.eliminar(plato)
                    mostrarToast("Plato eliminado correctamente")
                    volver()

            }
        }
        mensaje.setNegativeButton("Cancelar") { _, _ -> }
        mensaje.setIcon(android.R.drawable.ic_delete)
        mensaje.show()
    }



    fun actualizar(){
    if(validarCampos()) {
        val codPlato = tvCodPlatos.text.toString()
        if (imageData1 != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                val nombre = edtNamePlato.text.toString()
                val precio = edtPrePlato.text.toString().toDouble()
                val codCatPlato = (spCatplato.selectedItemPosition + 1).toString()
                val cat = "C-00" + codCatPlato
                val nombrecat = spCatplato.selectedItem.toString()

                val plato =
                    Plato(codPlato, nombre, precio, imageData1!!)

                plato.categoriaPlato = CategoriaPlato(cat, nombrecat)

                PlatosDao.actualizar(plato)
                mostrarToast("Plato actualizada correctamente")
                volver()
            }
        } else  {
            lifecycleScope.launch(Dispatchers.IO) {
                val nombre = edtNamePlato.text.toString()
                val precio = edtPrePlato.text.toString().toDouble()
                val codCatPlato = (spCatplato.selectedItemPosition + 1).toString()
                val cat = "C-00" + codCatPlato
                val nombrecat = spCatplato.selectedItem.toString()

                val plato =
                    Plato(codPlato, nombre, precio, imageData2)

                plato.categoriaPlato = CategoriaPlato(cat, nombrecat)

                PlatosDao.actualizar(plato)
                mostrarToast("Plato actualizada correctamente")
                volver()
            }

        }

    }

    }




    private fun cargarCategoria() {
        lifecycleScope.launch(Dispatchers.IO) {

            // Obtén la lista de categorías de platos desde la base de datos
            var data = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao().obtenerTodo()

            var nombreCat =   data.map {  it.categoria }
            // Crea un ArrayAdapter con los nombres de las categorías de platos
            var adapter = ArrayAdapter(
                this@ActualizarPlato,
                android.R.layout.simple_spinner_item,nombreCat

            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Asigna el adaptador al Spinner
            spCatplato.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Obtener la imagen seleccionada
            val imageUri = data.data
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            // Convertir la imagen en un ByteArray
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            imageData1 = outputStream.toByteArray()

            // Mostrar la imagen seleccionada en una ImageView (opcional)
            val imageView = findViewById<ImageView>(R.id.imageplatoEditar)
            imageView.setImageBitmap(bitmap)
        }
    }

    fun validarCampos() : Boolean{
        val nombre = edtNamePlato.text.toString()
        val precio = edtPrePlato.text.toString()

        val REGEX_NOMBRE = "^[A-Z][a-zA-Z\\\\s]+\$"
        val REGEX_PRECIO = "^-?\\d+\\.?\\d*\$"

        if (!REGEX_NOMBRE.toRegex().matches(nombre)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("El campo nombre no cumple con el formato requerido. Debe comenzar con mayúscula y contener solo letras y espacios")
            return false
        }
        if (!REGEX_PRECIO.toRegex().matches(precio)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("Ingrese un precio correcto")
            return false
        }


        return true
    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

}
