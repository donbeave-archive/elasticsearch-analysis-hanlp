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
package org.elasticsearch.index.analysis;

import com.hankcs.hanlp.HanLP;
import com.hankcs.lucene.HanLPTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;

import java.io.Reader;

import static org.elasticsearch.indices.analysis.hanlp.Settings.*;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class HanLpTokenizerTokenizerFactory extends AbstractTokenizerFactory {

    private boolean indexMode = false;
    private boolean nameRecognize = true;
    private boolean translatedNameRecognize = true;
    private boolean japaneseNameRecognize = false;
    private boolean placeRecognize = false;
    private boolean organizationRecognize = false;
    private boolean useCustomDictionary = true;
    private boolean speechTagging = false;
    private boolean offset = false;
    private boolean numberQuantifierRecognize = false;
    private int threads = 1;

    @Inject
    public HanLpTokenizerTokenizerFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);

        indexMode = settings.getAsBoolean(INDEX_MODE, indexMode);
        nameRecognize = settings.getAsBoolean(NAME_RECOGNIZE, nameRecognize);
        translatedNameRecognize = settings.getAsBoolean(TRANSLATED_NAME_RECOGNIZE, translatedNameRecognize);
        japaneseNameRecognize = settings.getAsBoolean(JAPANESE_NAME_RECOGNIZE, japaneseNameRecognize);
        placeRecognize = settings.getAsBoolean(PLACE_RECOGNIZE, placeRecognize);
        organizationRecognize = settings.getAsBoolean(ORGANIZATION_RECOGNIZE, organizationRecognize);
        useCustomDictionary = settings.getAsBoolean(USE_CUSTOM_DICTIONARY, useCustomDictionary); // enableCustomDictionary
        speechTagging = settings.getAsBoolean(SPEECH_TAGGING, speechTagging); // PorterStemming
        offset = settings.getAsBoolean(OFFSET, offset);
        numberQuantifierRecognize = settings.getAsBoolean(NUMBER_QUANTIFIER_RECOGNIZE, numberQuantifierRecognize);
        threads = settings.getAsInt(THREADS, threads); // if more than 1, it means use multi-threading
    }

    @Override
    public Tokenizer create(Reader reader) {
        return new HanLPTokenizer(HanLP.newSegment()
                .enableIndexMode(indexMode)
                .enableNameRecognize(nameRecognize)
                .enableTranslatedNameRecognize(translatedNameRecognize)
                .enableJapaneseNameRecognize(japaneseNameRecognize)
                .enablePlaceRecognize(placeRecognize)
                .enableOrganizationRecognize(organizationRecognize)
                .enableCustomDictionary(useCustomDictionary)
                .enablePartOfSpeechTagging(speechTagging)
                .enableOffset(offset)
                .enableNumberQuantifierRecognize(numberQuantifierRecognize)
                .enableMultithreading(threads), null, speechTagging, reader);
    }

}
