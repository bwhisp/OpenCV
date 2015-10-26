package test;

public class Benchmark {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = null;
		final String outputFile = "/home/bwhisp/softwares/install-opencv/output/walking.avi";
		// Check how many arguments were passed in
		if (args.length == 0) {
			// If no arguments were passed then default to local file
			url = "/home/bwhisp/Resource/walking.mp4";
		} else {
			url = args[0];
		}
		
		PeopleDetect.detect(url, outputFile);
		/* 1 . Liste de fichiers
		 * 2 . Lancer le people detect comme une méthode avec input/output
		 * */
	}

}
