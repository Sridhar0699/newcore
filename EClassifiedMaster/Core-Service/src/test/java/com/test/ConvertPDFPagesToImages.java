package com.test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ConvertPDFPagesToImages {
    public static void main(String[] args) {
        try {
        String sourceDir = "/home/incresol/Documents/fwscopeofdocuments/sample.pdf"; // Pdf files are read from this folder
        String destinationDir = "/home/incresol/Documents/fwscopeofdocuments/Converted_PdfFiles_to_Image/"; // converted images from pdf document are saved here

        File sourceFile = new File(sourceDir);
        File destinationFile = new File(destinationDir);
        if (!destinationFile.exists()) {
            destinationFile.mkdir();
            System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
        }
        if (sourceFile.exists()) {
            System.out.println("Images copied to Folder: "+ destinationFile.getName());             
            PDDocument document = Loader.loadPDF(sourceFile);
            PDPageTree list = document.getDocumentCatalog().getPages();
            System.out.println("Total files to be converted -> "+ list.getCount());

            String fileName = sourceFile.getName().replace(".pdf", "");             
            int pageNumber = 1;
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (PDPage page : list) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageNumber, 300, ImageType.RGB);
                File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
                System.out.println("Image Created -> "+ outputfile.getName());
                ImageIO.write(image, "png", outputfile);
                pageNumber++;
            }
            document.close();
            System.out.println("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
        } else {
            System.err.println(sourceFile.getName() +" File not exists");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}