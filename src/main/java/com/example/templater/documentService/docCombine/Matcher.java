package com.example.templater.documentService.docCombine;

import com.example.templater.documentService.tempParamsGetter.HeadingWithText;
import com.example.templater.documentService.tempParamsGetter.TempParamsGetter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Matcher {
    public int headingsMatch(List<XWPFParagraph> hListS, List<XWPFParagraph> hListC,
                             HeadingWithText sourceHWT, HeadingWithText candidateHWT) {
        int score = 0;
        XWPFParagraph source = sourceHWT.getHeading();
        XWPFParagraph candidate = candidateHWT.getHeading();
        // сравнивание названий
        score += headingsNameMatch(source, candidate);
        List<XWPFParagraph> subheadingsS = TempParamsGetter.getSubHeadings(hListS, source);
        List<XWPFParagraph> subheadingsC = TempParamsGetter.getSubHeadings(hListC, candidate);
        // проверка наличия одинаковых сабхедингов
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

        // проверка на наличие схожих таблиц
        int table_score = 0;
        List<XWPFTable> tablesS = sourceHWT.getTables();
        List<XWPFTable> tablesC = candidateHWT.getTables();
        List<XWPFTable> resolved = new ArrayList<>();
        if (tablesS != null && !tablesS.isEmpty() && tablesC != null && !tablesC.isEmpty()) {
            for (XWPFTable tableS : tablesS) {
                for (XWPFTable tableC : tablesC) {
                    if (!resolved.contains(tableC)) {
                        int cell_number = Integer.min(tableS.getRow(0).getTableCells().size(),
                                tableC.getRow(0).getTableCells().size());
                        int matched_coloms = 0;
                        for (int i = 0; i < cell_number; ++i) {
                            int headings_match = 0;
                            XWPFParagraph hS = tableS.getRow(0).getCell(i).getParagraphArray(0);
                            XWPFParagraph hC = tableC.getRow(0).getCell(i).getParagraphArray(0);
                            headings_match = headingsNameMatch(hS, hC);
                            if (headings_match >= 100) {
                                ++matched_coloms;
                            }
                        }
                        int scoreT = (matched_coloms * 100) / cell_number;
                        table_score += scoreT;
                        if (scoreT >= 80) {
                            resolved.add(tableC);
                            break;
                        }
                    }
                }
            }
            int part_of_matched_tables = (table_score * 100) / Integer.min(tablesS.size(), tablesC.size());
            score += (part_of_matched_tables * 20) / 100;
        }
        return score;
    }

    public int headingsNameMatch(XWPFParagraph source, XWPFParagraph candidate) {
        if (source == null || candidate == null) {
            return 0;
        }
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
                    if (isMatched && ((i * 100) / c.length() > 70)) {
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

    public List<String> getSentences(String text) {
        if (text == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("((\\.|\\?|!)\\s([A-Z]))");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        int start = 0;
        int begin = 0;
        int end = 0;
        List<String> list = new ArrayList<>();
        while(matcher.find(start)) {
            end = matcher.end();
            String sentence = text.substring(begin, end - 2);
            start = matcher.end();
            begin = matcher.end() - 1;
            list.add(sentence);
        }
        list.add(text.substring(begin));
        deleteEmptyWords(list);
        return list;
    }

    private void deleteEmptyWords(List<String> list) {
        if (list != null) {
            list.removeIf(s -> s == null || s.equals(""));
        }
    }

    // возвращает процент совпадения предложений
    // сравнивает наличие идентичных слов
    public int sentencesMatch(String l, String r) {
        List<String> wordsL = getWords(l);
        List<String> wordsR = getWords(r);
        List<String> matched_words = new ArrayList<>();
        int number_matched = 0;
        for (String wordL : wordsL) {
            for (String wordR : wordsR) {
                if (wordL.equals(wordR)) {
                    ++number_matched;
                    matched_words.add(wordR);
                    break;
                }
            }
            if (matched_words.size() == wordsR.size()) {
                break;
            }
        }
        return  (number_matched * 100) / (Integer.max(wordsL.size(), wordsR.size()));
    }


    // 1) ищет первое предложение первого абзаца во втором
    // 2) ищет первое предложение второго абзаца в первом
    // 3) проверяет совпадение предложений начиная с общего первого совпадения из 1) и 2)
    public int paragraphsMatch(String l, String r) {
        List<String> sentencesL = getSentences(l);
        List<String> sentencesR = getSentences(r);
        if (sentencesL.isEmpty() || sentencesR.isEmpty()) {
            return 0;
        }
        int start_posR = 0;
        int start_posL = 0;
        for (int i = 0; i < Integer.min(sentencesL.size(), sentencesR.size()); ++i) {
            if (sentencesMatch(sentencesL.get(0), sentencesR.get(i)) >= 80) {
                start_posR = i;
                break;
            }
        }
        for (int i = 0; i < Integer.min(sentencesL.size(), sentencesR.size()); ++i) {
            if (sentencesMatch(sentencesR.get(0), sentencesL.get(i)) >= 80) {
                start_posL = i;
                break;
            }
        }
        int score = 0;
        for (int i = 0; i < Integer.min(sentencesL.size(), sentencesR.size()); ++i) {
                score += sentencesMatch(sentencesL.get(i + start_posL), sentencesR.get(i + start_posR));
            }
        return score / Integer.max(sentencesL.size(), sentencesR.size());
    }
}
