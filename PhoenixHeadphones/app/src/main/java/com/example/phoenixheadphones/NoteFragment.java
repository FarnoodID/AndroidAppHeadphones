package com.example.phoenixheadphones;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoenixheadphones.entities.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteFragment extends Fragment {


    Button btnAdd,btnUpdate;
    EditText etName,etNote;
    TextView tvHeadphoneId;
    ListView lvMain;


    ArrayAdapter<String> headphoneListAdapter;
    ArrayList<String> headphonesArray;


    ArrayList<Integer> headphoneID;
    ArrayList<String> headphoneName;
    ArrayList<String> headphoneNote;

    public static HeadphoneDB headphoneDB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);




        btnAdd = view.findViewById(R.id.btn_add);
        btnUpdate = view.findViewById(R.id.btn_update);
        etName = view.findViewById(R.id.et_name);
        etNote = view.findViewById(R.id.et_note);

        tvHeadphoneId = view.findViewById(R.id.tv_headphone_id);
        lvMain = view.findViewById(R.id.lv_main);

        headphoneListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        headphonesArray = new ArrayList<String>();
        headphoneID = new ArrayList<Integer>();
        headphoneName = new ArrayList<String>();
        headphoneNote = new ArrayList<String>();

        headphoneDB = Room.databaseBuilder(getActivity(), HeadphoneDB.class,"headphoneDB").build();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextIsEmpty())
                    return;
                saveHeadphone();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextIsEmpty())
                    return;
                updateHeadphone();
            }
        });



        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvHeadphoneId.setText(headphoneID.get(position).toString());
                etName.setText(headphoneName.get(position));
                etNote.setText(headphoneNote.get(position));
            }
        });

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Remove Note?");
                alertDialogBuilder.setMessage("Are you sure you want to remove\n" + headphoneName.get(position));
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast(getActivity().getApplicationContext(),"Action Canceled");
                    }
                });
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note note = new Note(Integer.parseInt(headphoneID.get(position).toString()));
                        deleteHeadphone(note);
                    }
                });
                alertDialogBuilder.show();

                return true;
            }
        });


        return view;
    }



    public void saveHeadphone() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Note note = new Note(etName.getText().toString(),etNote.getText().toString());
                headphoneDB.headphoneDao().insertHeadphone(note);
                toast(getActivity().getApplicationContext(),"Note Added");
                getAllHeadphone();

            }
        }).start();



    }
    public void updateHeadphone() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Note note = new Note(Integer.parseInt(tvHeadphoneId.getText().toString()),etName.getText().toString(),etNote.getText().toString());
                headphoneDB.headphoneDao().updateHeadphone(note);
                toast(getActivity().getApplicationContext(),"Note Updated");
                getAllHeadphone();

            }
        }).start();

    }
    public void deleteHeadphone(final Note note) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                headphoneDB.headphoneDao().deleteHeadphone(note);
                toast(getActivity().getApplicationContext(),"Note  Removed");
                getAllHeadphone();

            }
        }).start();



    }
    public void getAllHeadphone() {

        headphoneID.clear();
        headphoneName.clear();
        headphoneNote.clear();
        headphonesArray.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> note = headphoneDB.headphoneDao().getAllHeadphones();
                String headphoneInfor;
                for(Note note1 : note)
                {
                    headphoneInfor = "Name: " + note1.getHeadphoneName() +
                            "\nNote: " + note1.getHeadphoneNote();

                    headphonesArray.add(headphoneInfor);
                    headphoneID.add(note1.getHeadphoneID());
                    headphoneName.add(note1.getHeadphoneName());
                    headphoneNote.add(note1.getHeadphoneNote());

                }
                showDataInListView();

            }
        }).start();




    }
    public void showDataInListView() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                headphoneListAdapter.clear();
                headphoneListAdapter.addAll(headphonesArray);

                lvMain.setAdapter(headphoneListAdapter);

            }
        });


    }
    private boolean editTextIsEmpty(){
        if(TextUtils.isEmpty(etName.getText().toString())){
            etName.setError("Cannot be Empty");
        }
        if(TextUtils.isEmpty(etNote.getText().toString())){
            etNote.setError("Cannot be empty");
        }

        if(TextUtils.isEmpty(etName.getText().toString())||TextUtils.isEmpty(etNote.getText().toString())){

            return true;
        }else
            return false;
    }
    public void toast(final Context context, final String text){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,text,Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        getAllHeadphone();
    }
}