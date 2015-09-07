/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elasticsearch.indices.analysis.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.lucene.HanLPAnalyzer;
import com.hankcs.lucene.HanLPTokenizer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;

import java.io.Reader;

/**
 * Registers indices level analysis components so, if not explicitly configured, will be shared
 * among all indices.
 *
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class HanLpIndicesAnalysis extends AbstractComponent {

    // TODO
    private boolean enableIndexMode = true;
    private boolean enablePorterStemming = false;

    @Inject
    public HanLpIndicesAnalysis(Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings);

        // Register hanlp analyzer
        indicesAnalysisService.analyzerProviderFactories().put("hanlp",
                new PreBuiltAnalyzerProviderFactory("hanlp", AnalyzerScope.INDICES, new HanLPAnalyzer(enablePorterStemming)));

        // Register hanlp_tokenizer tokenizer
        indicesAnalysisService.tokenizerFactories().put("hanlp_tokenizer", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "hanlp_tokenizer";
            }

            @Override
            public Tokenizer create(Reader reader) {
                // TODO
                return new HanLPTokenizer(HanLP.newSegment().enableOffset(true).enableIndexMode(enableIndexMode), null, enablePorterStemming, reader);
            }
        }));

        // Register hanlp_sentence tokenizer -- for backwards compat an alias to smartcn_tokenizer
        indicesAnalysisService.tokenizerFactories().put("hanlp_sentence", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "hanlp_sentence";
            }

            @Override
            public Tokenizer create(Reader reader) {
                // TODO
                return new HanLPTokenizer(HanLP.newSegment().enableOffset(true).enableIndexMode(enableIndexMode), null, enablePorterStemming, reader);
            }
        }));

        // Register hanlp_word token filter -- noop
        indicesAnalysisService.tokenFilterFactories().put("hanlp_word", new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
            @Override
            public String name() {
                return "hanlp_word";
            }

            @Override
            public TokenStream create(TokenStream tokenStream) {
                return tokenStream;
            }
        }));
    }

}
