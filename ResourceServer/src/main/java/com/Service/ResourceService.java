package com.Service;

import java.io.BufferedInputStream;
import java.io.InputStream;

public interface ResourceService {

    String uploadAudioResource(BufferedInputStream uploadFileInputStream);
}
