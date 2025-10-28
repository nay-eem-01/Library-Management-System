package com.disl.librarymanagementsystem.config;

import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.stereotype.Component;

@Component("customLuceneAnalysisConfigurer")
public class CustomLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {

    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        context.analyzer("english").custom()
                .tokenizer(StandardTokenizerFactory.class)
                .tokenFilter(WordDelimiterGraphFilterFactory.class)
                .param("generateWordParts", "1")
                .param("generateNumberParts", "1")
                .param("catenateWords", "1")
                .param("catenateNumbers", "1")
                .param("catenateAll", "1")
                .param("splitOnCaseChange", "1")
                .param("preserveOriginal", "1")
                .tokenFilter(LowerCaseFilterFactory.class)
                .tokenFilter(StopFilterFactory.class)
                .tokenFilter(SnowballPorterFilterFactory.class)
                .param("language", "English")
                .tokenFilter(ASCIIFoldingFilterFactory.class);
        
        context.analyzer("name").custom()
                .tokenizer(StandardTokenizerFactory.class)
                .tokenFilter(WordDelimiterGraphFilterFactory.class)
                .param("generateWordParts", "1")
                .param("generateNumberParts", "1")
                .param("catenateWords", "1")
                .param("catenateNumbers", "1")
                .param("catenateAll", "1")
                .param("splitOnCaseChange", "1")
                .param("preserveOriginal", "1")
                .tokenFilter(LowerCaseFilterFactory.class)
                .tokenFilter(ASCIIFoldingFilterFactory.class);

        context.analyzer("edge_ngram").custom()
                .tokenizer(StandardTokenizerFactory.class)
                .tokenFilter(LowerCaseFilterFactory.class)
                .tokenFilter(EdgeNGramFilterFactory.class)
                .param("minGramSize", "4")
                .param("maxGramSize", "10");
    }
}
