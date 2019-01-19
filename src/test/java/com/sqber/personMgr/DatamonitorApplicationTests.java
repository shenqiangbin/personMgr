package com.sqber.personMgr;

import com.sqber.personMgr.ui.config.FileUploadConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatamonitorApplicationTests {

    @Autowired
    private FileUploadConfig fileUploadConfig;


    @Test
    public void test1(){
        File dir = new File(fileUploadConfig.getSavePath());
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            //boolean r = dir.getParentFile().mkdirs();

        }
    }

}
