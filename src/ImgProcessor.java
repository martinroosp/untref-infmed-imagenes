import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.FFTFilter;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.util.Arrays;

public class ImgProcessor implements PlugInFilter {

	@Override
	public void run(ImageProcessor ip) {

		ImageProcessor imageDuplicated = ip.duplicate();
		getHistogram(imageDuplicated);
		imageDuplicated.findEdges();
//		FFTFilter filtro = new FFTFilter();
//		filtro.run(imageDuplicated);
		String Title = "Edges";
		ImagePlus imgPlus = new ImagePlus(Title, imageDuplicated);
		imgPlus.show();
	}

	@Override
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

	public int[] getHistogram(ImageProcessor ip) {

		int[] histograma = ip.getHistogram();
		System.out.println("Histograma: " + Arrays.toString(histograma));
		return histograma;
	}
}
