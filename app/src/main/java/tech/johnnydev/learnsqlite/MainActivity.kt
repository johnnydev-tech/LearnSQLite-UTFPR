package tech.johnnydev.learnsqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tech.johnnydev.learnsqlite.database.DatabaseHandler
import tech.johnnydev.learnsqlite.databinding.ActivityMainBinding
import tech.johnnydev.learnsqlite.entity.Cadastro


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        setButtonListener()

        db = DatabaseHandler(this)

    }

    private fun setButtonListener() {
        binding.btInclude.setOnClickListener {
            btnIncludeOnClick();
        }

        binding.btUpdate.setOnClickListener {
            btnUpdateOnClick();
        }

        binding.btDelete.setOnClickListener {
            btnDeleteOnClick();
        }

        binding.btSearch.setOnClickListener {
            btnSelectOnClick();
        }

        binding.btList.setOnClickListener {
            btnListOnClick();
        }


    }

    private fun btnIncludeOnClick() {

        if (binding.etName.text.isEmpty() || binding.etPhone.text.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }


        val cadastro = Cadastro(
            0,
            binding.etName.text.toString(),
            binding.etPhone.text.toString()
        )
        db.insert(cadastro)

        clearFieldsAndUnfocus()

        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()

    }

    private fun btnListOnClick() {
        val items = db.list()

        val registerList = mutableListOf<String>()
        for (item in items) {
            registerList.add("ID: ${item._id} - Nome: ${item.name} - Telefone: ${item.phone}\n")
        }
        clearFieldsAndUnfocus()

        Toast.makeText(this, registerList.toString(), Toast.LENGTH_LONG).show()
    }

    private fun btnSelectOnClick() {

        if (binding.etCode.text.isEmpty()) {
            Toast.makeText(this, "Preencha o campo código", Toast.LENGTH_SHORT).show()
            return
        }

        val register = db.find(binding.etCode.text.toString().toInt())
        if (register != null) {
            binding.etName.setText(register.name)
            binding.etPhone.setText(register.phone)

        } else {
            Toast.makeText(this, "Cadastro não encontrado", Toast.LENGTH_SHORT).show()
        }


    }

    private fun btnDeleteOnClick() {
        if (binding.etCode.text.isEmpty()) {
            Toast.makeText(this, "Preencha o campo código", Toast.LENGTH_SHORT).show()
            return
        }
        db.delete(binding.etCode.text.toString().toInt())
        clearFieldsAndUnfocus()
        Toast.makeText(this, "Cadastro excluído com sucesso", Toast.LENGTH_SHORT).show()
    }

    private fun btnUpdateOnClick() {
        if (binding.etCode.text.isEmpty() || binding.etName.text.isEmpty() || binding.etPhone.text.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }
        val cadastro = Cadastro(
            binding.etCode.text.toString().toInt(),
            binding.etName.text.toString(),
            binding.etPhone.text.toString()
        )

        db.update(cadastro)
        clearFieldsAndUnfocus()
        Toast.makeText(this, "Cadastro atualizado com sucesso", Toast.LENGTH_SHORT).show()
    }


    fun clearFieldsAndUnfocus() {
        binding.etCode.text.clear()
        binding.etName.text.clear()
        binding.etPhone.text.clear()

        binding.etCode.clearFocus()
        binding.etName.clearFocus()
        binding.etPhone.clearFocus()


        ViewCompat.getWindowInsetsController(binding.root)?.hide(WindowInsetsCompat.Type.ime())


    }
}