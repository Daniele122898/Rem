package com.serenity.rem.modules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    public static String[] remPics;
    public static String[] coinPics;
    public static List<File> remFiles = new ArrayList<>();
    public static List<File> coinFiles = new ArrayList<>();
    public static File morningRem;

    /*
    * File[] files = new File[remPics.lenght];;
for(int i=0; I<remPics.length;  I++) {
files[i] = new File(remPics[i]);
}
ArrayList<File> files = new ArrayList<>;
for(String file : remPics) {
files.add(file);
}
List<File>
 List<ulong> mywhiteList = new List<ulong>();




 List<String> stringList = new ArrayList<>();
//old
for (int i = 0; i < stringList.size(); i++) {
  String element = stringList.get(i);
  com.serenity.rem.modules.SystemRem.out.println(element);
}
//new
for (String element : stringList) {
  com.serenity.rem.modules.SystemRem.out.println(element);
}


*/
    public static void storage(){
        remPics = new String[] {
                "resources/rempics/rem1.gif", "resources/rempics/rem2.png", "resources/rempics/rem3.jpg", "resources/rempics/rem4.jpg", "resources/rempics/rem5.jpg",
                "resources/rempics/rem6.png", "resources/rempics/rem7.jpg", "resources/rempics/rem8.png", "resources/rempics/rem9.jpg", "resources/rempics/rem10.png",
                "resources/rempics/rem11.jpg", "resources/rempics/rem12.jpg", "resources/rempics/rem13.jpg", "resources/rempics/rem14.png", "resources/rempics/rem15.jpg",
                "resources/rempics/rem16.jpg", "resources/rempics/rem17.jpg", "resources/rempics/rem18.jpg", "resources/rempics/rem19.jpg", "resources/rempics/rem20.png",
                "resources/rempics/rem21.png", "resources/rempics/rem22.jpg"
        };
        coinPics = new String[]{
            "resources/coin/obverse.png" ,"resources/coin/reverse.png"
        };
        for(String coinPic : coinPics){
            coinFiles.add(new File(coinPic));
        }


        for (String remPic : remPics) {
            remFiles.add(new File(remPic));
        }
        for (int i=0; i<remPics.length; i++) {
            if (!remFiles.get(i).exists())
                System.out.println("File " +i+ " doesnt exist");
        }

        morningRem = new File("resources/rempics/morning.jpg");

    }
}
