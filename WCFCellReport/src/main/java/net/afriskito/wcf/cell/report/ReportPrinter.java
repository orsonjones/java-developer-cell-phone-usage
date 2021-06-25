package net.afriskito.wcf.cell.report;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.afriskito.wcf.cell.report.beans.ReportData;

class ReportPrinter implements Printable {
    private ReportData reportData;
    private int x;
    private int y;
    private int page = 0;

    public ReportPrinter(ReportData reportData) {
        this.reportData = reportData;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > page)
            return NO_SUCH_PAGE;
        
        x = (int) pageFormat.getImageableX();
        y = (int) pageFormat.getImageableY();
        
        printLine(graphics, "Date: " + reportData.reportDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        printLine(graphics, "Phones: " + reportData.phones());
        printLine(graphics, "Total Minutes: " + reportData.totalMinutes());
        printLine(graphics, "Total Data: " + reportData.totalData());
        printLine(graphics, "Average Minutes: " + reportData.averageMinutes());
        printLine(graphics, "Average Data: " + reportData.averageData());
        return PAGE_EXISTS;
    }

    private void printLine(Graphics graphics, String text) {
        y += graphics.getFontMetrics().getAscent();
        graphics.drawString(text, x, y);
        y += graphics.getFontMetrics().getDescent();
    }
    
}
