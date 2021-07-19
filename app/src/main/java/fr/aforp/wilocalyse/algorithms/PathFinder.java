package fr.aforp.wilocalyse.algorithms;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math.*;
import java.util.Hashtable;
import java.util.LinkedList;

import fr.aforp.wilocalyse.R;
import fr.aforp.wilocalyse.algorithms.GFG;
import fr.aforp.wilocalyse.navigation.Indicator;

import static java.lang.Character.getNumericValue;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.atan;
import static java.lang.Math.atan2;
import static java.lang.Math.ceil;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

public class PathFinder {

    ArrayList<HashMap<String, String>> roadStep = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    Indicator indicator = new Indicator();

    public PathFinder() {

    }

    public ArrayList<HashMap<String, String>> getRoadStep() {
        Log.i("size"," "+roadStep.size());return roadStep;
    }
    private int distance(GFG.Point A, GFG.Point B){
        return (int) ceil(sqrt(pow(B.y-A.y,2)+pow(B.x-A.x,2))*0.06);
    }
    // Calculer un chemin avec matrice, point de depart et point d'arrivee
    public ArrayList findPath(int[][] mat, int Xstart, int Ystart, int Xend, int Yend) {
        roadStep.clear();
        ArrayList<Integer[]> road = new ArrayList<Integer[]>();
        GFG.Point src = new GFG.Point(Xstart,Ystart);
        GFG.Point dest = new GFG.Point(Xend,Yend);
        LinkedList<GFG.Point> pointBleu= new  LinkedList<GFG.Point>();
        int i = GFG.BFS(mat,src,dest);
        int x = src.x;
        int y = src.y;

        Integer[] stepSrc={src.x,src.y},stepDest={dest.x,dest.y};

        road.add(stepSrc);
        pointBleu.add(src);
        Log.i("point bleu (angle)",src.toString());
        int lastPlace=0;
        double a=0;
        while(!GFG.pathQ.isEmpty()) {
            GFG.Point p = GFG.pathQ.peek();
            Integer[] point = {p.x, p.y};
            
            road.add(point);

           if(mat[p.x][p.y]== 2&& lastPlace!= 2) {
                pointBleu.add(p);
                lastPlace=2;
               Log.i("point bleu (angle)",p.toString());


           }else if(mat[p.x][p.y]!= 2)
               lastPlace=0;
            x=p.x;
            y=p.y;

            GFG.pathQ.remove();
        }

        Log.i("point bleu (angle)",dest.toString());
        pointBleu.add(dest);
       this.instruction(pointBleu);
        road.add(stepDest);
        return road;
    }
    private int determinant( GFG.Point u, GFG.Point v){
        return u.x*v.y-u.y*v.x;
    }
    private  GFG.Point stage2vector( GFG.Point start_stage, GFG.Point end_stage){
        GFG.Point p=new GFG.Point(start_stage.x-end_stage.x,start_stage.y-end_stage.y) ;
        return p;
    }
    private double computeAngle( GFG.Point u,  GFG.Point v){
        int prodScal=u.x*v.x+u.y*v.y;
        double normeU=sqrt(u.x*u.x+u.y*u.y);
        double normeV=sqrt(v.x*v.x+v.y*v.y);
        int det=determinant(u,v);
        double angle=toDegrees(acos(prodScal/(normeU*normeV)));

        if (det>=0){
            return 360-angle;
        }

        return angle;
    }
    private void instruction( LinkedList<GFG.Point> pointBleu){
        GFG.Point starting_stage = pointBleu.getFirst();
        GFG.Point end_stage = pointBleu.getLast();
        GFG.Point previous_stage=starting_stage;
        hashMap = new HashMap<String, String>();
        Indicator.Indication ind;
        ind = indicator.getGoBack(0);
        /*hashMap.put("titre", ind.text);
        hashMap.put("img", ind.iconPath);
        roadStep.add(hashMap);*/
        int dist=0,sum=0;

        for(int i=1;i<pointBleu.size()-1;i++){
            GFG.Point next_stage=pointBleu.get(i+1);
            GFG.Point stage = pointBleu.get(i);
            GFG.Point u = stage2vector(previous_stage,stage);
            GFG.Point v = stage2vector(next_stage,stage);
            double angle = computeAngle(u,v);
            int c=distance(previous_stage,stage);//previous to stage

            Log.i("angle +point","i "+i+" angle : "+angle+" depart "+previous_stage.toString()+" courant "+ pointBleu.get(i).toString()+" suivant "+next_stage.toString()+ "" +
                    "distance c "+ c);
            if(0<=angle&&angle<150){
                dist=sum+c;
                sum=0;
                hashMap = new HashMap<String, String>();
                ind = indicator.getTurnLeft(dist);
                hashMap.put("titre", ind.text);
                hashMap.put("img", ind.iconPath);
                roadStep.add(hashMap);
                Log.i("angle","gauche");

            }else if(150<=angle&&angle<=210){
                hashMap = new HashMap<String, String>();
                sum=sum+c;
                ind = indicator.getGoAhead(dist);
                Log.i("angle","droit");
            }else if(210<angle&&angle<=360){

                dist=sum+c;
                sum=0;
                hashMap = new HashMap<String, String>();
                ind = indicator.getTurnRight(dist);
                hashMap.put("titre", ind.text);
                hashMap.put("img", ind.iconPath);
                roadStep.add(hashMap);
                Log.i("angle","droite");
            }
            previous_stage=stage;

        }
        if(sum!=0){
            hashMap = new HashMap<String, String>();
            ind = indicator.getGoAhead(sum);
            hashMap.put("titre", ind.text);
            hashMap.put("img", ind.iconPath);
            roadStep.add(hashMap);
        }
        else{
            hashMap = new HashMap<String, String>();
            ind = indicator.getGoAhead(sum);
            hashMap.put("titre", "vous êtes arrivé");
            hashMap.put("img", ind.iconPath);
            roadStep.add(hashMap);
        }


    }


}
