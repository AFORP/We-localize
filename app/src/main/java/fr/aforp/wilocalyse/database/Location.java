package fr.aforp.wilocalyse.database;

import fr.aforp.wilocalyse.navigation.Position;

public class Location
{
    public String building;
    public String stage;
    public String room;
    public Position position;
    public String label;

    Location(String building, String stage, String room, int x, int y, String label)
    {
        this.building = building;
        this.stage = stage;
        this.room = room;
        this.position = new Position(x, y);
        this.label = label;
    }

    Location(String building, String stage, String room, Position position, String label)
    {
        this.building = building;
        this.stage = stage;
        this.room = room;
        this.position = new Position(position);
        this.label = label;
    }

    @Override
    public String toString() {
        return "Location{" +
                "building='" + building + '\'' +
                ", stage='" + stage + '\'' +
                ", room='" + room + '\'' +
                ", position=" + position +
                ", label=" + label +
                '}';
    }
}
