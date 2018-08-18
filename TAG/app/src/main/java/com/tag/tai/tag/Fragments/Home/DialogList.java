package com.tag.tai.tag.Fragments.Home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.tag.tai.tag.Services.RetroClient;

public class DialogList extends DialogFragment {

    private String[] items;
    private String title;


    static DialogList newInstance(String[] items, int categoryId, int intent, String title) {
        DialogList d = new DialogList();
        Bundle b = new Bundle();
        b.putStringArray("items", items);
        b.putString("title", title);
        b.putInt("categoryId", categoryId);
        b.putInt("intent", intent);
        d.setArguments(b);
        return d;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        items = getArguments().getStringArray("items");

        Log.d(RetroClient.TAG, "onCreateDialog: CategoryID" + getArguments().getInt("categoryId"));
        Log.d(RetroClient.TAG, "onCreateDialog: intent" + getArguments().getInt("intent"));


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("title", "Pick a category");
        builder.setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "" + items[which], Toast.LENGTH_SHORT).show();

                        Intent i = new Intent();
                        i.putExtra("position", which);
                        i.putExtra("itemname", items[which]);
                        i.putExtra("categoryid", getArguments().getInt("categoryId"));
                        i.putExtra("intent", getArguments().getInt("intent"));

                        getTargetFragment().onActivityResult(HomeFragment.DIALOG_FRAGMENT, Activity.RESULT_OK, i);

                    }
                });

        return builder.create();
    }
}
