package com.uds.yl.poi.word;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Created by GLF on 2017/8/24. Talk is Cheap,Show me the Code!
 */
public class XWPFParagraphPackage {
    private XWPFParagraph paragraph;

    public XWPFParagraphPackage(XWPFParagraph paragraph){
        this.paragraph = paragraph;
    }


    public XWPFParagraphPackage setAlignment(ParagraphAlignment align){
        this.paragraph.setAlignment(align);
        return this;
    }


    public XWPFRun createRun(){
        return this.paragraph.createRun();
    }

}
