package com.example.project_kotlin

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_kotlin.dao.*
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.*
import com.example.project_kotlin.utils.appConfig
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AperturaDaoTest {
    private lateinit var aperturaDao: AperturaDao
    private lateinit var estableci : EstablecimientoDao
    private lateinit var cajaDao: CajaDao
    private lateinit var categoriaPlatoDao: CategoriaPlatoDao
    private lateinit var db: ComandaDatabase
    private lateinit var platoDao: PlatoDao

    @Before
    fun crearDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ComandaDatabase::class.java).build()
        aperturaDao = db.aperturaDao()
        categoriaPlatoDao = db.categoriaPlatoDao()
        cajaDao = db.cajaDao()
        platoDao = db.platoDao()
        estableci = appConfig.db.establecimientoDao()

    }

    @After
    @Throws(IOException::class)
    fun cerrarDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun registrarCajaAperturaLeerCajaConApertura() {



        var categoria = CategoriaPlato("C-001", "Chafuas")
        var plato = Plato("P-001", "Pollo", 22.5, "SADAS", "C-001")

        var categoriaID = categoriaPlatoDao.guardar(categoria)
        System.out.println(categoriaPlatoDao.obtenerTodo())
        var platoID = platoDao.guardar(plato)
        System.out.println(platoDao.obtenerTodo())

    }
}