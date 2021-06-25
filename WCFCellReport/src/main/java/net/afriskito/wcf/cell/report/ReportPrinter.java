package net.afriskito.wcf.cell.report;

import com.google.common.collect.ImmutableList;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.afriskito.wcf.cell.report.beans.CellPhone;
import net.afriskito.wcf.cell.report.beans.Details;
import net.afriskito.wcf.cell.report.beans.ReportData;

class ReportPrinter implements Printable {
    private ReportData reportData;
    private int x;
    private int y;
    private Graphics graphics = null;
    private PageFormat pageFormat = null;
    private int page = 0;
    private boolean headerPrinted = false;
    private int detailsPrinted = 0;
    private Map<Integer, Integer> pageDetailsPrinted = new HashMap<>();

    public ReportPrinter(ReportData reportData) {
        this.reportData = reportData;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > page)
            return NO_SUCH_PAGE;
        if (pageIndex == 0) {
            headerPrinted = false;
            detailsPrinted = 0;
        } else {
            if (pageDetailsPrinted.containsKey(pageIndex))
                detailsPrinted = pageDetailsPrinted.get(pageIndex);
            else
                pageDetailsPrinted.put(pageIndex, detailsPrinted);
        }
        this.graphics = graphics;
        this.pageFormat = pageFormat;
        x = (int) pageFormat.getImageableX();
        y = (int) pageFormat.getImageableY() + graphics.getFontMetrics().getAscent();
        
        if (!headerPrinted) {
            printHeader();
            headerPrinted = true;
        }
        
        int remainLines = getRemainingLines();
        final ImmutableList<Details> details = reportData.details();
        while (remainLines >= 6 && detailsPrinted < details.size()) {
            printDetail(details.get(detailsPrinted));
            detailsPrinted++;
            remainLines = getRemainingLines();
        }
        
        if (detailsPrinted < details.size())
            page = pageIndex + 1;
        
        return PAGE_EXISTS;
    }

    private int getRemainingLines() {
        return (((int) (pageFormat.getImageableHeight() + pageFormat.getImageableY())) - y)
                / graphics.getFontMetrics().getHeight();
    }

    private void printHeader() {
        printLine("Date: " + reportData.reportDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        printLine("Phones: " + reportData.phones());
        printLine("Total Minutes: " + reportData.totalMinutes());
        printLine("Total Data: " + String.format("%1.1f", reportData.totalData()));
        printLine("Average Minutes: " + String.format("%1.1f", reportData.averageMinutes()));
        printLine("Average Data: " + String.format("%1.1f", reportData.averageData()));
        printLine();
    }

    private void printDetail(Details details) {
        CellPhone cellPhone = details.cellPhone();
        printLine("ID:" + cellPhone.employeeId() + "  " + cellPhone.employeeName());
        printCell("Model:" + cellPhone.model(), 0, 2);
        printCell("Purchased:" + cellPhone.purchaseDate(), 1, 2);
        printLine();
        for (int i = 0; i < 12; i++)
            printCell(Month.of(i+1).getDisplayName(TextStyle.SHORT, Locale.getDefault()), i+2, 14);
        printLine();
        printCell("Minutes", 0, 14);
        for (int i = 0; i < 12; i++)
            printCell(Integer.toString(details.monthlyMinutes().get(i)), i+2, 14);
        printLine();
        printCell("Data", 0, 14);
        for (int i = 0; i < 12; i++)
            printCell(String.format("%1.1f", details.monthlyData().get(i)), i+2, 14);
        printLine();
        printLine();
    }

    private void printLine(String text) {
        graphics.drawString(text, x, y);
        y += graphics.getFontMetrics().getHeight();
    }

    private void printLine() {
        y += graphics.getFontMetrics().getHeight();
    }

    private void printCell(String text, int column, int columns) {
        int cellWidth = (int) pageFormat.getImageableWidth()/columns;
        int cellX = ((int) pageFormat.getImageableX()) + cellWidth*column;
        graphics.drawString(text, cellX, y);
    }
    
}
