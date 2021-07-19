package fr.aforp.wilocalyse.database;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class LocationLoader
{
    private String TAG = "LocationLoader";
    private String csvSeparator = ";";

    public ArrayList<Location> load(Context context)
    {
        ArrayList<Location> locations = new ArrayList<Location>();
        try
        {
            InputStreamReader input = new InputStreamReader(context.getAssets().open("lieux.csv"));
            BufferedReader reader = new BufferedReader(input);
            String line = reader.readLine();
            while (line != null)
            {
                locations.add(createLocation(line));
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return locations;
    }

    private Location createLocation(String line)
    {
        String[] parts = line.split(csvSeparator);
        Location location = new Location(
                parts[0],
                parts[1],
                parts[2],
                Integer.parseInt(parts[4]),
                Integer.parseInt(parts[5]),
                parts[6]);
        return location;
    }

}
