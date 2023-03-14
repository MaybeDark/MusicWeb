package com.tools;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.audio.mp3.MP3FileReader;
import org.jaudiotagger.tag.TagException;

import java.io.*;


public class AudioTools {
    public static String mp3FileHeader =  "49443303";
    public static String getVideoDuration(String FilePath) {
        String strLen = "00:00";
        try {
            MP3File file = new MP3File(FilePath);
            MP3AudioHeader audioHeader = (MP3AudioHeader) file.getAudioHeader();
            strLen = audioHeader.getTrackLengthAsString();
            return strLen;
        } catch (TagException | InvalidAudioFrameException | ReadOnlyFileException | IOException e) {
            return strLen;
        }
    }
    public static String getVideoDuration(File file){
        MP3FileReader mp3FileReader = new MP3FileReader();
       String strLen = "00:00";
        try {
            AudioFile mp3File=  mp3FileReader.read(file);
            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
            strLen = audioHeader.getTrackLengthAsString();
            return strLen;
        } catch (IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            return strLen;
        }
    }

    public static boolean isMP4(BufferedInputStream fileInputStream){
        return FileTools.getFileHeader(fileInputStream).equals(mp3FileHeader);
    }

    public static boolean isMp4(byte[] header){
        StringBuilder builder = new StringBuilder();
        String hv;
        for (byte value : header) {
            hv = Integer.toHexString(value & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString().equals(mp3FileHeader);
    }
}
