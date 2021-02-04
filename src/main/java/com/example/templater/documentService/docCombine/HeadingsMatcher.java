package com.example.templater.documentService.docCombine;

import com.example.templater.documentService.tempParamsGetter.TempParamsGetter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeadingsMatcher {
    public int headingsMatch(List<XWPFParagraph> hListS, List<XWPFParagraph> hListC,
                             XWPFParagraph source, XWPFParagraph candidate) {
        int score = 0;
        score += headingsNameMatch(source, candidate);
        List<XWPFParagraph> subheadingsS = TempParamsGetter.getSubHeadings(hListS, source);
        List<XWPFParagraph> subheadingsC = TempParamsGetter.getSubHeadings(hListC, candidate);
        int number_matched_subheadings = 0;
        for (XWPFParagraph s : subheadingsS) {
            for (XWPFParagraph c : subheadingsC) {
                if (headingsNameMatch(s, c) == 100) {
                    ++number_matched_subheadings;
                    break;
                }
            }
        }
        if (number_matched_subheadings != 0) {
            int sizeS = subheadingsS.size();
            int sizeC = subheadingsC.size();
            score += (number_matched_subheadings * 40) / Integer.max(sizeS, sizeC);
        }
        return score;
    }

    public int headingsNameMatch(XWPFParagraph source, XWPFParagraph candidate) {
        String strS = source.getText();
        String strC = candidate.getText();
        if (strS.equals(strC)) {
            return 100;
        }
        List<String> SWords = getWords(strS);
        List<String> CWords = getWords(strC);
        if (SWords == null || CWords == null) {
            return 0;
        }
        int number_matched_words = 0;
        for (String s : SWords) {
            s = s.toLowerCase();
            for (String c : CWords) {
                c = c.toLowerCase();
                if (s.length() < c.length()) {
                    continue;
                }
                else if (s.equals(c)) {
                    ++number_matched_words;
                    break;
                }
                else {
                    boolean isMatched = true;
                    int i;
                    for (i = 0; i < s.length(); ++i) {
                        if (s.charAt(i) != c.charAt(i)) {
                            isMatched = false;
                            break;
                        }
                    }
                    if (isMatched && ((i * 100) / c.length() > 80)) {
                        ++number_matched_words;
                        break;
                    }
                }
            }
        }
        if (number_matched_words == 0) {
            return 0;
        }
        else {
            return (number_matched_words * 100) / SWords.size();
        }
    }

    public List<String> getWords(String src) {
        if (src == null) {
            return null;
        }
        String[] strings =  src.split("[\\s()\\[\\]{}<>\\-~'\"`.,;:!?@#$%^&*+_=/|\\\\]");
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, strings);
        deleteEmptyWords(list);
        return list;
    }

    private void deleteEmptyWords(List<String> list) {
        if (list != null) {
            list.removeIf(s -> s == null || s.equals(""));
        }
    }

}
