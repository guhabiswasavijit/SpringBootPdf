package dev.simplesolution.pdf;

public class PdfGenerationError extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public PdfGenerationError(Exception ex) {
		this.setStackTrace(ex.getStackTrace());
	}

}
