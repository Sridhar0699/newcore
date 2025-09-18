package com.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class ExtractPdf extends PDFStreamEngine {
	private final String filePath;
	private final String outputDir;

	// Constructor
	public ExtractPdf(String filePath, String outputDir) {
		this.filePath = filePath;
		this.outputDir = outputDir;
	}

	// Execute
	public void execute() {
		try {
			File file = new File(filePath);
			PDDocument document = Loader.loadPDF(file);

			for (PDPage page : document.getPages()) {
				
				processPage(page);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
		String operation = operator.getName();

		if ("Do".equals(operation)) {
			COSName objectName = (COSName) operands.get(0);
			PDXObject pdxObject = getResources().getXObject(objectName);

			if (pdxObject instanceof PDImageXObject) {
				// Image
				PDImageXObject image = (PDImageXObject) pdxObject;
				BufferedImage bImage = image.getImage();

				// File
				String randomName = UUID.randomUUID().toString();
				File outputFile = new File(outputDir, randomName + ".png");

				// Write image to file
				//ImageIO.write(bImage, "PNG", outputFile);
				
				 Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
	                float imageXScale = ctmNew.getScalingFactorX();
	                float imageYScale = ctmNew.getScalingFactorY();
	                System.out.println("position in PDF = " + ctmNew.getTranslateX() + ", " + ctmNew.getTranslateY() + " in user space units");

			} else if (pdxObject instanceof PDFormXObject) {
				PDFormXObject form = (PDFormXObject) pdxObject;
				showForm(form);
			}
		}

		else
			super.processOperator(operator, operands);
	}
	
	public static void main(String[] args){
        String filePath = "/home/incresol/Documents/fwscopeofdocuments/sample.pdf";
        String outputDir = "/home/incresol/Documents/fwscopeofdocuments/Output";

        ExtractPdf useCase = new ExtractPdf(
                filePath,
                outputDir
        );
        useCase.execute();
    }
}