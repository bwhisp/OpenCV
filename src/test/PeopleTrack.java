package test;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.core.Point;

class PeopleTrack {

	public static int countNewPersons(MatOfRect currentDetection,
			HeapList<MatOfRect> previousDetections) {
		int counter = 0;

		for (Rect person : currentDetection.toList()) {
			if (isNewPerson(person, previousDetections)) {
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
				if (isNear(person, previousPerson, 0.5)) {
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

		Point maxAllowed = new Point(ratio
				* (person.width + previousPerson.width) / 2, ratio
				* (person.height + previousPerson.height) / 2);
		
		Point movement = new Point(previousCenter.x - personCenter.x,
				previousCenter.y - personCenter.y);

		if (movement.x < maxAllowed.x && movement.y < maxAllowed.y) {
			return true;
		}

		return false;
	}
}
