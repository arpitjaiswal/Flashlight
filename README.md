# Flashlight
Flashlight - Version 1.0

<article>
A very simple app to utilize your device's flash as a torch. It requires the minimal permission to access the hardware, and is very small in size.

The project is also very tiny and concise, and I've tried to make it as explanatory as possible.

It performs three simple steps to start the flash:
  <section>
  <h5>Get Camera Reference</h5>
  If the device has camera flash feature, then get a reference to the camera, and its parameters.
  <pre>
  if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
    camera = Camera.open();
    params = cam.getParameters();
  }
  </pre>
  </section>
  
  <section>
  <h5>Set TORCH parameter</h5>
  Update the <blackquote>'params'</blackquote> by setting the flash mode to TORCH, and apply the modified parameters to camera reference.
  <pre>
  params.setFlashMode(Parameters.FLASH_MODE_TORCH);
  cam.setParameters(params);
  </pre>
  </section>
  
  <section>
  <h5>Start the Camera</h5>
  Now, simply start the camera, and the flash will turn on.
  <pre>
  cam.startPreview();
  </pre>
  </section>
</article>

<article>
<h4>Improvements</h4>
<p>Currently, the app is using the now deprecated <a href="http://developer.android.com/reference/android/hardware/Camera.html" target="_blank">Camera</a> library. So the first improvement that is to be applied is using the new <a href="http://developer.android.com/reference/android/hardware/Camera.html" target="_blank">Camera2 API</a>. The problem with this package is that it not backward compatible and thus only can be used for Lollipop and newer versions.</p>
<p>Next, and much more important improvement that can be done is to make it more pleasent looking, i.e. improving the UI. </p>
<p>Third, the app has not been thoroughly tested, as it was made in a haste, so I am currently looking forward to test the app repeatedly.</p>
</article>
