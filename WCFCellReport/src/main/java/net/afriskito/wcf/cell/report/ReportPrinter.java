package net.afriskito.wcf.cell.report;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

class ReportPrinter implements Printable {
    private String text;

    public ReportPrinter(String text) {
        this.text = text;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0)
            return NO_SUCH_PAGE;
        
        int x = (int) pageFormat.getImageableX();
        int y = (int) pageFormat.getImageableY();
        
        y += graphics.getFontMetrics().getAscent();
        graphics.drawString(text, x, y);
        return PAGE_EXISTS;
    }
    
}
