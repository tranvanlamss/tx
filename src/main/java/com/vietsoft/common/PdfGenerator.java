package com.vietsoft.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public final class PdfGenerator {
	private static final String INVOICE_BOOKED_CANDIDATE_OF_REQUESTER = "/templates/mails/invoice-booking-candidate-of-requester.txt";
	private static final String INVOICE_BOOKED_CANDIDATE_OF_SUPPLIER = "/templates/mails/invoice-booking-candidate-of-supplier.txt";
	private static final String LOGO_URL = "/logo.png";
	private static final String FONT_URL = "/asset/vuArial.ttf";

	

	// TODO: create invoice of book candidate to supplier
	public void createInvoiceBookCandidateOfSupplier(String supplierName, String supplierEmail, String supplierPhone,
			String candidateId, String candidateName, String requester, String request, String applyType,
			String totalCost, String step, String stepCost) throws IOException, DocumentException {
		generateInvoiceOfSupplier(supplierName, supplierEmail, supplierPhone, candidateId, candidateName, requester,
				request, applyType, totalCost, step, stepCost, INVOICE_BOOKED_CANDIDATE_OF_SUPPLIER);

	}

	// TODO: create invoice of book candidate to supplier
	public void createInvoiceBookCandidateOfRequester(String requesterName, String requesterEmail,
			String requesterPhone, String candidateId, String candidateName, String supplier, String request,
			String applyType, String totalCost, String step, String stepCost) throws IOException, DocumentException {
		generateInvoiceOfRequester(requesterName, requesterEmail, requesterPhone, candidateId, candidateName, supplier,
				request, applyType, totalCost, step, stepCost, INVOICE_BOOKED_CANDIDATE_OF_REQUESTER);

	}

	// get text from template
	private String loadInvoiceTemplate(String filepath) throws IOException {
		InputStream ins = getClass().getResourceAsStream(filepath);
		return IOUtils.toString(ins, "UTF-8");
	}

	

	// TODO: =======================BOOK CANDIDATE==============================

	// TODO: create invoice for requester
	public String generateInvoiceOfRequester(String candidate) {
		return null;
	}

	// TODO: create invoice for supplier
	public String generateInvoiceOfSupplier(String supplierName, String supplierEmail, String supplierPhone,
			String candidateId, String candidateName, String requester, String request, String applyType,
			String totalCost, String step, String stepCost, String template) throws IOException, DocumentException {
		String content = loadInvoiceTemplate(template).replaceAll("(\\$\\{username\\})", supplierName)
				.replaceAll("(\\$\\{email\\})", supplierEmail).replaceAll("(\\$\\{phone\\})", supplierPhone);
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("Invoice.pdf"));
		doc.open();
		String imageFile = LOGO_URL;
		Image img = Image.getInstance(imageFile);
		doc.add(img);
		String[] infors = { request, requester, candidateId, candidateName, applyType, totalCost, step, stepCost };
		String[] headers = { "Request", "Requester", "Candidate ID", "Candidate name", "ApplyType", "Total cost",
				"Step", "Step payment" };
		doc = addTextIntoPDFfile(content, doc);
		doc = addTableIntoPDFfile(doc, headers, infors);
		doc.close();

		return content;
	}

	// TODO: create invoice of requester
	public String generateInvoiceOfRequester(String requesterName, String requesterEmail, String requesterPhone,
			String candidateId, String candidateName, String supplier, String request, String applyType,
			String totalCost, String step, String stepCost, String template) throws IOException, DocumentException {
		String content = loadInvoiceTemplate(template).replaceAll("(\\$\\{username\\})", requesterName)
				.replaceAll("(\\$\\{email\\})", requesterEmail).replaceAll("(\\$\\{phone\\})", requesterPhone);
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("Invoice.pdf"));
		doc.open();
		String imageFile = LOGO_URL;
		Image img = Image.getInstance(imageFile);
		doc.add(img);
		String[] headers = { "Request", "Supplier", "Candidate ID", "Candidate name", "ApplyType", "Total cost", "Step",
				"Step payment" };
		String[] infors = { request, supplier, candidateId, candidateName, applyType, totalCost, step, stepCost };
		doc = addTextIntoPDFfile(content, doc);
		doc = addTableIntoPDFfile(doc, headers, infors);
		doc.close();
		return content;
	}

	// =========================================================================
	// TODO: add text into pdf file
	public Document addTextIntoPDFfile(String text, Document document) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(FONT_URL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Paragraph para = new Paragraph(text, new Font(bf, 10));
		document.add(para);
		document.add(Chunk.NEWLINE);

		return document;
	}

	// TODO: add table into pdf file
	public Document addTableIntoPDFfile(Document document, String[] headers, String[] infors)
			throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(new float[] { 25, 75 });
		table.setWidthPercentage(100);
		addTableHeader(table);
		addRows(table, headers, infors);
		document.add(table);
		return document;
	}

	// TODO: add image into pdf file
	public Document addImageIntoPDFfile() {
		return null;
	}

	//
	private void addTableHeader(PdfPTable table) {
		PdfPCell header = new PdfPCell();
		header.setBackgroundColor(BaseColor.CYAN);
		header.setBorderWidth(1);
		header.setPhrase(new Phrase("No."));
		table.addCell(header);

		header.setPhrase(new Phrase("Information"));
		header.setPaddingLeft(120);
		table.addCell(header);
	}

	private void addRows(PdfPTable table, String[] headers, String[] infors) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(FONT_URL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Paragraph para = new Paragraph();
		for (int i = 0; i < headers.length; i++) {
			para = new Paragraph(headers[i], new Font(bf, 10));
			table.addCell(para);
			para = new Paragraph(infors[i], new Font(bf, 10));
			table.addCell(para);
		}
	}

}
