package com.disl.librarymanagementsystem.config;

import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
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
                .tokenFilter(LowerCaseFilterFactory.class)
                .tokenFilter(StopFilterFactory.class)
                .tokenFilter(SnowballPorterFilterFactory.class)
                .param("language", "English")
                .tokenFilter(ASCIIFoldingFilterFactory.class);
        
        context.normalizer("lowercase").custom()
                .tokenFilter(LowerCaseFilterFactory.class)
                .tokenFilter(ASCIIFoldingFilterFactory.class);
    }
}
