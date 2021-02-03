package com.uzandroid.contactapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uzandroid.contactapp.R;
import com.uzandroid.contactapp.conventer.ImageConverter;
import com.uzandroid.contactapp.model.UsersContact;

import java.util.ArrayList;
import java.util.List;

import static com.uzandroid.contactapp.R.*;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<UsersContact> contacts = new ArrayList<>();
    private Context context;
    private OnItemEditListener listener;
    private OnItemDeleteListener listenerDelete;
    private OnItemClickListener listenerClick;

    public ContactAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.item_contact, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactHolder holder, final int position) {

        final UsersContact contact = contacts.get(position);

        holder.textName.setText(contact.getName());
        holder.textPhone.setText(contact.getPhone());
        holder.textUssd.setText(contact.getUssd());

holder.image_item_id.setImageBitmap(ImageConverter.toBitmap(contact.getImageUrl()));


        holder.callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = "tel:" + contact.getPhone();

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }



    public void filterList(ArrayList<UsersContact> fileredList){

        contacts = fileredList;
        notifyDataSetChanged();
    }

    public void setContacts(List<UsersContact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

/*    public UsersContact getContactAt(int position) {
        return contacts.get(position);
    }*/


    class ContactHolder extends RecyclerView.ViewHolder {
        private ImageView callPhone;
        private TextView textName;
        private TextView textPhone, textUssd;
        private ImageView image_item_id;


        ContactHolder(@NonNull View itemView) {
            super(itemView);
            image_item_id = itemView.findViewById(id.image_item_id);
            callPhone = itemView.findViewById(id.image_call_Phone_id);
            textName = itemView.findViewById(id.text_name_id);
            textPhone = itemView.findViewById(id.text_phone_id);
            textUssd = itemView.findViewById(id.text_ussd_id);
            ImageView edit_imageView = itemView.findViewById(id.edit_imageView);
            ImageView delete_imageView = itemView.findViewById(id.delete_imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (listenerClick != null && pos != RecyclerView.NO_POSITION) {
                        listenerClick.onItemClickActivity(contacts.get(pos));

                    }

                }
            });


            edit_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = getAdapterPosition();

                    if (listener != null && pos != RecyclerView.NO_POSITION) {

                        listener.onItemClickEdit(contacts.get(pos));

                    }
                }
            });

            delete_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (listenerDelete != null && pos != RecyclerView.NO_POSITION) {

                        listenerDelete.onItemClickDelete(contacts.get(pos));

                    }

                }
            });

        }
    }

    //listener bosilgan view ni aniqlab berish uchun kerak
    public interface OnItemClickListener {
        void onItemClickActivity(UsersContact contact);

    }

    //delete button
    public interface OnItemDeleteListener {
        void onItemClickDelete(UsersContact contact);
    }

    //edit button
    public interface OnItemEditListener {
        void onItemClickEdit(UsersContact contact);

    }

    public void setOnItemClickListener(OnItemClickListener listenerClick) {
        this.listenerClick = listenerClick;
    }

    public void setOnItemClickDeleteListener(OnItemDeleteListener listenerDelete) {
        this.listenerDelete = listenerDelete;
    }

    public void setOnItemClickListener(OnItemEditListener listener) {
        this.listener = listener;

    }

}
