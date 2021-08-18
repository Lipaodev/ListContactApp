package com.example.listcontact

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.SharedMemory
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import com.example.listcontact.DetailActivity.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemContactListener {

    private val rvList: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_list) //Obtem o recyclerview do xml
    }
    private val adapter= ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        fetchListContact()
        bindViews()

    }

    private fun fetchListContact(){ //Método cria a lista abaixo e salva no sharedpreferences para depois ser utilizada no método bindView
        val list =   arrayListOf(
            Contact(
                "Felipe",
                "(00) 0001-0021",
                "img.png"

            ), Contact(
                "Felipe",
                "(00) 0001-0021",
                "img.png"

            )
        )
        getInstanceSharedPreferences().edit {
            putString("contacts", Gson().toJson(list)) //Método converte um objeto de classe para string Json
            commit()
        }

    }

    private fun getInstanceSharedPreferences(): SharedPreferences {
        return getSharedPreferences("com.example.listcontact.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun initDrawer(){ //Inicialização do drawer layout
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)//Ação de abrir e fechar o drawer layout
        toggle.syncState() //Sincronizar o evento de abrir e fechar com o layout
    }

    private fun bindViews(){
        rvList.adapter = adapter //unifica o adapter e main
        rvList.layoutManager = LinearLayoutManager(this) //A forma como o recyclerview irá se comportar
        updateList()
    }

    private fun getListContacts(): List<Contact>{
        val list = getInstanceSharedPreferences().getString("contacts","[]") //Método obtem lista de contatos do metodo de preferencias do usuário
        val turnsType =  object : TypeToken<List<Contact>>() {}.type //Converte a string em objetode classe
        return Gson().fromJson(list, turnsType)
    }

    private fun updateList(){
        adapter.updateList(getListContacts())
    }


    private fun showToast (message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }//Método privado que faz encapsulamento do toast, ou captura

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true //Implementado o método de criação do menu.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) { //<--Neste momento sabemos qual item será clicado pelo ID
           R.id.item_menu_1 -> {
               showToast("Exibindo item de menu 1")
               true //Verificação que exibe mensagem de clique no menu 1
           }R.id.item_menu_2 ->{
               showToast("Exibindo item de menu 2")
               true //Verificação que exibe mensagem de clique no menu 2
           }
           else -> super.onOptionsItemSelected(item) //Padrão da implementação do metodo de capturar cliques no itens de menu
       }
    }

    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact) //Método chave/valor para identificação do objeto
        startActivity(intent)
    }

}