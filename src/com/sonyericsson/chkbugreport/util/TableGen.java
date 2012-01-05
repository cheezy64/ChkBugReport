package com.sonyericsson.chkbugreport.util;

import java.util.Vector;

import com.sonyericsson.chkbugreport.Chapter;

public class TableGen {

    public static final int FLAG_NONE = 0;
    public static final int FLAG_SORT = 1;

    private int mTableFlags;
    private Chapter mCh;
    private Vector<Column> mColumns = new Vector<TableGen.Column>();
    private int mColIdx;

    private class Column {
        private String title;
        public Column(String title, int flag) {
            this.title = title;
        }
    }

    public TableGen(Chapter ch, int flag) {
        mCh = ch;
        mTableFlags = flag;
    }

    public void addColumn(String title, int flag) {
        mColumns.add(new Column(title, flag));
    }

    public void begin() {
        if (0 != (mTableFlags & FLAG_SORT)) {
            mCh.addLine("<div class=\"hint\">(Hint: click on the headers to sort the data. Shift+click to sort on multiple columns.)</div>");
            mCh.addLine("<table class=\"tablesorter\">");
        } else {
            mCh.addLine("<table class=\"\">");
        }
        mCh.addLine("<thead>");
        mCh.addLine("<tr>");
        for (Column c : mColumns) {
            mCh.addLine("<th>" + c.title + "</td>");
        }
        mCh.addLine("</tr>");
        mCh.addLine("</thead>");
        mCh.addLine("<tbody>");
        mColIdx = 0;
    }

    public void addData(String text) {
        addData(null, text, FLAG_NONE);
    }

    public void addData(String link, String text, int flag) {
        if (mColIdx == 0) {
            mCh.addLine("<tr>");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<td>");
        if (link != null) {
            sb.append("<a href=\"");
            sb.append(link);
            sb.append("\">");
        }
        sb.append(text);
        if (link != null) {
            sb.append("</a>");
        }
        sb.append("</td>");
        mCh.addLine(sb.toString());
        mColIdx = (mColIdx + 1) % mColumns.size();
        if (mColIdx == 0) {
            mCh.addLine("</tr>");
        }
    }

    public void end() {
        mCh.addLine("</tbody>");
        mCh.addLine("<table>");
    }

}