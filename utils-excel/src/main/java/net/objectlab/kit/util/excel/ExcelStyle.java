package net.objectlab.kit.util.excel;

import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ExcelStyle {
    private final boolean center;
    private final boolean left;
    private final boolean right;
    private final boolean bold;
    private final boolean italic;
    private final boolean underline;
    private final boolean numericFormat;
    private final boolean percentFormat;
    private final boolean wrap;
    private final boolean header;
    private final String dataFormat;

    public static Builder builder() {
        return new Builder();
    }

    private ExcelStyle(Builder b) {
        center = b.center;
        left = b.left;
        right = b.right;
        bold = b.bold;
        italic = b.italic;
        underline = b.underline;
        numericFormat = b.numericFormat;
        percentFormat = b.percentFormat;
        wrap = b.wrap;
        header = b.header;
        dataFormat = b.dataFormat;
    }

    public CellStyle build(ExcelCell cell) {
        Optional<CellStyle> cs = cell.findStyle(hashCode());
        if (cs.isPresent()) {
            return cs.get();
        }

        CellStyle cellStyle = cell.cloneStyle(hashCode());
        if (bold || italic || underline || header) {
            Font f = cell.workbook().createFont();
            f.setBold(bold | header);
            if (underline) {
                f.setUnderline(Font.U_SINGLE);
            }
            f.setItalic(italic);
            cellStyle.setFont(f);
        }

        if (header) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        if (dataFormat != null && dataFormat.length() > 0) {
            DataFormat format = cell.poiWorkbook().createDataFormat();
            cellStyle.setDataFormat(format.getFormat(dataFormat));
        } else if (numericFormat) {
            DataFormat format = cell.poiWorkbook().createDataFormat();
            cellStyle.setDataFormat(format.getFormat("#,###,###,###"));
        } else if (percentFormat) {
            DataFormat format = cell.poiWorkbook().createDataFormat();
            cellStyle.setDataFormat(format.getFormat("#,###,###,##0.00%"));
        }

        if (center) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        } else if (right) {
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        } else if (left) {
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        }
        return cellStyle;
    }

    public static class Builder {
        private boolean center;
        private boolean left;
        private boolean right;
        private boolean bold;
        private boolean italic;
        private boolean underline;
        private boolean numericFormat;
        private boolean percentFormat;
        private boolean wrap;
        private boolean header;
        private String dataFormat;

        public Builder center() {
            center = true;
            return this;
        }

        public Builder left() {
            left = true;
            return this;
        }

        public Builder right() {
            right = true;
            return this;
        }

        public Builder bold() {
            bold = true;
            return this;
        }

        public Builder italic() {
            italic = true;
            return this;
        }

        public Builder underline() {
            underline = true;
            return this;
        }

        public Builder numericFormat() {
            numericFormat = true;
            return this;
        }

        public Builder percentFormat() {
            percentFormat = true;
            return this;
        }

        public Builder wrap() {
            wrap = true;
            return this;
        }

        public Builder header() {
            header = true;
            return this;
        }

        public Builder dataFormat(String dataFormat) {
            this.dataFormat = dataFormat;
            return this;
        }

        public ExcelStyle build() {
            return new ExcelStyle(this);
        }
    }
}