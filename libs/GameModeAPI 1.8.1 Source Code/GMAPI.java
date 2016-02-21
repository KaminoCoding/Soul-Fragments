// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

package net.minecraft.src;

import net.minecraft.client.Minecraft;

import java.io.*;
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.jar.Attributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// Referenced classes of package net.minecraft.src:
//            Tessellator, FontRenderer

public class GMAPI
{
    private static void MakeList()
    {
       playerController = new PlayerController[100];
       GMNameList.add(0,"Survival");
       GMIDList.add(0, 0);
       GMNameList.add(1,"Classic");
       GMIDList.add(1, 1);
       for( int i = 2; 100 >= i;i++)
        {
            GMNameList.add(i, null);
            GMIDList.add(i, null);
        }
        GMLine1 = new String[100];
        GMLine2 = new String[100];
    }
    public static void AddGamemodeLine1(int GMID, String Line)
    {
       GMLine1[GMID] = Line;
    }
     public static void AddGamemodeLine2(int GMID, String Line)
    {
       GMLine2[GMID] = Line;
    }
    public static void AddGamemode(int GMID, String GMName, PlayerController GMPC)
    {
     if(ML)
     {
         ML = false;
         MakeList();
     }
        if(GMID >= 100 || GMID <= 1)
        {
            System.out.println("Gamemode error - Name: " + GMName + ",PlayerController: " + GMPC+", ID: INVALID ID.");
            return;
        }
       int GMId = GMID;
       GMNameList.add(GMId,GMName);
       GMIDList.add(GMId,GMId);
      GMAPI.playerController[GMID] = GMPC;
       System.out.println("Gamemode added - Name: " + GMName + ",PlayerController: " + GMPC+", ID:" + GMId + ".");
    }
   public static String GMLine1[];
   public static String GMLine2[];
   public static PlayerController playerController[];
   public static boolean NeedGM = false;
   public static int GM = -1;
   public static final String GetVersion = "Gamemode API 1.0";
   private static boolean ML = true;
   public static List GMNameList = new ArrayList(),
                        GMIDList = new ArrayList();
}
