import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class barcodedecode {

	public static void main(String[] args) throws IOException, NotFoundException, ChecksumException, FormatException {
		String imageurl = "C:\\temp\\qrcode.jpg";
		BufferedImage img = ImageIO.read(new File(imageurl));
		LuminanceSource source = new BufferedImageLuminanceSource(img);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		
	    final Reader reader = new QRCodeReader();
	    Result qrcode = reader.decode(bitmap);
	    String qrtext = qrcode.getText();

	}

}
