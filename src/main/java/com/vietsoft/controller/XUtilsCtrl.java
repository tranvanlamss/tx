package com.vietsoft.controller;

import com.vietsoft.common.CaptchaResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/utils")
public class XUtilsCtrl {
	private static Logger logger = LoggerFactory.getLogger(XUtilsCtrl.class);
	private final SecureRandom random = new SecureRandom();
	private final Color colorFrom = Color.WHITE;
	private final Color colorTo = Color.WHITE;
	private Color noiseColor;
	private float noiseWidth;
	private final int imgWidth = 100;
	private final int imgHeight = 32;
	private final List<Color> randomColorFont = new ArrayList<Color>();
	private final List<Color> randomColorNoise = new ArrayList<Color>();
	private final List<Color> randomColorDotNoise = new ArrayList<Color>();
	private final List<Font> randomFont = new ArrayList<Font>();
	private final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
	private final GradientPaint ytow = new GradientPaint(0, 0, colorFrom, this.imgWidth, this.imgHeight, colorTo);
	private final Rectangle2D rectangle2D = new Rectangle2D.Double(0, 0, this.imgWidth, this.imgHeight);
	private static final double YOFFSET = 0.25;
	private static final double XOFFSET = 0.02;
	// private final char[] RANDOM_CHAR =
	// "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	private final char[] RANDOM_CHAR = "0123456789".toCharArray();
//
//	@RequestMapping(value = { "/captcha" }, method = RequestMethod.GET)
//	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String captkey = request.getParameter("captkey");
//		String imgType = "png";
//		//Fix issue: Cannot create a session after the response has been committed
//		UserUtil.setCapt(captkey, null);
//		response.setContentType("image/png");
//		logger.info("Generating Captcha Image, key: {}, type: {}", captkey, imgType);
//		OutputStream out = new BufferedOutputStream(response.getOutputStream());
//		try {
//			CaptchaResult answer = generateCaptcha(imgType, out);
//			logger.info("Set Captcha Image, key: {}, type: {}", captkey, imgType);
//			UserUtil.setCapt(captkey, answer);
//		} catch (Throwable e) {
//			logger.error("Server Error {}", e.getMessage());
//			//response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server exception");
//		} finally {
//			try {
//				if (out != null) {
//					out.close();
//					out.flush();
//				}
//			} catch (Exception e2) {
//			}
//		}
//	}

//	@RequestMapping(value = { "/check" }, method = RequestMethod.GET)
//	public void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String captkey = request.getParameter("captkey");
//		String captcha = request.getParameter("captcha");
//		logger.info("Captcha: {}, {}", captkey, captcha);
//		int captMatched = UserUtil.checkCapt(captkey, captcha);
//		logger.info("Captcha is valid? {}, {}", captMatched, (captMatched == 0 ? "YES" : "NO"));
//		if (captMatched == 0) {
//			logger.info("Captcha is OK");
//			response.getWriter().write("Captcha is OK");
//			response.getWriter().flush();
//		} else {
//			logger.info("Captcha does NOT matched");
//			if (captMatched == 1) {
//				logger.info("Captcha does NOT matched");
//				response.getWriter().write("Captcha does NOT matched");
//				response.getWriter().flush();
//			} else {
//				logger.info("Captcha is expired");
//				response.getWriter().write("Captcha is expired");
//				response.getWriter().flush();
//			}
//		}
//	}

	private void drawingConfig() {
		if (randomColorNoise.isEmpty()) {
			randomColorNoise.add(Color.WHITE);
			randomColorDotNoise.add(Color.YELLOW);
			randomColorDotNoise.add(Color.ORANGE);
			randomColorDotNoise.add(Color.BLUE);
			randomColorDotNoise.add(Color.GREEN);
			randomColorDotNoise.add(Color.GRAY);
			randomColorDotNoise.add(Color.LIGHT_GRAY);
			randomColorFont.add(Color.BLACK);
			randomColorFont.add(Color.RED);
			randomFont.add(new Font("Arial", Font.BOLD | Font.ITALIC, 23));
			randomFont.add(new Font("Arial", Font.BOLD, 23));
			randomFont.add(new Font("Arial", Font.ITALIC, 24));
			randomFont.add(new Font("Courier", Font.BOLD, 23));
			randomFont.add(new Font("Courier", Font.BOLD | Font.ITALIC, 24));
			randomFont.add(new Font("Courier", Font.ITALIC, 23));
			randomFont.add(new Font("Verdana", Font.BOLD, 21));
			randomFont.add(new Font("Verdana", Font.BOLD | Font.ITALIC, 21));
			randomFont.add(new Font("Verdana", Font.ITALIC, 21));
			hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		}
	}

