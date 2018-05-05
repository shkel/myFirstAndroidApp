package com.example.galleryn.galleryn;

import com.example.galleryn.galleryn.utils.GalleryUtils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class GalleryUtilsTest {
    @Test
    public void getImagesPerScreen() {
        int[] actual = {
                GalleryUtils.getImagesPerScreen(0, 0, 0, 0),
                GalleryUtils.getImagesPerScreen(2, 3.0f, 300.0f, 567),
                GalleryUtils.getImagesPerScreen(14, 3.0f, 300.0f, 567),
        };
        int[] expected = {0, 12, 14};
        assertArrayEquals("Wrong images per screen value", expected, actual);
    }
}
