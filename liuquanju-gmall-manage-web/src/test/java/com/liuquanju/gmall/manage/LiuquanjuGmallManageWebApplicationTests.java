package com.liuquanju.gmall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiuquanjuGmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {

        String path = LiuquanjuGmallManageWebApplicationTests.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(path);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String filePath = "D:\\Download\\picture\\75affff268abc7f3437559fb859973b9.jpg";
        String[] uploadFileInfo = storageClient.upload_file(filePath, "jpg", null);
        String url = "http://192.168.11.128/";
        for (int i = 0; i < uploadFileInfo.length; i++) {
            url += uploadFileInfo[i] + "/";
            System.out.println(url);
        }

    }

}
