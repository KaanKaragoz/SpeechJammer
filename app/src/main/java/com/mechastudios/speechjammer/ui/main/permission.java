package com.mechastudios.speechjammer.ui.main;

import android.Manifest;

public class permission {
    public static String[] permission(){
        String[] dizi = new String[] {Manifest.permission.RECORD_AUDIO};
        return dizi;
    }
}
