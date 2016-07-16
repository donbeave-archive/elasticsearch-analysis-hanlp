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

import static org.elasticsearch.indices.analysis.hanlp.Settings.*;

/**
 * Registers indices level analysis components so, if not explicitly configured, will be shared
 * among all indices.
 *
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class HanLpIndicesAnalysis extends AbstractComponent {

    private boolean analyzerIndexMode = false;
    private boolean analyzerNameRecognize = true;
    private boolean analyzerTranslatedNameRecognize = true;
    private boolean analyzerJapaneseNameRecognize = false;
    private boolean analyzerPlaceRecognize = false;
    private boolean analyzerOrganizationRecognize = false;
    private boolean analyzerUseCustomDictionary = true; // enableCustomDictionary
    private boolean analyzerSpeechTagging = false; // PorterStemming
    private boolean analyzerOffset = false;
    private boolean analyzerNumberQuantifierRecognize = false;
    private int analyzerThreads = 1; // if more than 1, it means use multi-threading

    private boolean tokenizerIndexMode = false;
    private boolean tokenizerNameRecognize = true;
    private boolean tokenizerTranslatedNameRecognize = true;
    private boolean tokenizerJapaneseNameRecognize = false;
    private boolean tokenizerPlaceRecognize = false;
    private boolean tokenizerOrganizationRecognize = false;
    private boolean tokenizerUseCustomDictionary = true; // enableCustomDictionary
    private boolean tokenizerSpeechTagging = false; // PorterStemming
    private boolean tokenizerOffset = false;
    private boolean tokenizerNumberQuantifierRecognize = false;
    private int tokenizerThreads = 1; // if more than 1, it means use multi-threading

    @Inject
    public HanLpIndicesAnalysis(Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings);

        initSettings(settings);

        // Register hanlp analyzer
        indicesAnalysisService.analyzerProviderFactories().put("hanlp",
                                                               new PreBuiltAnalyzerProviderFactory("hanlp", AnalyzerScope.INDICES,
                                                                                                   new HanLPAnalyzer(analyzerIndexMode, analyzerNameRecognize, analyzerTranslatedNameRecognize,
                                                                                                                     analyzerJapaneseNameRecognize, analyzerPlaceRecognize, analyzerOrganizationRecognize,
                                                                                                                     analyzerUseCustomDictionary, analyzerSpeechTagging, analyzerOffset,
                                                                                                                     analyzerNumberQuantifierRecognize, analyzerThreads, null)));

        // Register hanlp_tokenizer tokenizer
        indicesAnalysisService.tokenizerFactories().put("hanlp_tokenizer", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "hanlp_tokenizer";
            }

            @Override
            public Tokenizer create() {
                return new HanLPTokenizer(HanLP.newSegment()
                                               .enableIndexMode(tokenizerIndexMode)
                                               .enableNameRecognize(tokenizerNameRecognize)
                                               .enableTranslatedNameRecognize(tokenizerTranslatedNameRecognize)
                                               .enableJapaneseNameRecognize(tokenizerJapaneseNameRecognize)
                                               .enablePlaceRecognize(tokenizerPlaceRecognize)
                                               .enableOrganizationRecognize(tokenizerOrganizationRecognize)
                                               .enableCustomDictionary(tokenizerUseCustomDictionary)
                                               .enablePartOfSpeechTagging(tokenizerSpeechTagging)
                                               .enableOffset(tokenizerOffset)
                                               .enableNumberQuantifierRecognize(tokenizerNumberQuantifierRecognize)
                                               .enableMultithreading(tokenizerThreads), null, tokenizerSpeechTagging);
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

    private void initSettings(Settings settings) {
        // get tokenizer settings
        analyzerIndexMode = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + INDEX_MODE, analyzerIndexMode);
        analyzerNameRecognize = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + NAME_RECOGNIZE, analyzerNameRecognize);
        analyzerTranslatedNameRecognize = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + TRANSLATED_NAME_RECOGNIZE, analyzerTranslatedNameRecognize);
        analyzerJapaneseNameRecognize = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + JAPANESE_NAME_RECOGNIZE, analyzerJapaneseNameRecognize);
        analyzerPlaceRecognize = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + PLACE_RECOGNIZE, analyzerPlaceRecognize);
        analyzerOrganizationRecognize = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + ORGANIZATION_RECOGNIZE, analyzerOrganizationRecognize);
        analyzerUseCustomDictionary = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + USE_CUSTOM_DICTIONARY, analyzerUseCustomDictionary);
        analyzerSpeechTagging = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + SPEECH_TAGGING, analyzerSpeechTagging);
        analyzerOffset = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + OFFSET, analyzerOffset);
        analyzerNumberQuantifierRecognize = settings.getAsBoolean(ANALYZER_CONFIG_PREFIX + NUMBER_QUANTIFIER_RECOGNIZE, analyzerNumberQuantifierRecognize);
        analyzerThreads = settings.getAsInt(ANALYZER_CONFIG_PREFIX + THREADS, analyzerThreads);

        // get tokenizer settings
        tokenizerIndexMode = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + INDEX_MODE, tokenizerIndexMode);
        tokenizerNameRecognize = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + NAME_RECOGNIZE, tokenizerNameRecognize);
        tokenizerTranslatedNameRecognize = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + TRANSLATED_NAME_RECOGNIZE, tokenizerTranslatedNameRecognize);
        tokenizerJapaneseNameRecognize = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + JAPANESE_NAME_RECOGNIZE, tokenizerJapaneseNameRecognize);
        tokenizerPlaceRecognize = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + PLACE_RECOGNIZE, tokenizerPlaceRecognize);
        tokenizerOrganizationRecognize = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + ORGANIZATION_RECOGNIZE, tokenizerOrganizationRecognize);
        tokenizerUseCustomDictionary = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + USE_CUSTOM_DICTIONARY, tokenizerUseCustomDictionary);
        tokenizerSpeechTagging = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + SPEECH_TAGGING, tokenizerSpeechTagging);
        tokenizerOffset = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + OFFSET, tokenizerOffset);
        tokenizerNumberQuantifierRecognize = settings.getAsBoolean(TOKENIZER_CONFIG_PREFIX + NUMBER_QUANTIFIER_RECOGNIZE, tokenizerNumberQuantifierRecognize);
        tokenizerThreads = settings.getAsInt(TOKENIZER_CONFIG_PREFIX + THREADS, tokenizerThreads);

        // fix threads
        if (analyzerThreads < 1) {
            analyzerThreads = 1;
        }
        if (tokenizerThreads < 1) {
            tokenizerThreads = 1;
        }

        // get global HanLP settings
        HanLP.Config.CoreDictionaryPath =
            settings.get(CORE_DICTIONARY_PATH, HanLP.Config.CoreDictionaryPath);
        HanLP.Config.CoreDictionaryTransformMatrixDictionaryPath =
            settings.get(CORE_DICTIONARY_TRANSFORM_MATRIX_DICTIONARY_PATH, HanLP.Config.CoreDictionaryTransformMatrixDictionaryPath);
        HanLP.Config.BiGramDictionaryPath =
            settings.get(BI_GRAM_DICTIONARY_PATH, HanLP.Config.BiGramDictionaryPath);
        HanLP.Config.CoreStopWordDictionaryPath =
            settings.get(CORE_STOP_WORD_DICTIONARY_PATH, HanLP.Config.CoreStopWordDictionaryPath);
        HanLP.Config.CoreSynonymDictionaryDictionaryPath =
            settings.get(CORE_SYNONYM_DICTIONARY_DICTIONARY_PATH, HanLP.Config.CoreSynonymDictionaryDictionaryPath);
        HanLP.Config.PersonDictionaryPath =
            settings.get(PERSON_DICTIONARY_PATH, HanLP.Config.PersonDictionaryPath);
        HanLP.Config.PersonDictionaryTrPath =
            settings.get(PERSON_DICTIONARY_TR_PATH, HanLP.Config.PersonDictionaryTrPath);
        HanLP.Config.CustomDictionaryPath =
            settings.getAsArray(CUSTOM_DICTIONARY_PATH, HanLP.Config.CustomDictionaryPath);
        HanLP.Config.TraditionalChineseDictionaryPath =
            settings.get(TRADITIONAL_CHINESE_DICTIONARY_PATH, HanLP.Config.TraditionalChineseDictionaryPath);
        HanLP.Config.SYTDictionaryPath =
            settings.get(SYT_DICTIONARY_PATH, HanLP.Config.SYTDictionaryPath);
        HanLP.Config.PinyinDictionaryPath =
            settings.get(PINYIN_DICTIONARY_PATH, HanLP.Config.PinyinDictionaryPath);
        HanLP.Config.TranslatedPersonDictionaryPath =
            settings.get(TRANSLATED_PERSON_DICTIONARY_PATH, HanLP.Config.TranslatedPersonDictionaryPath);
        HanLP.Config.JapanesePersonDictionaryPath =
            settings.get(JAPANESE_PERSON_DICTIONARY_PATH, HanLP.Config.JapanesePersonDictionaryPath);
        HanLP.Config.PlaceDictionaryPath =
            settings.get(PLACE_DICTIONARY_PATH, HanLP.Config.PlaceDictionaryPath);
        HanLP.Config.PlaceDictionaryTrPath =
            settings.get(PLACE_DICTIONARY_TR_PATH, HanLP.Config.PlaceDictionaryTrPath);
        HanLP.Config.OrganizationDictionaryPath =
            settings.get(ORGANIZATION_DICTIONARY_PATH, HanLP.Config.OrganizationDictionaryPath);
        HanLP.Config.OrganizationDictionaryTrPath =
            settings.get(ORGANIZATION_DICTIONARY_TR_PATH, HanLP.Config.OrganizationDictionaryTrPath);
        HanLP.Config.CharTypePath =
            settings.get(CHAR_TYPE_PATH, HanLP.Config.CharTypePath);
        HanLP.Config.CharTablePath =
            settings.get(CHAR_TABLE_PATH, HanLP.Config.CharTablePath);
        HanLP.Config.WordNatureModelPath =
            settings.get(WORD_NATURE_MODEL_PATH, HanLP.Config.WordNatureModelPath);
        HanLP.Config.MaxEntModelPath =
            settings.get(MAX_ENT_MODEL_PATH, HanLP.Config.MaxEntModelPath);
        HanLP.Config.CRFSegmentModelPath =
            settings.get(CRF_SEGMENT_MODEL_PATH, HanLP.Config.CRFSegmentModelPath);
        HanLP.Config.CRFDependencyModelPath =
            settings.get(CRF_DEPENDENCY_MODEL_PATH, HanLP.Config.CRFDependencyModelPath);
        HanLP.Config.HMMSegmentModelPath =
            settings.get(HMM_SEGMENT_MODEL_PATH, HanLP.Config.HMMSegmentModelPath);
        HanLP.Config.ShowTermNature = settings.getAsBoolean(SHOW_TERM_NATURE, true);
        HanLP.Config.Normalization = settings.getAsBoolean(NORMALIZATION, false);
    }

}
