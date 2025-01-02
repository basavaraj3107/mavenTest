package test;

public class GDownloadPathFinder {
	public static void main(String[] args) {
		 String os = System.getProperty("os.name").toLowerCase();
         String downloadPath;

         if (os.contains("win")) {
             downloadPath = System.getenv("USERPROFILE") + "\\Downloads";
         } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
             downloadPath = System.getProperty("user.home") + "/Downloads";
         } else {
             downloadPath = "Unsupported OS";
         }

         System.out.println("Download Path: " + downloadPath);
	}
}
