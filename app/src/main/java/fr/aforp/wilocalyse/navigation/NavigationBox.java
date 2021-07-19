package fr.aforp.wilocalyse.navigation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Arrays;

import fr.aforp.wilocalyse.App;
import fr.aforp.wilocalyse.R;
import fr.aforp.wilocalyse.algorithms.AutoCompleteAdapter;
import fr.aforp.wilocalyse.database.LocationDatabase;

// TODO enlever les éléments start* quand la géolocalisation wifi sera opérationnelle

public class NavigationBox extends DialogFragment
{
    public interface NavigationFragmentListener
    {
        void onReturnValue(Position start, Position end, String destinationTitle, String positionTitle);
    }

    private static final String TAG = "NavigationBox";
    private final LocationDatabase locationDatabase;
    private ArrayList<String> labels;
    private AutoCompleteTextView autoCompleteTextViewPosition;
    private AutoCompleteTextView autoCompleteTextViewDestination;
    private String mPosition;
    private String mDestination;
    private Chip chip_reception;
    private Chip chip_hall;
    private Chip chip_workcoffee;
    private Chip chip_building_cd;
    private Chip chip_parking;
    private Chip chip_delivery;
    private Chip chip_reception_dest;
    private Chip chip_hall_dest;
    private Chip chip_workcoffee_dest;
    private Chip chip_building_cd_dest;
    private Chip chip_parking_dest;
    private Chip chip_delivery_dest;

    public NavigationBox(LocationDatabase locationDatabase)
    {
        this.locationDatabase = locationDatabase;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mPosition = "";
        mDestination = "";


        labels = locationDatabase.getLabel();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.popup_route_configurator, null, false);
        initSpinner(dialog, labels);
        builder.setView(dialog);
        final AlertDialog alertDialog = builder.create();
        Button buttonEnter = dialog.findViewById(R.id.btEnter);
        TextView tv_secure = dialog.findViewById(R.id.tv_secure);
        tv_secure.setText("");
        initChipButton(dialog);
        buttonEnter.setOnClickListener(v -> {
            if (mPosition != "" && mDestination != "") {
                if (mPosition != mDestination) {
                    NavigationFragmentListener activity = (NavigationFragmentListener) getActivity();
                    Position start = locationDatabase.getPosition(mPosition);
                    Position end = locationDatabase.getPosition(mDestination);
                    Log.i("req", "pos : " + start.x + "-" + start.y + " dest : " + end.x + "-" + end.y);
                    activity.onReturnValue(start, end, mDestination, mPosition);
                    dismiss();
                }
                else {
                    tv_secure.setText("Vous êtes déjà arrivé à destination");
                }
            }
            else {
                tv_secure.setText("Veuillez saisir votre position et votre destination");
            }

        });
        Button buttonClose = dialog.findViewById(R.id.btClose);
        buttonClose.setOnClickListener(v -> {
            dismiss();
        });
        return alertDialog;
    }

    private void initSpinner(View view, ArrayList<String> labels)
    {
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(App.getContext(),
                android.R.layout.select_dialog_item, android.R.id.text1, labels);

        autoCompleteTextViewPosition = view.findViewById(R.id.autoCompleteTextViewPosition);
        autoCompleteTextViewPosition.setAdapter(adapter);
        autoCompleteTextViewPosition.setOnItemClickListener((parent, arg1, position, arg3) -> {
            mPosition = "";
            Object item = parent.getItemAtPosition(position);
            mPosition = item.toString();
        });

        autoCompleteTextViewDestination = view.findViewById(R.id.autoCompleteTextViewDestination);
        autoCompleteTextViewDestination.setAdapter(adapter);
        autoCompleteTextViewDestination.setOnItemClickListener((parent, arg1, position, arg3) -> {
            mDestination = "";
            Object item = parent.getItemAtPosition(position);
            mDestination = item.toString();
        });
    }

    private void initChipButton (View v)
    {
        chip_reception = v.findViewById(R.id.chip_reception);
        chip_reception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = "Accueil";
                autoCompleteTextViewPosition.setText(mPosition);
            }
        });
        chip_reception_dest = v.findViewById(R.id.chip_reception_destination);
        chip_reception_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDestination = "Accueil";
                autoCompleteTextViewDestination.setText(mDestination);
            }
        });

        chip_hall = v.findViewById(R.id.chip_hall);
        chip_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = "Hall";
                autoCompleteTextViewPosition.setText(mPosition);
            }
        });
        chip_hall_dest = v.findViewById(R.id.chip_hall_destination);
        chip_hall_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDestination = "Hall";
                autoCompleteTextViewDestination.setText(mDestination);
            }
        });

        chip_workcoffee = v.findViewById(R.id.chip_workcoffee);
        chip_workcoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = "Work Café";
                autoCompleteTextViewPosition.setText(mPosition);
            }
        });
        chip_workcoffee_dest = v.findViewById(R.id.chip_workcoffee_destination);
        chip_workcoffee_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDestination = "Work Café";
                autoCompleteTextViewDestination.setText(mDestination);
            }
        });

        chip_building_cd = v.findViewById(R.id.chip_building_cd);
        chip_building_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = "Accès Bât. C / D";
                autoCompleteTextViewPosition.setText(mPosition);
            }
        });
        chip_building_cd_dest = v.findViewById(R.id.chip_building_cd_destination);
        chip_building_cd_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDestination = "Accès Bât. C / D";
                autoCompleteTextViewDestination.setText(mDestination);
            }
        });

        chip_parking = v.findViewById(R.id.chip_parking);
        chip_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = "Parking";
                autoCompleteTextViewPosition.setText(mPosition);
            }
        });
        chip_parking_dest = v.findViewById(R.id.chip_parking_destination);
        chip_parking_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDestination = "Parking";
                autoCompleteTextViewDestination.setText(mDestination);
            }
        });

        chip_delivery = v.findViewById(R.id.chip_delivery);
        chip_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = "Bât. D - Livraison";
                autoCompleteTextViewPosition.setText(mPosition);
            }
        });
        chip_delivery_dest = v.findViewById(R.id.chip_delivery_destination);
        chip_delivery_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDestination = "Bât. D - Livraison";
                autoCompleteTextViewDestination.setText(mDestination);
            }
        });
    }
}