package com.namics.glassshowcase.nespressocapsuleinformation.imagerecognition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.namics.glassshowcase.nespressocapsuleinformation.helpers.Callable;

import java.io.ByteArrayOutputStream;


/**
 * Created by chbuehler on 14.03.14.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private Callable<Integer, Void> pixelCallback = null;
    private SurfaceHolder surfaceHolder = null;
    private Camera camera = null;

    @SuppressWarnings("deprecation")
    public CameraPreview(Context context)
    {
        super(context);

        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        camera = Camera.open();

        // Set the Hotfix for Google Glass
        this.setCameraParameters(camera);

        // Show the Camera display
        try
        {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {

                    if(pixelCallback != null){
                        Camera.Parameters parameters = camera.getParameters();
                        Camera.Size size = parameters.getPreviewSize();

                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        YuvImage yuvImage = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
                        yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 70, out);
                        byte[] imageBytes = out.toByteArray();
                        Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                        int pixelColor = image.getPixel(image.getWidth() / 2, image.getHeight() / 2);

                        pixelCallback.call(pixelColor);
                        pixelCallback = null;
                    }
                }
            });
        }
        catch (Exception e)
        {
            this.releaseCamera();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        // Start the preview for surfaceChanged
        if (camera != null)
        {
            camera.startPreview();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        // Do not hold the camera during surfaceDestroyed - view should be gone
        this.releaseCamera();
    }

    /**
     * Important HotFix for Google Glass (post-XE11) update
     * @param camera Object
     */
    public void setCameraParameters(Camera camera)
    {
        if (camera != null)
        {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewFpsRange(30000, 30000);
            camera.setParameters(parameters);
        }
    }

    /**
     * Release the camera from use
     */
    public void releaseCamera()
    {
        if (camera != null)
        {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public void getPixelColor(Callable<Integer, Void> callback){
        pixelCallback = callback;
    }

    public static void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {
        final int frameSize = width * height;

        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0; else if (r > 262143) r = 262143;
                if (g < 0) g = 0; else if (g > 262143) g = 262143;
                if (b < 0) b = 0; else if (b > 262143) b = 262143;

                rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
            }
        }
    }
}
