package com.example.listcontact

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(var listener: ClickItemContactListener) :
    RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>(){//Foi adicionado a classe listener como parametro

    private val list: MutableList<Contact> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        val view = LayoutInflater.from (parent.context).inflate(R.layout.contact_item, parent, false)
         return ContactAdapterViewHolder(view, list, listener)
           //Método resposável por criar cada item visual na tela, ou seja desenhando a view, com um layout para que posteriormente possamos preencher.
    }

    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        holder.bind(list [position])
        //Método responsável por rodar em cada item do array, obter o valor e preencher na tela.
    }

    override fun getItemCount(): Int {
        return list.size
        //Método responsável pela contagem do número de contatos na lista.
    }

    fun updateList(list:List<Contact>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()

    } //Sempre que for chamado este método, será limpo (clear) a lista interna, e após limpar, irá popular ela (addAll (list)), e por final irá notificar o adapter que a lista foi modificada (notify).

    class ContactAdapterViewHolder (itemView : View, var list: List<Contact>, var listener: ClickItemContactListener) : RecyclerView.ViewHolder(itemView){
        //Basicamente cada tipo de item
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val ivPhotograph: ImageView = itemView.findViewById(R.id.iv_photograph)

        init {
            itemView.setOnClickListener {
                listener.clickItemContact(list[adapterPosition])
            }

        }

            fun bind(contact: Contact){
                tvName.text = contact.name
                tvPhone.text = contact.phone

                //Método que vai popular os dados

            }
    }



    }


