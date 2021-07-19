package fr.aforp.wilocalyse.navigation;

import fr.aforp.wilocalyse.App;
import fr.aforp.wilocalyse.R;

public class Indicator
{
    private double patM=1.5;

    public class Indication
    {
        public String iconPath;
        public String text;

        Indication(int iconId, String text)
        {
            this.iconPath = String.valueOf(iconId);
            this.text = text;
        }
    }

    public Indication getGoAhead(int dist)
    {
        int feet=(int) Math.ceil(dist*this.patM);
        return new Indication(R.drawable.location_destination,  App.getContext().getResources().getString(R.string.go_straight) + " "+dist+"m"+", ("+feet+" "+App.getContext().getResources().getString(R.string.foots)+")");
    }

    public Indication getGoBack(int dist)
    {
        return new Indication(R.drawable.arrow_up,  App.getContext().getResources().getString(R.string.go_back));
    }

    public Indication getTurnRight(int dist)
    {
        int feet=(int) Math.ceil(dist*this.patM);
        return new Indication(R.drawable.arrow_rigth,  App.getContext().getResources().getString(R.string.turn_right) + " "+dist+"m"+", ("+feet+" "+App.getContext().getResources().getString(R.string.foots)+")");
    }

    public Indication getTurnLeft(int dist)
    {
        int feet=(int) Math.ceil(dist*this.patM);
        return new Indication(R.drawable.arrow_left, App.getContext().getResources().getString(R.string.turn_left) + " "+dist+"m"+", ("+feet+" "+App.getContext().getResources().getString(R.string.foots)+")");
    }

    public Indication getStairs(int dist)
    {
        return new Indication(R.drawable.stairs, App.getContext().getResources().getString(R.string.take_the_stairs) );
    }



    // etc.
}