package fr.aforp.wilocalyse.navigation;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;

import fr.aforp.wilocalyse.R;
import fr.aforp.wilocalyse.Texttospeechaudio;
import fr.aforp.wilocalyse.algorithms.PathFinder;
import fr.aforp.wilocalyse.database.LocationDatabase;
import fr.aforp.wilocalyse.database.LocationLoader;
import fr.aforp.wilocalyse.database.MatrixLoader;

public class MapActivity extends AppCompatActivity implements NavigationBox.NavigationFragmentListener {
    private static String TAG = "MapActivity";
    private FloatingActionButton buttonSearch;
    private PhotoView imageViewMap;
    private Canvas mapCanvas;
    private Bitmap drawableBitmapCopy;
    private Bitmap cleanBitmapCopy;
    private int[][] matrixMap;
    private Paint p;
    private PathFinder pathFinder = new PathFinder();
    private PathDrawer pathDrawer = new PathDrawer();
    private TextView tvDestinationMap;
    private TextView tvBottomSheetFloor;
    private ListView stepListView;
    private SimpleAdapter stepAdapter;
    private View bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private Texttospeechaudio audio_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
    }

    private void init() {
        initNavigationBox();
        initMatrixMap();
        initBitmap();
        initBottomSheet();
        audio_map = new Texttospeechaudio(this);
    }

    private void initNavigationBox() {
        LocationLoader loader = new LocationLoader();
        LocationDatabase locationDatabase = new LocationDatabase(loader.load(this));
        buttonSearch = findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NavigationBox box = new NavigationBox(locationDatabase);
            box.show(fragmentManager, TAG);
        });
    }

    private void initMatrixMap() {
        MatrixLoader loader = new MatrixLoader();
        matrixMap = loader.load(this);
    }

    private void initBitmap() {
        imageViewMap = findViewById(R.id.imgview_map);
        BitmapDrawable drawable = (BitmapDrawable) imageViewMap.getDrawable();
        if (drawable == null) {
            drawable = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.map_rdc, null);
        }
        Bitmap bitmap = drawable.getBitmap();
        drawableBitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        cleanBitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        mapCanvas = new Canvas(drawableBitmapCopy);
        imageViewMap.setAllowParentInterceptOnEdge(false);
        imageViewMap.setImageBitmap(drawableBitmapCopy);
    }

    private void initBottomSheet() {

        LinearLayout dragBar = findViewById(R.id.dragBar);
        dragBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
              } else {
                  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
              }
            }
        });

        tvDestinationMap = findViewById(R.id.tv_map_destination);
        tvBottomSheetFloor = findViewById(R.id.tv_bottom_sheet_floor);
        stepListView = findViewById(R.id.step_listView);
        bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        Resources r = getResources();
        int pxPeekHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, r.getDisplayMetrics()));
        bottomSheetBehavior.setPeekHeight(pxPeekHeight, true);
        bottomSheetBehavior.setDraggable(false);
    }

    private void clearBitmap() {
        drawableBitmapCopy = cleanBitmapCopy.copy(Bitmap.Config.ARGB_8888, true);
        mapCanvas.setBitmap(drawableBitmapCopy);
        imageViewMap.setImageBitmap(drawableBitmapCopy);
    }

    private void updateBitmap() {
        imageViewMap.setImageBitmap(drawableBitmapCopy);
    }

    @Override
    public void onReturnValue(Position start, Position end, String destinationTitle, String positionTitle) {
        clearBitmap();
        Log.i("road", "pos : "+start.x + "-" +start.y + " dest : " + end.x + "-" + end.y );
        ArrayList<Integer[]> road = pathFinder.findPath(matrixMap, start.x, start.y, end.x, end.y);
        ArrayList<HashMap<String, String>> roadStep = pathFinder.getRoadStep();
        Log.i("size activity"," "+roadStep.size());
        Log.i("size activity", "road" + roadStep.toString());
        stepAdapter = new SimpleAdapter(this.getBaseContext(), roadStep, R.layout.custom_step_listview,
                new String[]{"img", "titre"}, new int[]{R.id.icon_custom_list, R.id.tv_custom_list});
        stepListView.setAdapter(stepAdapter);

        Resources r = getResources();
        int pxPeekHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, r.getDisplayMetrics()));
        bottomSheetBehavior.setPeekHeight(pxPeekHeight, true);
        bottomSheetBehavior.setDraggable(true);

        tvDestinationMap.setText(destinationTitle);
        tvBottomSheetFloor.setText("RDC");

        pathDrawer.drawOnMap(road, mapCanvas, start.x, start.y, end.x, end.y);
        setRoadIcons(mapCanvas, road);
        updateBitmap();
        audio_map.speakOut(getString(R.string.choose_destination) + " " + audio_map.updateformattolocalisation(destinationTitle));
    }

    private void setRoadIcons(Canvas mapCanvas, ArrayList<Integer[]> road) {
        final int size = road.size() - 1;
        final Position startOffset = new Position(-50, -60);
        final Position endOffset = new Position(-45, -100);
        final int startRoadX = road.get(0)[1] + startOffset.x;
        final int startRoadY = road.get(0)[0] + startOffset.y;
        final int endRoadX = road.get(size)[1] + endOffset.x;
        final int endRoadY = road.get(size)[0] + endOffset.y;
        Bitmap bend = BitmapFactory.decodeResource(getResources(), R.drawable.location_destination);
        Bitmap rbend = Bitmap.createScaledBitmap(
                bend, 264, 290, false);
        Bitmap bstart = BitmapFactory.decodeResource(getResources(), R.drawable.location_start);
        Bitmap rbstart = Bitmap.createScaledBitmap(
                bstart, 290, 290, false);
        mapCanvas.drawBitmap(rbend, endRoadX, endRoadY, p);
        mapCanvas.drawBitmap(rbstart, startRoadX, startRoadY, p);
    }

    @Override
    public void onDestroy() {
        audio_map.onDestroy();
        super.onDestroy();
    }
}