package test;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.core.Point;

class PeopleTrack {
	
	public static int countNewPersons(MatOfRect currentDetection, HeapList<MatOfRect> previousDetections) {
		int counter = 0;
		
		for (Rect person : currentDetection.toList()){
			if (isNewPerson(person, previousDetections)){
				counter++;
			}
		}
		
		return counter;
	}
	

	public static boolean isNewPerson(Rect person,
			HeapList<MatOfRect> previousDetections) {

		for (MatOfRect selectedDetection : previousDetections) {
			List<Rect> rectList = selectedDetection.toList();
			for (Rect previousPerson : rectList) {
				if (isNear(person, previousPerson, 0.2)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean isNear(Rect person, Rect previousPerson, double ratio) {
		Point personCenter = new Point(person.x + person.width / 2, person.y
				+ person.height / 2);
		Point previousCenter = new Point(previousPerson.x
				+ previousPerson.width / 2, previousPerson.y
				+ previousPerson.height / 2);

		double distance = Math.sqrt(Math.pow(previousCenter.x - personCenter.x,
				2) + Math.pow(previousCenter.y - personCenter.y, 2));
		double maxAllowed = Math.sqrt(Math.pow(ratio, 2)
				* (Math.pow(person.width + previousPerson.width, 2) + Math.pow(
						person.height + previousPerson.height, 2)));

		if (distance < maxAllowed) {
			return true;
		}

		return false;
	}
}
