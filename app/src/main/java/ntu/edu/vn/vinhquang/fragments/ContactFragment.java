package ntu.edu.vn.vinhquang.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ntu.edu.vn.vinhquang.customlayout.R;
import ntu.edu.vn.vinhquang.models.Friend;
import ntu.edu.vn.vinhquang.models.FriendsManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    ArrayList<Friend> listFriend = new ArrayList<>();
    RecyclerView rvFriends;
    ContactAdapter adapter;

    public ContactFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_contact, container, false);
        rvFriends = view.findViewById(R.id.rvListContact);
        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        updateRV();
        return view;
    }

    private void updateRV()
    {
        listFriend = FriendsManager.getInstance().getFriends();
        adapter = new ContactAdapter(listFriend);
        rvFriends.setAdapter(adapter);

    }

    /**
     * ViewHolder dung de hien thi thong tin cua mot Friend
     */
    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txtName, txtBirthday, txtPhone;
        ImageView imgEdit, imgCall, imgMess;
        Friend fr;
        public ContactHolder(@NonNull View itemView)
        {
            super(itemView);
            txtName = this.itemView.findViewById(R.id.txtName);
            txtBirthday = this.itemView.findViewById(R.id.txtBirthDay);
            txtPhone = this.itemView.findViewById(R.id.txtPhoneNumber);
            imgEdit = this.itemView.findViewById(R.id.imgV_edit);
            imgCall = this.itemView.findViewById(R.id.imgV_call);
            imgCall.setOnClickListener(this);
            imgMess = this.itemView.findViewById(R.id.imgV_mess);
        }

        /**
         * Hien thi thong tin cua doi tuong len mot itemView
         * @param friend
         */
        public void onBind(Friend friend)
        {
            this.fr = friend;
            txtName.setText(friend.getName());
            txtBirthday.setText(friend.getBirthday());
            txtPhone.setText(friend.getPhone());
        }
        public void onClick(View v)
        {
            int id = v.getId();
            if(id == R.id.imgV_call)
            {
                Uri uri = Uri.parse("tel:"+this.fr.getPhone());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
               getActivity(). startActivity(intent);
            }
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>
    {
        ArrayList<Friend> listFriends;

        public ContactAdapter(ArrayList<Friend> listFriends)
        {
            this.listFriends = listFriends;
        }

        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_rv, viewGroup, false);
            return new ContactHolder(view);
        }

        /**
         * Hien thi thong tin mot doi tuong trong listFriend len mot ContactHolder
         * @param contactHolder: Doi tuong lop ContactHolder
         * @param i: Chi muc cua doi tuong trong listFriends
         */
        @Override
        public void onBindViewHolder(@NonNull ContactHolder contactHolder, int i)
        {
            contactHolder.onBind(listFriends.get(i));
        }

        @Override
        public int getItemCount()
        {
            return listFriends.size();
        }
    }

}
