import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class ImgProcessor implements PlugInFilter {

	public void run(ImageProcessor ip) {

		ImageProcessor imageDuplicated = ip.duplicate(); 
		imageDuplicated.findEdges();
		
//		FFT filtro = new FFT();
//		filtro.run("resources/radiografia_mano.jpg");
		
		String Title = "Edges";
		ImagePlus imgPlus = new ImagePlus(Title, imageDuplicated);
		
		imgPlus.show();
	}

	public int setup(String arg, ImagePlus imp) {
		return DOES_ALL; 
	}
	
	public static void main(String[] args) {
		
		Opener opener = new Opener();
		
		String path = "resources/radiografia_mano.jpg";
		
		ImagePlus imgPlus = opener.openImage(path);
		
		ImgProcessor processor = new ImgProcessor();
		
		processor.run(imgPlus.getProcessor());
		
	}
}
