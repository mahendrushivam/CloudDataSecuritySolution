package com.example.shivam.datcompressorfinal;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import me.itangqi.waveloadingview.WaveLoadingView;


public class ProgressLoader extends Fragment {


WaveLoadingView waveLoadingView;
Button shapetype,wavecolor,backgroundcolor,bordercolor,amplitude,borderwidth,animator;
    public static final String WAVE_COLOR="WAVE_COLOR";
    public static final String BORDER_COLOR="BORDER_COLOR";
    public static final String BACKGROUND_COLOR="BACKGROUND_COLOR";
    public  static final String BORDER_WIDTH="BORDER_WIDTH";
    public  static final String PROGRESS_SHAPE="PROGRESS_SHAPE";
    public static final String WAVE_AMPLITUDE="WAVE_AMPLITUDE";
    public  static final  String PROGRESS_VALUE="PROGRESS_VALUE";
SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ProgressLoader() {
    }
    private int checkedItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_progress_loader, container, false);
        waveLoadingView=(WaveLoadingView)v.findViewById(R.id.waveLoadingView);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        setProgressLayout();
        shapetype=(Button)v.findViewById(R.id.tv_shape_type);
        wavecolor=(Button)v.findViewById(R.id.tv_wave_color) ;
        backgroundcolor=(Button)v.findViewById(R.id.tv_wave_background_color);
        bordercolor=(Button)v.findViewById(R.id.tv_border_color);
        amplitude=(Button)v.findViewById(R.id.tv_amplitude);
        borderwidth=(Button)v.findViewById(R.id.border_width);
        animator=(Button)v.findViewById(R.id.tv_animator);



        //imageView=(ImageView)v.findViewById(R.id.shapeicon) ;
        amplitude.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#EAF2F8"), PorterDuff.Mode.SRC_ATOP);
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {

                        LayoutInflater inflater =getActivity().getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.bordamplayout, null);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Set Wave Amplitude")
                                .setView(dialogView)
                                .setCancelable(true)
                                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DiscreteSeekBar seekBar=(DiscreteSeekBar)dialogView.findViewById(R.id.seekbar) ;
                                        int width=seekBar.getProgress();
                                            waveLoadingView.setAmplitudeRatio(width);
                                        editor=sharedPreferences.edit();
                                        editor.putInt(WAVE_AMPLITUDE,width);
                                        editor.commit();

                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        //imageView.setBackgroundColor(Color.parseColor("#e2e7e7"));
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        animator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#EAF2F8"), PorterDuff.Mode.SRC_ATOP);
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {

                        LayoutInflater inflater =getActivity().getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.animatorlayout, null);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Change Animation Effects")
                                .setView(dialogView)
                                .setCancelable(true)
                                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, final int which) {

                                        ((CheckBox) dialogView.findViewById(R.id.cb_animator_cancel_and_start)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                if (b) {
                                                    waveLoadingView.cancelAnimation();
                                                } else {
                                                    waveLoadingView.startAnimation();
                                                }
                                            }
                                        });

                                        ((CheckBox) dialogView.findViewById(R.id.cb_animator_pause_and_resume)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                if (b) {
                                                    waveLoadingView.pauseAnimation();
                                                } else {
                                                    waveLoadingView.resumeAnimation();
                                                }
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        //imageView.setBackgroundColor(Color.parseColor("#e2e7e7"));
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        borderwidth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#EAF2F8"), PorterDuff.Mode.SRC_ATOP);
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {

                        LayoutInflater inflater =getActivity().getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.bordamplayout, null);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Set border width")
                                .setView(dialogView)
                                .setCancelable(true)
                                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DiscreteSeekBar seekBar=(DiscreteSeekBar)dialogView.findViewById(R.id.seekbar) ;
                                        float width=seekBar.getProgress();
                                        waveLoadingView.setBorderWidth(width);
                                        editor=sharedPreferences.edit();
                                        editor.putFloat(BORDER_WIDTH,width);
                                        editor.commit();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        //imageView.setBackgroundColor(Color.parseColor("#e2e7e7"));
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        bordercolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(waveLoadingView.getWaveColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                waveLoadingView.setBorderColor(selectedColor);
                                editor=sharedPreferences.edit();
                                editor.putInt(BORDER_COLOR,selectedColor);
                                editor.commit();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });
        backgroundcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(waveLoadingView.getWaveColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                waveLoadingView.setBackgroundColor(selectedColor);
                                editor=sharedPreferences.edit();
                                editor.putInt(BACKGROUND_COLOR,selectedColor);
                                editor.commit();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });
        wavecolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(waveLoadingView.getWaveColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                waveLoadingView.setWaveColor(selectedColor);
                                editor=sharedPreferences.edit();
                                editor.putInt(WAVE_COLOR,selectedColor);
                                editor.commit();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });
        shapetype.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#EAF2F8"), PorterDuff.Mode.SRC_ATOP);
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {

                        new AlertDialog.Builder(getActivity()).setTitle("Shape Type").setSingleChoiceItems(
                                new String[] { "CIRCLE", "TRIANGLE", "SQUARE", "RECTANGLE" }, checkedItem,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor=sharedPreferences.edit();
                                        checkedItem = which;
                                        switch (which) {
                                            case 0:
                                              waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                                                editor.putString(PROGRESS_SHAPE,"Circle");
                                                dialog.dismiss();
                                                break;
                                            case 1:
                                                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.TRIANGLE);
                                                editor.putString(PROGRESS_SHAPE,"Triangle");
                                                dialog.dismiss();
                                                break;
                                            case 2:
                                                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.SQUARE);
                                                editor.putString(PROGRESS_SHAPE,"Square");
                                                dialog.dismiss();
                                                break;
                                            case 3:
                                                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.RECTANGLE);
                                                editor.putString(PROGRESS_SHAPE,"Rectangle");
                                                dialog.dismiss();
                                                break;
                                            default: {
                                                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                                                editor.putString(PROGRESS_SHAPE,"Circle");
                                                dialog.dismiss();
                                                break;
                                            }
                                        }
                                        editor.commit();
                                    }
                                }).show();
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        //imageView.setBackgroundColor(Color.parseColor("#e2e7e7"));
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
//editor.commit();
    return v;
    }


    public void setProgressLayout()
    {
            String progshape=sharedPreferences.getString(ProgressLoader.PROGRESS_SHAPE,"Circle");
            int wavecolor=0,bordercolor=0,backgroundcolor=0;
        wavecolor=sharedPreferences.getInt(WAVE_COLOR,0);
        bordercolor=sharedPreferences.getInt(BORDER_COLOR,0);
        backgroundcolor=sharedPreferences.getInt(BACKGROUND_COLOR,0);

            float borderwidth=3;
        borderwidth=sharedPreferences.getFloat(BORDER_WIDTH,3);

            waveLoadingView.setBorderWidth(borderwidth);
            if(wavecolor!=0)
                waveLoadingView.setWaveColor(wavecolor);
            if(bordercolor!=0)
                waveLoadingView.setBorderColor(bordercolor);
            if(backgroundcolor!=0)
                waveLoadingView.setBackgroundColor(backgroundcolor);

            Toast.makeText(getContext(),"In Progress Layout" + progshape,Toast.LENGTH_LONG).show();

            switch(progshape.trim())
            {
                case "Circle":
                {
                    waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                    break;
                }
                case "Square":
                {
                    waveLoadingView.setShapeType(WaveLoadingView.ShapeType.SQUARE);
                    break;
                }
                case "Rectangle":
                {
                    waveLoadingView.setShapeType(WaveLoadingView.ShapeType.RECTANGLE);
                    break;
                }
                case "Triangle":
                {
                    waveLoadingView.setShapeType(WaveLoadingView.ShapeType.TRIANGLE);
                    break;
                }
                default:
                {
                    waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                    break;
                }

            }
        }



}
