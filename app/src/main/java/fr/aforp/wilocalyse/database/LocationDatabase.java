package fr.aforp.wilocalyse.database;

import java.util.ArrayList;
import java.util.Objects;

import fr.aforp.wilocalyse.navigation.Position;

public class LocationDatabase
{
    private ArrayList<Location> locations;

    public LocationDatabase(ArrayList<Location> locations)
    {

        this.locations = locations;
    }

    public ArrayList<String> getBuildings()
    {
        ArrayList<String> buildings = new ArrayList<>();
        for (Location location : locations)
        {
            if (!buildings.contains(location.building)) {
                buildings.add(location.building);
            }
        }
        return buildings;
    }

    public ArrayList<String> getStages(String building)
    {
        ArrayList<String> stages = new ArrayList<>();
        for (Location location : locations)
        {
            if (location.building.equals(building) && !stages.contains(location.stage))
            {
                stages.add(location.stage);
            }
        }
        return stages;
    }

    public ArrayList<String> getRooms(String building, String stage)
    {
        ArrayList<String> rooms = new ArrayList<>();
        for (Location location : locations)
        {
            if (location.building.equals(building) && location.stage.equals(stage) && !rooms.contains(location.room))
            {
                rooms.add(location.room);
            }
        }
        return rooms;
    }

    public Position getPosition(String label)
    {
        for (Location location : locations)
        {
            if (location.label.equals(label))
            {
                return location.position;
            }
        }
        return null;
    }

    public ArrayList<String> getLabel()
    {
        ArrayList<String> labels = new ArrayList<>();
        for (Location location : locations)
        {
            if (location.position.x != 0 && location.position.y != 0 ) {
                labels.add(location.label);
            }
        }
        return labels;
    }
}
