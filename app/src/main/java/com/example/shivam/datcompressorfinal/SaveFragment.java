package com.example.shivam.datcompressorfinal;


import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveFragment extends Fragment {
GestureLibrary gestureLibrary;
    boolean gesturedrawn;
    Gesture gesture;
    public static final String TAG="SaveFragment";
    public String gesturename;
     ViewGroup container,placeholder;
    LayoutInflater inflater;
    public SaveFragment() {
        // Required empty public constructor
    }

View v1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container=container;
        this.inflater=inflater;
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_save, container, false);
        v1=(View)v;
        Log.d(TAG, "path = " + Environment.getExternalStorageDirectory().getAbsolutePath());
        //getActivity().openOptionsMenu();
        gestureLibrary= GestureLibraries.fromFile(getActivity().getExternalFilesDir(null) + "/" + "gesture.txt");
        gestureLibrary.load();
        GestureOverlayView gestureOverlayView=(GestureOverlayView)v.findViewById(R.id.savegesture);
        gestureOverlayView.addOnGestureListener(mGestureListener);
        resetEverything();
        placeholder=(ViewGroup) v;
        return placeholder;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private GestureOverlayView.OnGestureListener mGestureListener= new GestureOverlayView.OnGestureListener() {
        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
        gesturedrawn=true;
        }

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event)
        {
                gesture=overlay.getGesture();
        }

        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

        }
    };
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.topmenu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

        void reDrawGestureView(View v) {
            View newview=inflater.inflate(R.layout.fragment_save,container,false);
            placeholder.removeAllViews();
            placeholder.addView(newview);
            GestureOverlayView gestures = (GestureOverlayView) newview.findViewById(R.id.savegesture);
            gestures.removeAllOnGestureListeners();
            gestures.addOnGestureListener(mGestureListener);
            resetEverything();
        }
    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch(menuItem.getItemId())
        {
            case R.id.deletegesture:
            {
                reDrawGestureView(getView());
                return true;
            }
            case R.id.savegesture:
            {
                if(gesturedrawn)
                {
                    getName();
                }
                return true;
            }
        }
        super.onOptionsItemSelected(menuItem);
        return true;
    }

    private void getName() {
        AlertDialog.Builder namePopup = new AlertDialog.Builder(getActivity());
        namePopup.setTitle("");


        final EditText nameField = new EditText(getActivity());
        namePopup.setView(nameField);
        namePopup.setPositiveButton("Save ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //db.updateExistingMeasurement(measurement);
                if (!nameField.getText().toString().matches("")) {
                    gesturename = nameField.getText().toString();
                    saveGesture();
                } else {
                    getName();
                }
                //return;
            }
        });
        namePopup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gesturename = "";
                return;
            }
        });

        namePopup.show();

    }

    private void saveGesture() {
        DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());


        //TODO: check kar k same naam valu gesture che k nai
        gestureLibrary.addGesture(gesturename, gesture);
        Log.e("Gesture","gesture name"+gesturename);
        if (!gestureLibrary.save()) {
            Log.e(TAG, "gesture not saved!");
        }else {
            dataBaseHelper.insertgesturename(gesturename.trim());
            Toast.makeText(getContext(), "File saved in " + getActivity().getExternalFilesDir(null) + "/gesture.txt",Toast.LENGTH_SHORT).show();
        }
        reDrawGestureView(getView());

    }
    private void resetEverything(){
        gesturedrawn = false;
        gesture = null;
        gesturename = "";
    }
}