	protected CaptchaResult generateCaptcha(String imageType, final OutputStream outStream) throws Throwable {
		CaptchaResult capResult = new CaptchaResult();

		StringBuilder sb = new StringBuilder(12);
		int loop = random.nextBoolean() ? 5 : 6;
		for (int i = 0; i < loop; i++) {
			sb.append(RANDOM_CHAR[random.nextInt(RANDOM_CHAR.length)]);
		}
		String generated = sb.toString();
		capResult.setCaptKey(generated);
		drawingConfig();
		BufferedImage img = genBgImage();
		Graphics2D g = img.createGraphics();
		g.setRenderingHints(hints);
		drawBgImage(img, g);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		for (int i = 0; i < 4; i++)
			makeDotNoise(img, g);
		renderImage(generated, img, g);
		for (int i = 0; i < 6; i++)
			makeNoise(img, g);
		g.dispose();
		ImageIO.write(img, imageType, outStream);
		return capResult;
	}

	private BufferedImage genBgImage() {
		BufferedImage img = new BufferedImage(this.imgWidth, this.imgHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHints(hints);
		drawBgImage(img, g);
		g.dispose();
		return img;
	}

	private BufferedImage drawBgImage(BufferedImage img, Graphics2D g) {
		g.setPaint(ytow);
		g.fill(rectangle2D);
		g.drawImage(img, 0, 0, null);
		return img;
	}

	private void makeDotNoise(BufferedImage image, Graphics2D g) {
		noiseWidth = (float) Math.random();

		g.setColor(randomColorDotNoise.get(random.nextInt(randomColorDotNoise.size())));
		g.fillOval((int) (imgWidth * Math.random()), (int) (imgHeight * Math.random()), (int) (15 * noiseWidth),
				(int) (15 * noiseWidth));
	}

	private void makeNoise(BufferedImage image, Graphics2D g) {
		noiseColor = randomColorNoise.get(random.nextInt(randomColorNoise.size()));
		noiseWidth = random.nextFloat();
		CubicCurve2D cc = new CubicCurve2D.Float(imgWidth * .1f, imgHeight * random.nextFloat(), imgWidth * .1f,
				imgHeight * random.nextFloat(), imgWidth * .25f, imgHeight * random.nextFloat(), imgWidth * .9f,
				imgHeight * random.nextFloat());
		PathIterator pi = cc.getPathIterator(null, 2);
		Point2D tmp[] = new Point2D[200];
		int i = 0;
		while (!pi.isDone()) {
			float[] coords = new float[6];
			switch (pi.currentSegment(coords)) {
			case PathIterator.SEG_MOVETO:
			case PathIterator.SEG_LINETO:
				tmp[i] = new Point2D.Float(coords[0], coords[1]);
			}
			i++;
			pi.next();
		}
		Point2D[] pts = new Point2D[i];
		System.arraycopy(tmp, 0, pts, 0, i);
		g.setColor(noiseColor);
		for (i = 0; i < pts.length - 1; i++) {
			if (i < 3) {
				g.setStroke(new BasicStroke(noiseWidth));
			}
			g.drawLine((int) pts[i].getX(), (int) pts[i].getY(), (int) pts[i + 1].getX(), (int) pts[i + 1].getY());
		}
	}

	private void renderImage(final String secretString, BufferedImage image, Graphics2D g) {
		FontRenderContext frc = g.getFontRenderContext();
		int xBaseline = (int) Math.round(image.getWidth() * XOFFSET);
		char[] chars = new char[1];
		for (char c : secretString.toCharArray()) {
			int yBaseline = image.getHeight() - (int) Math.round(image.getHeight() * YOFFSET);
			if (Character.isWhitespace(c)) {
				xBaseline = xBaseline + 2;
			} else {
				chars[0] = c;
				g.setColor(randomColorFont.get(random.nextInt(randomColorFont.size())));

				int choiceFont = random.nextInt(randomFont.size());
				Font font = randomFont.get(choiceFont);
				g.setFont(font);

				GlyphVector gv = font.createGlyphVector(frc, chars);
				g.drawChars(chars, 0, chars.length, xBaseline, yBaseline + random.nextInt(10) - random.nextInt(10));

				int width = (int) gv.getVisualBounds().getWidth();
				xBaseline += width;
				xBaseline += 3;
			}
		}
	}
}