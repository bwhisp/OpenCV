package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

public class FaceDetectorVid {
	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");

		CascadeClassifier faceDetector = new CascadeClassifier(
				"/home/bwhisp/Resource/haarcascade.xml");
		VideoCapture videoCapture = new VideoCapture("/home/bwhisp/Resource/federer.mp4");

		Size frameSize = new Size(
				(int) videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH),
				(int) videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT));

		System.out.println(String.format("Resolution %s ", frameSize));
		FourCC fourCC = new FourCC("X264");
		VideoWriter videoWriter = new VideoWriter("/home/bwhisp/softwares/install-opencv/output/faceDetect.avi",
				fourCC.toInt(), videoCapture.get(Videoio.CAP_PROP_FPS),
				frameSize, true);
		Mat mat = new Mat();
		int frames = 0;
		int faces = 0;
		while (videoCapture.read(mat)) {
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(mat, faceDetections);
			for (Rect rect : faceDetections.toArray()) {
				Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(
						rect.x + rect.width, rect.y + rect.height), new Scalar(
						0, 255, 0));
			}
			videoWriter.write(mat);
			faces = faces + faceDetections.toArray().length;
			frames++;
		}

		System.out.println(String.format("Detected %d faces", faces));

		System.out.println(String.format("frames %d", frames));
	}
}