package com.namics.glassshowcase.colorrecognition.controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;


/**
 * Created by chbuehler on 14.03.14.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder surfaceHolder = null;
    private Camera camera = null;

    public int pixelColor;

    public Callable<Void> getFrameCallback() {
        return frameCallback;
    }

    public void setFrameCallback(Callable<Void> frameCallback) {
        this.frameCallback = frameCallback;
    }

    private Callable<Void> frameCallback;

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
                    Camera.Parameters parameters = camera.getParameters();
                    Camera.Size size = parameters.getPreviewSize();

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    YuvImage yuvImage = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
                    yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 70, out);
                    byte[] imageBytes = out.toByteArray();
                    Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                    pixelColor = image.getPixel(image.getWidth() / 2, image.getHeight() / 2);
                    if(frameCallback != null){
                        try{
                            frameCallback.call();
                        } catch (Exception e){

                        }
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
}
