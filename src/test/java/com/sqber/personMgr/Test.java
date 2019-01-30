package com.sqber.personMgr;

import java.io.File;

public class Test {

    public static void main(String[] args){

        File dir = new File("c:/personMgr/upload");
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            //boolean r = dir.getParentFile().mkdirs();

        }

    }

}
